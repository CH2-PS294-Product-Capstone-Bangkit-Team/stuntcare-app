package com.bangkit.stuntcare.ui.view.parent.children.update

import android.util.Log
import android.widget.Toast
import com.bangkit.stuntcare.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.data.remote.response.ApiResponse
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import retrofit2.HttpException
import java.util.concurrent.Flow

@Composable
fun UpdateChildrenScreen(
    childrenId: String,
    navigator: ChildrenScreenNavigator,
    viewModel: UpdateChildrenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {

    viewModel.getChildrenById(childrenId).let {
        val children = viewModel.children.collectAsState().value
        if (children != null) {
            UpdateChildrenContent(
                children = children,
                navigator = navigator,
                viewModel = viewModel
            ) // TODO: Tempat Pemanggilan ViewMdoel
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateChildrenContent(
    children: DetailChildrenResponse,
    viewModel: UpdateChildrenViewModel,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var weight by remember {
        mutableStateOf(children.data.growthHistory.first().weight.toString())
    }

    var height by remember {
        mutableStateOf(children.data.growthHistory.first().height.toString())
    }

    var updateState by remember {
        mutableStateOf(false)
    }

    val heightFromModel = navigator.getResult()
    if (heightFromModel != null){
        height = heightFromModel.toString()
    }

    LazyColumn {
        item {
            Column(verticalArrangement = Arrangement.Center) {
                TopAppBar(
                    title = { Text(text = "Update") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.backNavigation() }) { // TODO
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )

                Column(
                    modifier = modifier.padding(24.dp)
                ) {
                    OutlinedTextField(
                        value = children.data.name,
                        label = { Text("Nama") },
                        onValueChange = { },
                        readOnly = true,
                        modifier = modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = dateToDay(children.data.birthDay).toString(),
                        label = { Text("Usia") },
                        onValueChange = {},
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        },
                        readOnly = true,
                        modifier = modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = height,
                        label = { Text(text = "Tinggi Badan") },
                        onValueChange = { height = it },
                        trailingIcon = {
                            Text(text = "cm")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = weight,
                        label = { Text(text = "Berat Badan") },
                        onValueChange = { weight = it },
                        trailingIcon = {
                            Text(text = "kg")
                        },
                        isError = weight == "",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            try {
                                scope.launch {
                                    val response = withContext(Dispatchers.IO) {
                                        viewModel.updateChildren(
                                            id = children.data.id,
                                            weight = weight.toFloat(),
                                            height = height.toFloat()
                                        )
                                    }

                                    if (response.status) {
                                        showToast("Data Anak Gagal di Update", context)
                                    } else {
                                        navigator.backNavigation()
                                        showToast("Data Anak Berhasil di Update", context)
                                    }
                                }
                            } catch (e: HttpException) {
                                val jsonInString = e.response()?.errorBody()?.string()
                                val errorBody = Gson().fromJson(jsonInString, ApiResponse2::class.java)
                                val errorMessage = errorBody.message
                                showToast(errorMessage, context)
                                Log.d("Update Children", "Response: $e")
                            }
                        },
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    ) {
                        Text(text = "UPDATE", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text("Atau", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        ) {
                            Text(
                                "Gunakan Fitur AI Kami (Beta Version)",
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                            Text(
                                "Prediksi Pengukuran Tinggi Badan melalui  Foto Anak",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Button(
                        onClick = {
                            navigator.navigateToHeightMeasurement()
                        },
                        shape = RoundedCornerShape(4.dp),
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text("Prediksi Tinggi Badan")
                    }
                }
            }
        }
    }

}