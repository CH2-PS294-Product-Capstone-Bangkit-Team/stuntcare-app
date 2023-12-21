package com.bangkit.stuntcare.ui.view.parent.children.high_measurement

import android.Manifest
import android.net.Uri
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.FoodClassificationResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ImageDialogPicker
import com.bangkit.stuntcare.ui.component.ShowLoading
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.removeTrailingZeros
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import kotlin.IllegalStateException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighMeasurementScreen(
    childrenScreenNavigator: ChildrenScreenNavigator,
    viewModel: HighMeasurementPredictionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentImageUri: Uri? by remember {
        mutableStateOf(null)
    }
    var height: Float? by remember {
        mutableStateOf(null)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var heightMeasurementPrediction: Float? by remember {
        mutableStateOf(null)
    }

    var isDialogImageShow by remember { mutableStateOf(false) }
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
            isDialogImageShow = !isDialogImageShow
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
            isDialogImageShow = !isDialogImageShow
            currentImageUri = uri
        }
    }

    val text = buildAnnotatedString {
        append(stringResource(id = R.string.hight_guide))
        pushStringAnnotation(tag = "toGuide", annotation = "toguide")
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = Color.Red
            )
        ) {
            append("halaman tutorial ini")
        }
    }
    val uriHandler = LocalUriHandler.current

    Column {
        TopAppBar(
            title = { Text(text = "Ambil Foto Anak") },
            navigationIcon = {
                IconButton(onClick = { childrenScreenNavigator.backNavigation() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            ClickableText(text = text, onClick = {
                text.getStringAnnotations(tag = "toGuide", start = it, end = it).firstOrNull()
                    .let {
                        uriHandler.openUri("") // TODO
                    }
            })

            AsyncImage(
                model = if (currentImageUri != null) currentImageUri else "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.image_placeholder),
                placeholder = painterResource(id = R.drawable.image_placeholder),
                modifier = modifier
                    .fillMaxWidth()
                    .size(454.dp)
                    .clickable {
                        isDialogImageShow = true
                    }
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = "Hasil Predisksi Tinggi Gambar",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(top = 32.dp, bottom = 12.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
            ) {
                Text(
                    text = "Tinggi Badan",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = modifier
                )
                Text(
                    text = if (heightMeasurementPrediction != null) heightMeasurementPrediction.toString() else "-",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = modifier
                )
            }

            Button(
                onClick = {
                    if (heightMeasurementPrediction != null) {
                        childrenScreenNavigator.backNavigationWithHeight(heightMeasurementPrediction)
                    } else {
                        showToast("Belum ada data yang ditampilkan", context)
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = modifier.padding(vertical = 12.dp)
            ) {
                Text(text = "Gunakan Hasil", fontWeight = FontWeight.Bold, fontSize = 12.sp)
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
                    isDialogImageShow = { isDialogImageShow = !isDialogImageShow }
                )
            }


            LaunchedEffect(key1 = currentImageUri) {
                currentImageUri?.let {
                    val imageFile = uriToFile(it, context).reduceFileImage()
                    val requestImageFile =
                        imageFile.asRequestBody("image/jpeg".toMediaType())

                    val multipartBody = MultipartBody.Part.createFormData(
                        "file",
                        imageFile.name,
                        requestImageFile
                    )
                    viewModel.getHeight.value.let {
                        when (it) {
                            is UiState.Loading -> {
                                viewModel.getHeightMeasurementPrediction(multipartBody)
                                isLoading = true
                            }

                            is UiState.Success -> {
                                try {
                                    val response = it.data
                                    if (response.status == "success") {
                                        showToast(response.message, context)
                                    } else {
                                        showToast("Data Belum Berhasil Ditambahkan", context)
                                    }
                                    heightMeasurementPrediction = response.data.tinggiBadan
                                    isLoading = false
                                } catch (e: HttpException) {
                                    isLoading = false
                                    val jsonInString = e.response()?.errorBody()?.string()
                                    val errorBody = Gson().fromJson(
                                        jsonInString,
                                        HighMeasurementPrediction::class.java
                                    )
                                    val errorMessage = errorBody.message
                                    showToast(errorMessage, context)
                                }
                            }

                            is UiState.Error -> {
                                showToast("Silahkan Masukkan Gambar Yang Valid", context)
                            }
                        }
                    }
                }
            }
        }
    }
    ShowLoading(state = isLoading, modifier.fillMaxSize())
}

