package com.bangkit.stuntcare.ui.view.register

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.component.ImageDialogPicker
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import run.nabla.gallerypicker.picker.GalleryHeader
import run.nabla.gallerypicker.picker.GalleryPicker
import java.time.LocalDate

@Composable
fun RegisterScreen() {
    RegisterContent()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val genderOptions = listOf("Laki - Laki", "Perempuan")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(genderOptions[1])
    }

    val statusOptions = listOf("Ayah", "Ibu", "Wali")
    val (selectedStatus, onStatusSelected) = remember {
        mutableStateOf(statusOptions[1])
    }

    var currentImageUri: Uri? by remember { mutableStateOf(null) }
    var birthDate: LocalDate? by remember { mutableStateOf(null) }
    var showPicker by remember { mutableStateOf(false) }
    var isDialogDateShow by remember { mutableStateOf(false) }
    var fullName by remember {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    val passwordError = password != confirmPassword

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

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            showToast("Izin telah diberikan", context)
        } else {
            showToast("Izin ditolak", context)
        }
    }

    Column(
        modifier = modifier
    ) {
        TopAppBar(
            title = { Text(text = "Daftar") },
            navigationIcon = {
                IconButton(onClick = { }) { // TODO
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            modifier = modifier
        )

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 100.dp))
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                val (imgView, tvName, tvBirthDate, tvGender, rgGender, tvStatus, rgStatus, edtEmail, edtPassword, edtConfirmPassword, btnRegist) = createRefs()

                AsyncImage(
                    model = if (currentImageUri == null) "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTpx5xZmWDlIO-BOzbuGYTGFQ3wF8IRnPBjQ&usqp=CAU" else currentImageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .clip(CircleShape)
                        .size(150.dp)
                        .clickable {
                            showPicker = true
                        }
                        .constrainAs(imgView) {
                            top.linkTo(parent.top, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )


                OutlinedTextField(
                    value = fullName,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    label = { Text(text = "Masukkan Nama") },
                    onValueChange = {
                        fullName = it
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(tvName) {
                            top.linkTo(imgView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = if (birthDate == null) "" else birthDate.toString(),
                    label = {
                        Text(text = "Pilih tanggal Lahir")
                    },
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = {
                        IconButton(onClick = { isDialogDateShow = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(tvBirthDate) {
                            top.linkTo(tvName.bottom)
                            start.linkTo(tvName.start)
                            end.linkTo(tvName.end)
                        }
                        .padding(16.dp)
                )

                Text(
                    text = "Gender",
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .constrainAs(tvGender) {
                            top.linkTo(tvBirthDate.bottom)
                            start.linkTo(tvBirthDate.start)
                        }
                )
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(rgGender) {
                            top.linkTo(tvGender.bottom)
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

                Text(
                    text = "Status",
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .constrainAs(tvStatus) {
                            top.linkTo(rgGender.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                        })
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .constrainAs(rgStatus) {
                            top.linkTo(tvStatus.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    statusOptions.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier.selectable(
                                selected = (it == selectedStatus),
                                onClick = { onStatusSelected(it) })
                        ) {
                            RadioButton(
                                selected = it == selectedStatus,
                                onClick = { onStatusSelected(it) })
                            Text(text = it)
                        }
                    }
                }

                OutlinedTextField(
                    value = email,
                    label = { Text(text = "Masukkan Nama") },
                    onValueChange = {
                        email = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(edtEmail) {
                            top.linkTo(rgStatus.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = password,
                    label = { Text(text = "Masukkan Password") },
                    onValueChange = {
                        password = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(edtPassword) {
                            top.linkTo(edtEmail.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    label = { Text(text = "Masukkan Ulang Password", fontSize = 14.sp) },
                    onValueChange = {
                        confirmPassword = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        if (passwordError) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null
                            )
                        }
                    },
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) {
                            Text(text = "Password Tidak Sama")
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(edtConfirmPassword) {
                            top.linkTo(edtPassword.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp)
                )

                Button(
                    onClick = {
                        currentImageUri?.let {
                            val imageFile = uriToFile(it, context).reduceFileImage()
                            val requestImageFile =
                                imageFile.asRequestBody("image/jpeg".toMediaType())
                            val multipartBody = MultipartBody.Part.createFormData(
                                "photo",
                                imageFile.name,
                                requestImageFile
                            )

                            scope.launch {
                                try {
                                    val response = withContext(Dispatchers.IO) {
                                        // TODO
                                    }
                                } catch (e: HttpException) {
                                    // TODO
                                }
                            }
                        }
                    },
                    modifier = modifier
                        .padding(8.dp)
                        .constrainAs(btnRegist) {
                            top.linkTo(edtConfirmPassword.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(text = "Daftar", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }

                if (isDialogDateShow) {
                    DatePickerDialog(
                        onDismissRequest = { isDialogDateShow = false },
                        onDateChange = {
                            birthDate = it
                            isDialogDateShow = false
                        },
                        title = { Text(text = "Tanggal Lahir") }
                    )
                }
            }
        }
    }

    AnimatedVisibility(visible = showPicker, modifier = modifier.fillMaxSize()) {
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
            isDialogImageShow = { showPicker = !showPicker })

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


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    StuntCareTheme {
        RegisterContent()
    }
}