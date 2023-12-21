@file:OptIn(ExperimentalComposeUiApi::class)

package com.bangkit.stuntcare.ui.view.parent.consultation.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.exp

@Composable
fun SetScheduleScreen(
    doctorId: String,
    navigator: ConsultationScreenNavigator,
    viewModel: SetScheduleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    viewModel.uiState.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAllChildren()
            }

            is UiState.Success -> {
                val children = it.data
                SetScheduleContent(children = children, navigator = navigator)
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetScheduleContent(
    children: List<Children>,
    navigator: ConsultationScreenNavigator,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var childrenName by remember {
        mutableStateOf("Pilih Anak")
    }

    var patientComplaint by remember {
        mutableStateOf("")
    }

    var isDialogDateShow by remember {
        mutableStateOf(false)
    }

    var isDialogTimeShow by remember {
        mutableStateOf(false)
    }

    var dateScheduling: LocalDate? by remember {
        mutableStateOf(null)
    }
    var timeScheduling: LocalTime? by remember {
        mutableStateOf(null)
    }

    Column {
        TopAppBar(
            title = {
                Text(text = "Atur Jadwal", fontWeight = FontWeight.Medium, fontSize = 20.sp)
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = modifier.size(24.dp).clickable {
                        navigator.backNavigation()
                    }
                )
            }
        )
        Column(
            modifier = modifier.padding(24.dp)
        ) {
            Button(onClick = { expanded = !expanded }) {
                Text(text = childrenName)
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    children.map {
                        DropdownMenuItem(
                            text = { Text(text = it.name) },
                            onClick = {
                                childrenName = it.name
                                expanded = !expanded
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = patientComplaint,
                label = { Text(text = "Masukkan Keluhan") },
                onValueChange = {},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = modifier
                    .fillMaxWidth()
                    .size(192.dp)
            )

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .border(BorderStroke(1.dp, LocalContentColor.current))
                    .clickable { isDialogDateShow = true }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = modifier.padding(12.dp)
                )
                Divider(
                    color = LocalContentColor.current,
                    modifier = modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                    Text(
                        text = if (dateScheduling != null) dateScheduling.toString() else "Pilih Tanggal Konsultasi",
                        modifier = modifier.padding(horizontal = 20.dp)
                    )
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(BorderStroke(1.dp, LocalContentColor.current))
                    .clickable { isDialogTimeShow = true }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = modifier.padding(12.dp)
                )
                Divider(
                    color = LocalContentColor.current,
                    modifier = modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Text(
                    text = if (timeScheduling != null) timeScheduling.toString() else "Pilih Jam Konsultasi",
                    modifier = modifier.padding(horizontal = 20.dp)
                )
            }
            
            Button(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(16.dp),
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Jadwalkan Sekarang", fontWeight = FontWeight.Medium, fontSize = 16.sp)
            }

            if (isDialogDateShow) {
                DatePickerDialog(
                    onDismissRequest = { isDialogDateShow = false },
                    onDateChange = {
                        dateScheduling = it
                        isDialogDateShow = false
                    },
                    title = { Text(text = "Pilih Tanggal Konsultasi") }
                )
            }

            if (isDialogTimeShow) {
                TimePickerDialog(
                    onDismissRequest = { isDialogTimeShow = false },
                    onTimeChange = {
                        timeScheduling = it
                        isDialogTimeShow = false
                    },
                    is24HourFormat = true,
                    title = { Text(text = "Pilih Jam Konsultasi") }
                )
            }
        }
    }
}