package com.bangkit.stuntcare.ui.view.parent.children.food_classification

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.ChildrenFoodResponse
import com.bangkit.stuntcare.data.remote.response.FoodClassificationResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ImageDialogPicker
import com.bangkit.stuntcare.ui.component.ShowLoading
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.removeTrailingZeros
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.stringToMediaType
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.net.SocketTimeoutException

@Composable
fun FoodClassificationScreen(
    childrenId: String,
    schedule: String,
    navigator: ChildrenScreenNavigator,
    viewModel: FoodClassificationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    FoodClassificationContent(
        childrenId = childrenId,
        navigator = navigator,
        schedule = schedule,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodClassificationContent(
    childrenId: String,
    schedule: String,
    navigator: ChildrenScreenNavigator,
    viewModel: FoodClassificationViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentImageUri: Uri? by remember {
        mutableStateOf(null)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    var multipartBody: MultipartBody.Part? by remember {
        mutableStateOf(null)
    }

    var foodName by remember {
        mutableStateOf("Nama Makanan")
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
            currentImageUri = it
            isDialogImageShow = !isDialogImageShow
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

    Column {
        TopAppBar(
            title = { Text(text = "Ambil Gambar Makanan") },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) {
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
            Text(text = "${childrenId} $schedule")
            Text(
                text = "Pastikan mengambil gambar makanan dengan kecerahan yang baik",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                modifier = modifier.padding(bottom = 12.dp)
            )

            AsyncImage(
                model = if (currentImageUri != null) currentImageUri else "https://blog.eigeradventure.com/wp-content/uploads/2022/07/tips-foto-aesthetics-2.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .size(454.dp)
                    .clickable {
                        isDialogImageShow = true
                    }
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = "Hasil Klasifikasi Gambar",
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
                    text = "Jenis Makanan",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = modifier
                )
                Text(
                    text = foodName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = modifier
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Jenis Makanan",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = modifier
                )
                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 16.dp),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Text(text = "Detail", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        val foodNameBody: RequestBody = stringToMediaType(foodName)
                        val scheduleBody: RequestBody = stringToMediaType(schedule)

                        if (multipartBody != null){
                            try {
                                val response = viewModel.addFoodChildren(childrenId, multipartBody!!, foodNameBody, scheduleBody)
                                if (!response.status){
                                    showToast(response.message, context)
                                    navigator.backNavigation()
                                }
                            }catch (e: HttpException){
                                val jsonInString = e.response()?.errorBody()?.string()
                                val errorBody =
                                    Gson().fromJson(jsonInString, ApiResponse2::class.java)
                                val errorMessage = errorBody.message
                                showToast(errorMessage, context)
                                Log.d("Update Children", "Response: $e")
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = modifier.padding(vertical = 12.dp)
            ) {
                Text(text = "Tampilkan Hasil", fontWeight = FontWeight.Bold, fontSize = 12.sp)
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

            ShowLoading(state = isLoading)

            LaunchedEffect(key1 = currentImageUri) {
                currentImageUri?.let {
                    val imageFile = uriToFile(it, context).reduceFileImage()
                    val requestImageFile =
                        imageFile.asRequestBody("image/jpeg".toMediaType())

                    multipartBody = MultipartBody.Part.createFormData(
                        "image",
                        imageFile.name,
                        requestImageFile
                    )
                    scope.launch {
                        if (multipartBody != null){
                            val response = viewModel.getFoodClassification(multipartBody!!)
                            if (response.data != null){
                                foodName = response.data.category
                                showToast("Klasifikasi Berhasil", context)
                            }else{
                                showToast(response.status.message, context)
                            }
                        }
                    }
                }
            }
        }
    }
}