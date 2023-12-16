package com.bangkit.stuntcare.ui.view.profile.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun DetailProfileScreen() {
    DetailProfileContent("asas")
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailProfileContent(
    user: Any, // TODO
    modifier: Modifier = Modifier
) {
    LazyColumn {
        stickyHeader {
            TopAppBar(
                title = { Text(text = "Informasi Pengguna") },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                }
            )
        }

        item {
            DetailUser()
        }
    }
}

@Composable
fun DetailUser(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val genderOptions = listOf<String>("Laki-Laki", "Perempuan")
    val (selectedOptions, onOptionSelected) = remember {
        mutableStateOf("")
    }

    val statusOptions = listOf("Ayah", "Ibu", "Wali")
    val (selectedStatus, onStatusSelected) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(top = 12.dp)
        ) {
            AsyncImage(
                model = null,
                contentDescription = null,
                error = painterResource(id = R.drawable.logo),
                modifier = modifier
                    .fillMaxWidth()
                    .size(100.dp)
            )
            Text(text = "Ubah Foto Profil", modifier = modifier.padding(top = 12.dp))
        }

        OutlinedTextField(
            value = "",
            label = {
                    Text(text = "Nama")
            },
            onValueChange = {},
            leadingIcon = {},
            trailingIcon = {},
            modifier = modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = "",
            label = {
                Text(text = "Email")
            },
            onValueChange = {},
            leadingIcon = {},
            trailingIcon = {},
            modifier = modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = "",
            label = {
                Text(text = "Tanggal Lahir")
            },
            onValueChange = {},
            leadingIcon = {},
            trailingIcon = {},
            modifier = modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Text(text = "Gender", modifier = modifier.padding(top = 8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            genderOptions.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.selectable(
                        selected = (it == selectedOptions),
                        onClick = { onOptionSelected(it) }
                    )
                ) {
                    RadioButton(
                        selected = (it == selectedOptions),
                        onClick = { onOptionSelected(it) },
                        modifier = modifier.padding(start = 4.dp)
                    )
                    Text(text = it, modifier = modifier.padding(horizontal = 4.dp))
                }
            }
        }

        Text(text = "Status", modifier = modifier.padding(top = 8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            statusOptions.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.selectable(
                        selected = (it == selectedOptions),
                        onClick = { onOptionSelected(it) }
                    )
                ) {
                    RadioButton(
                        selected = (it == selectedOptions),
                        onClick = { onOptionSelected(it) })
                    Text(text = it, modifier = modifier.padding(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailUserPreview() {
    StuntCareTheme {
        DetailProfileContent(user = "")
    }
}