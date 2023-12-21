package com.bangkit.stuntcare.ui.view.parent.children.add

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.stuntcare.BuildConfig
import com.bangkit.stuntcare.MainActivity
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ImageDialogPicker
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.stringToMediaType
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import run.nabla.gallerypicker.permission.rememberRequestPermissionState
import run.nabla.gallerypicker.picker.GalleryHeader
import run.nabla.gallerypicker.picker.GalleryPicker
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Objects
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.theme.Green600
import com.bangkit.stuntcare.ui.theme.Grey100

@Composable
fun AddChildrenScreen(
    navigator: ChildrenScreenNavigator, viewModel: AddChildrenViewModel = viewModel(
        factory = ViewModelFactory.getInstance(
            LocalContext.current
        )
    )
) {
    AddChildrenContent(navigator = navigator, viewModel = viewModel)
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddChildrenContent(
    navigator: ChildrenScreenNavigator,
    viewModel: AddChildrenViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var currentImageUri: Uri? by remember { mutableStateOf(null) }
    var birthDate: LocalDate? by remember { mutableStateOf(null) }
    var isDialogImageShow by remember { mutableStateOf(false) }
    var isDialogDateShow by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val genderOptions = listOf("Laki-Laki", "Perempuan")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(genderOptions[0])
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
            currentImageUri = uri
            isDialogImageShow = !isDialogImageShow
        }
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (topBar, imgChildren, tfName, tfBirthDate, tvGenderOptions, rgGenderOptions, tfWeight, tfHeight, btnUpload, dialog) = createRefs()

        TopAppBar(
            title = { Text(text = "Tambahkan Anak") },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) { // TODO
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            modifier = modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        AsyncImage(
            model = if (currentImageUri != null) currentImageUri else "",
            contentDescription = null,
            contentScale = ContentScale.Inside,
            placeholder = painterResource(id = R.drawable.ic_camera),
            error = painterResource(id = R.drawable.ic_camera),
            modifier = modifier
                .clip(CircleShape)
                .size(150.dp)
                .clickable {
                    isDialogImageShow = true
                }
                .background(Grey100)
                .constrainAs(imgChildren) {
                    top.linkTo(topBar.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = name,
            label = { Text("Nama") },
            onValueChange = {
                name = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(tfName) {
                    top.linkTo(imgChildren.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
                .border(BorderStroke(1.dp, color = Color.Gray))
                .clickable { isDialogDateShow = true }
                .constrainAs(tfBirthDate) {
                    top.linkTo(tfName.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = modifier.padding(12.dp)
            )
            Divider(
                color = Color.Gray,
                modifier = modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = if (birthDate != null) birthDate.toString() else "Masukkan tanggal Lahir",
                modifier = modifier.padding(horizontal = 20.dp)
            )
        }

        Text(
            text = "Jenis Kelamin",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .constrainAs(tvGenderOptions) {
                    top.linkTo(tfBirthDate.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(horizontal = 16.dp)
                .constrainAs(rgGenderOptions) {
                    top.linkTo(tvGenderOptions.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        ) {
            genderOptions.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.selectable(
                        selected = (it == selectedGender),
                        onClick = { onGenderSelected(it) })
                ) {
                    RadioButton(
                        selected = it == selectedGender,
                        onClick = { onGenderSelected(it) })
                    Text(text = it)
                }
            }
        }

        OutlinedTextField(
            value = weight,
            label = { Text(text = "Berat Badan") },
            onValueChange = {
                weight = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 4.dp)
                .constrainAs(tfWeight) {
                    top.linkTo(rgGenderOptions.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 4.dp)
                .constrainAs(tfHeight) {
                    top.linkTo(tfWeight.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            OutlinedTextField(
                value = height,
                label = { Text(text = "Tinggi Badan") },
                onValueChange = {
                    height = it
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                },
                trailingIcon = {
                    Text(text = "CM")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        Button(
            onClick = {
                currentImageUri?.let {
                    val imageFile = uriToFile(it, context).reduceFileImage()

                    val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                    val nameBody = stringToMediaType(name)
                    val birthDateBody = stringToMediaType(birthDate.toString())
                    val weightBody = stringToMediaType(weight)
                    val heightBody = stringToMediaType(height)
                    val gender = stringToMediaType(selectedGender)

                    val multipartBody = MultipartBody.Part.createFormData(
                        "image",
                        imageFile.name,
                        requestImageFile
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val response = withContext(Dispatchers.IO){
                                viewModel.addChildren(
                                    multipartBody,
                                    nameBody,
                                    gender,
                                    birthDateBody,
                                    weightBody,
                                    heightBody
                                )
                            }

                            if (!response.status){
                                showToast(response.message, context)
                                navigator.backNavigation()
                            } else {
                                showToast("Data Belum Berhasil Ditambahkan", context)
                            }

                        }catch (e: HttpException){
                            val jsonInString = e.response()?.errorBody()?.string()
                            val errorBody = Gson().fromJson(jsonInString, ApiResponse2::class.java).message
                            Toast.makeText(context, errorBody, Toast.LENGTH_LONG).show()
                            Log.d("Error", errorBody)
                        }
                    }
                }
            },
            contentPadding = PaddingValues(8.dp),
            shape = RoundedCornerShape(4.dp),
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .constrainAs(btnUpload) {
                    top.linkTo(tfHeight.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = "TAMBAHKAN", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }

        AnimatedVisibility(visible = isDialogImageShow, modifier = modifier.constrainAs(dialog) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
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

        if (isDialogDateShow) {
            DatePickerDialog(
                onDismissRequest = { isDialogDateShow = false },
                onDateChange = {
                    birthDate = it
                    isDialogDateShow = false
                },
                title = { Text(text = "Pilih Tanggal Konsultasi") }
            )
        }
    }
}