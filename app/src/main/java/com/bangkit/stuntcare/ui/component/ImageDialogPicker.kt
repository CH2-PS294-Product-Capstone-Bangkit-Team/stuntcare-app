package com.bangkit.stuntcare.ui.component

import android.Manifest
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.checkPermission
import com.bangkit.stuntcare.ui.utils.getImageUri
import com.bangkit.stuntcare.ui.utils.showToast

@Composable
fun ImageDialogPicker(
    takeFromGallery: () -> Unit,
    takeFromCamera: () -> Unit,
    isDialogImageShow: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Dialog(onDismissRequest = { isDialogImageShow() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                SectionText("Pilih Gambar")
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.clickable {
                            takeFromGallery()
                        }
                    ) {
                        AsyncImage(
                            model = "https://cdn-icons-png.flaticon.com/512/4503/4503941.png",
                            contentDescription = null,
                            modifier = modifier.size(48.dp)
                        )
                        Text(
                            text = "Ambil dari Gallery",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            modifier = modifier.padding(top = 4.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.clickable {
                            if(checkPermission(Manifest.permission.CAMERA, context)){
                                takeFromCamera()
                            }
                        }
                    ) {
                        AsyncImage(
                            model = "https://www.iconarchive.com/download/i107295/vexels/office/camera-photo.1024.png",
                            contentDescription = null,
                            modifier = modifier.size(48.dp)
                        )
                        Text(
                            text = "Ambil dari Kamera",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            modifier = modifier.padding(top = 4.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { isDialogImageShow() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
