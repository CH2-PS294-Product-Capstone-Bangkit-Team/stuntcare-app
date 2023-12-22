package com.bangkit.stuntcare.ui.view.parent.register

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ImageDialogPicker
import com.bangkit.stuntcare.ui.model.User
import com.bangkit.stuntcare.ui.theme.Blue50
import com.bangkit.stuntcare.ui.theme.Blue500
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.gson.Gson
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
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    RegisterContent(
        viewModel = viewModel,
        navigateToLogin = navigateToLogin
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun RegisterContent(
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val genderOptions = listOf("Laki - Laki", "Perempuan")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val statusOptions = listOf("Ayah", "Ibu", "Wali")
    val (selectedStatus, onStatusSelected) = remember {
        mutableStateOf(statusOptions[0])
    }

    var currentImageUri: Uri? by remember { mutableStateOf(null) }
    var birthDate: LocalDate? by remember { mutableStateOf(null) }
    var showPicker by remember { mutableStateOf(false) }
    var isDialogDateShow by remember { mutableStateOf(false) }
    var fullName by remember {
        mutableStateOf("")
    }
    var address by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var numberPhone by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

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
    var register by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
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

    LazyColumn(contentPadding = PaddingValues(0.dp)) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TopAppBar(
                    title = { Text(text = "Daftar") },
                    navigationIcon = {
                        IconButton(onClick = { }) { // TODO
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    modifier = modifier
                )

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = modifier
                        .padding(horizontal = 80.dp, vertical = 32.dp)
                        .size(92.dp)
                )
            }

        }

        stickyHeader {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .height(20.dp)
                    .fillMaxWidth()
            )
        }

        item {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .fillMaxSize()
            ) {
                ConstraintLayout(
                    modifier = modifier
                        .background(Blue50)
                        .padding(horizontal = 24.dp)
                        .fillMaxSize()
                ) {
                    val (tvRegister, edtEmail, edtPassword, edtConfirmPassword, edtFullname, edtAddress, edtBirthdate, tvGender, rgGender, edtNumberPhone, tvStatus, rgStatus, btnRegister, rowLogin) = createRefs()

                    Text(
                        text = "Daftar",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = modifier.constrainAs(tvRegister) {
                            top.linkTo(parent.top, margin = 32.dp)
                            start.linkTo(parent.start)
                        }
                    )

                    OutlinedTextField(
                        value = email,
                        label = { Text(text = "Email") },
                        onValueChange = {
                            email = it
                        },
                        placeholder = {
                            Text(
                                text = "Masukkan Email Anda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtEmail) {
                                top.linkTo(tvRegister.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(top = 16.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        label = { Text(text = "Password") },
                        placeholder = {
                            Text(
                                text = "Masukkan Password Anda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        onValueChange = { password = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                if (isPasswordVisible) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_password_visible),
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_password_hidden),
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtPassword) {
                                top.linkTo(edtEmail.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
//                            bottom.linkTo(edtConfirmPassword.top)
                            }
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        label = { Text(text = "Konfirmasi Password") },
                        placeholder = {
                            Text(
                                text = "Masukkan Password Anda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        onValueChange = { confirmPassword = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                if (isPasswordVisible) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_password_visible),
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_password_hidden),
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = password != confirmPassword,
                        supportingText = {
                            if (password != confirmPassword) {
                                Text(text = "Password Tidak Sama")
                            }
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtConfirmPassword) {
                                top.linkTo(edtPassword.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
//                            bottom.linkTo(edtConfirmPassword.top)
                            }
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(text = "Nama Lengkap") },
                        placeholder = {
                            Text(
                                text = "Masukkan Nama Lengkap Anda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtFullname) {
                                top.linkTo(edtConfirmPassword.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text(text = "Alamat") },
                        placeholder = {
                            Text(
                                text = "Masukkan Alamat Anda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtAddress) {
                                top.linkTo(edtFullname.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtBirthdate) {
                                top.linkTo(edtAddress.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .clickable {
                                isDialogDateShow = true
                            }
                    ) {
                        OutlinedTextField(
                            value = if (birthDate != null) birthDate.toString() else "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = "Tanggal Lahir") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary // TODO
                                )
                            },
                            placeholder = { Text(text = "Masukkan Tanggal Lahir Anda") },
                            modifier = modifier
                                .fillMaxWidth()
                                .onFocusEvent {
                                    isDialogDateShow = it.isFocused == true
                                }
                        )
                    }

                    Text(
                        text = "Jenis Kelamin",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                            .constrainAs(tvGender) {
                                top.linkTo(edtBirthdate.bottom, margin = 16.dp)
                                start.linkTo(parent.start)
                            }
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
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

                    OutlinedTextField(
                        value = numberPhone,
                        onValueChange = { numberPhone = it },
                        placeholder = {
                            Text(
                                text = "Masukkan Nomor Handphone Anda (08XXXXXX)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        label = { Text(text = "Nomor Handphone") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier
                            .fillMaxWidth()
                            .constrainAs(edtNumberPhone) {
                                top.linkTo(rgGender.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Text(
                        text = "Hubungan Status",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                            .constrainAs(tvStatus) {
                                top.linkTo(edtNumberPhone.bottom, margin = 16.dp)
                                start.linkTo(parent.start)
                            }
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
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


                    Button(
                        onClick = {
                                val user = User(
                                    email = email,
                                    password = password,
                                    name = fullName,
                                    address = address,
                                    gender = selectedGender,
                                    birthDay = birthDate.toString(),
                                    cellulerNumber = numberPhone,
                                    status = selectedStatus
                                )

                                scope.launch {
                                    try {
                                        val response = withContext(Dispatchers.IO) {
                                            viewModel.registerUser(user)
                                        }

                                        if (!response.status) {
                                            showToast(response.message, context)
                                            navigateToLogin()
                                        }
                                    } catch (e: HttpException) {
                                        val jsonInString = e.response()?.errorBody()?.string()
                                        val errorBody =
                                            Gson().fromJson(
                                                jsonInString,
                                                HighMeasurementPrediction::class.java
                                            )
                                        val errorMessage = errorBody.message
                                        showToast(errorMessage, context)
                                        Log.d("Update Children", "Response: $e")
                                    } finally {
                                        isLoading = false
                                    }
                                }
                        },
                        shape = RoundedCornerShape(0.dp),
                        modifier = modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .constrainAs(btnRegister) {
                                top.linkTo(rgStatus.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        Text(
                            text = "Daftar",
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
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