package com.bangkit.stuntcare.ui.view.children.add

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.utils.reduceFileImage
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.utils.stringToMediaType
import com.bangkit.stuntcare.ui.utils.uriToFile
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import run.nabla.gallerypicker.picker.GalleryHeader
import run.nabla.gallerypicker.picker.GalleryPicker
import java.time.LocalDate

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
    var showPicker by remember { mutableStateOf(false) }
    var isDialogDateShow by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var addChildrenState by remember {
        mutableStateOf(false)
    }


    ConstraintLayout(
        modifier = modifier
    ) {
        val (topBar, imgChildren, tfName, tfBirthDate, tfWeight, tfHeight, btnUpload) = createRefs()
        TopAppBar(
            title = { Text(text = "Update") },
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
            model = if (currentImageUri == null) "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTpx5xZmWDlIO-BOzbuGYTGFQ3wF8IRnPBjQ&usqp=CAU" else currentImageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .size(150.dp)
                .clickable {
                    showPicker = true
                }
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
                    top.linkTo(tfBirthDate.bottom)
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
                addChildrenState = true
            },
            modifier = modifier
                .padding(8.dp)
                .constrainAs(btnUpload) {
                    top.linkTo(tfHeight.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = "UPDATE", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }

        if (showPicker != false) {
            GalleryPicker(
                onImageSelected = {
                    currentImageUri = it
                    showPicker = false
                },
                header = {
                    GalleryHeader(
                        onLeftActionClick = { showPicker = false }
                    )
                },
                modifier = modifier.fillMaxSize()
            )
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

    if (addChildrenState) {
        LaunchedEffect(key1 = addChildrenState) {
            currentImageUri?.let {
                val imageFile = uriToFile(it, context).reduceFileImage()

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val nameBody = stringToMediaType(name)
                val birthDateBody = stringToMediaType(birthDate.toString())
                val weightBody = stringToMediaType(weight)
                val heightBody = stringToMediaType(height)

                val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
                )

                val response = viewModel.addChildren(multipartBody, nameBody, birthDateBody, weightBody, heightBody)

                addChildrenState = if (response.status == "success"){
                    navigator.backNavigation()
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    false
                } else {
                    showToast(response.message, context)
                    false
                }

            }
        }
    }
}