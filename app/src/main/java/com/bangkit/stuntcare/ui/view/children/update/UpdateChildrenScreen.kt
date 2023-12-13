package com.bangkit.stuntcare.ui.view.children.update

import android.widget.Toast
import com.bangkit.stuntcare.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import org.w3c.dom.Text
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
    children: Children,
    viewModel: UpdateChildrenViewModel,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    var weight by remember {
        mutableStateOf(children.weight.toString())
    }

    var height by remember {
        mutableStateOf(children.height.toString())
    }

    var updateState by remember {
        mutableStateOf(false)
    }

    Column(verticalArrangement = Arrangement.Center, modifier = modifier.padding(12.dp)) {
        TopAppBar(
            title = { Text(text = "Update") },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) { // TODO
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
        OutlinedTextField(
            value = children.name,
            label = { Text("Nama") },
            onValueChange = { },
            readOnly = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_child_menu),
                    contentDescription = null
                )
            },
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = children.age.toString(),
            label = { Text("Usia") },
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            readOnly = true,
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = weight,
            label = { Text(text = "Berat Badan") },
            onValueChange = { weight = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = height,
            label = { Text(text = "Tinggi Badan") },
            onValueChange = { height = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = {
                updateState = true
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(text = "UPDATE", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }
    }

    // TODO
    val context = LocalContext.current

    if (updateState){
        LaunchedEffect(key1 = updateState) {
            val response = viewModel.updateChildren(children.id, weight.toFloat(), height.toInt())
            if (response.status == "success") {
                navigator.backNavigation()
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                updateState = false
            } else {
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                updateState = false
            }
        }
    }
}