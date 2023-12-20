package com.bangkit.stuntcare.ui.view.parent.children.profile

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.data.remote.response.UserData
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ImageDialogPicker
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.stringToMediaType
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.bangkit.stuntcare.ui.view.parent.home.TopBarSection
import com.google.gson.Gson
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.time.LocalDate

@Composable
fun ChildrenProfileScreen(
    childrenId: String?,
    viewModel: ChildrenProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {

    viewModel.uiState.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                if (childrenId != null) {
                    viewModel.getChildrenById(childrenId)
                }
            }

            is UiState.Success -> {
                val data: DetailChildrenResponse = it.data

                ChildrenProfileContent(
                    childrenById = data,
                    navigator = navigator,
                    viewModel = viewModel
                )
            }

            is UiState.Error -> {

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChildrenProfileContent(
    childrenById: DetailChildrenResponse,
    navigator: ChildrenScreenNavigator,
    viewModel: ChildrenProfileViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUrl by remember {
        mutableStateOf(childrenById.data.imageUrl)
    }

    var isDialogImageShow by remember { mutableStateOf(false) }

    var currentImageUri: Uri? by remember { mutableStateOf(null) }
    var name by remember { mutableStateOf(childrenById.data.name) }
    var height by remember { mutableStateOf(childrenById.data.birthHeight.toString()) }
    var weight by remember { mutableStateOf(childrenById.data.birthWeight.toString()) }
    var genderOptions = listOf("Laki-laki", "Perempuan")
    var (selectedGender) = remember {
        mutableStateOf(if (genderOptions[0] == childrenById.data.gender) genderOptions[0] else genderOptions[1])
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            showToast("Izin telah diberikan", context)
        } else {
            showToast("Izin ditolak", context)
        }
    }

    val takeFromGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            currentImageUri = it
        } else {
            showToast("Tidak ada gambar dipilih", context)
        }
    }

    val uri = getImageUri(context = context)

    val takeFromCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            currentImageUri = uri
        }
    }

    Column {
        TopAppBar(
            title = {
                Text(text = "Update Profile Anak")
            },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Box(
                    modifier = modifier
                        .clickable {
                            isDialogImageShow = true
                        }
                ) {
                    AsyncImage(
                        model = if (currentImageUri == null) childrenById.data.imageUrl else currentImageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(160.dp)
                            .clip(CircleShape)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = modifier
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(24.dp)
        ) {
            OutlinedTextField(
                value = name,
                label = { Text("Nama") },
                onValueChange = { name = it },
                modifier = modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = childrenById.data.birthDay,
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                genderOptions.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.selectable(
                            selected = (it == selectedGender),
                            onClick = { })
                    ) {
                        RadioButton(
                            selected = it == selectedGender,
                            onClick = { })
                        Text(text = it)
                    }
                }
            }

            OutlinedTextField(
                value = weight,
                label = { Text(text = "Berat Badan") },
                onValueChange = { weight = it },
                trailingIcon = {
                    Text(text = "cm")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = height,
                label = { Text(text = "Tinggi Badan") },
                onValueChange = { height = it },
                trailingIcon = {
                    Text(text = "kg")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val nameBody = stringToMediaType(name)
                    val weightBody = stringToMediaType(weight)
                    val heightBody = stringToMediaType(height)
                    if (currentImageUri != null) {
                        currentImageUri?.let {
                            val imageFile = uriToFile(it, context).reduceFileImage()
                            val requestImageFile =
                                imageFile.asRequestBody("image/jpeg".toMediaType())

                            val multipartBody = MultipartBody.Part.createFormData(
                                "image",
                                imageFile.name,
                                requestImageFile
                            )

                            CoroutineScope(Dispatchers.Main).launch {
                                try {
                                    val response = withContext(Dispatchers.IO) {
                                        viewModel.updateProfileChildren(
                                            childrenById.data.id,
                                            multipartBody,
                                            nameBody,
                                            weightBody,
                                            heightBody
                                        )
                                    }

                                    if (!response.status) {
                                        showToast(response.message, context)
                                        navigator.backNavigation()
                                    } else {
                                        showToast("Data Belum Berhasil Ditambahkan", context)
                                    }

                                } catch (e: HttpException) {
                                    val jsonInString = e.response()?.errorBody()?.string()
                                    val errorBody = Gson().fromJson(
                                        jsonInString,
                                        ApiResponse2::class.java
                                    ).message
                                    Toast.makeText(context, errorBody, Toast.LENGTH_LONG).show()
                                    Log.d("Error", errorBody)
                                }
                            }
                        }
                    } else {
                        try {
                            scope.launch {
                                val response = runBlocking {
                                    viewModel.updateProfileChildren(
                                        childrenById.data.id,
                                        nameBody,
                                        weightBody,
                                        heightBody
                                    )
                                }
                                if (!response.status) {
                                    showToast(response.message, context)
                                    navigator.backNavigation()
                                } else {
                                    showToast("Data Belum Berhasil Ditambahkan", context)
                                }
                            }
                        } catch (e: HttpException) {
                            val jsonInString = e.response()?.errorBody()?.string()
                            val errorBody = Gson().fromJson(
                                jsonInString,
                                ApiResponse2::class.java
                            ).message
                            Toast.makeText(context, errorBody, Toast.LENGTH_LONG).show()
                            Log.d("Error", errorBody)
                        }
                    }
                },
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Text(text = "UPDATE", fontWeight = FontWeight.Medium, fontSize = 16.sp)
            }
        }

        AnimatedVisibility(
            visible = isDialogImageShow,
            modifier = modifier
                .fillMaxSize()
        ) {
            if (!checkPermission(Manifest.permission.CAMERA, context)) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }

            ImageDialogPicker(
                takeFromGallery = {
                    takeFromGallery.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                takeFromCamera = {
                    takeFromCamera.launch(uri)
                },
                isDialogImageShow = { isDialogImageShow = !isDialogImageShow })

        }
    }

}