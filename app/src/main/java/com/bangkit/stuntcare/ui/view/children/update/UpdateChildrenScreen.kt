package com.bangkit.stuntcare.ui.view.children.update

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import org.w3c.dom.Text

@Composable
fun UpdateChildrenScreen(
    childrenId: Int,
    navigator: ChildrenScreenNavigator,
    viewModel: UpdateChildrenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    viewModel.getChildrenById(childrenId).let {
        val children = viewModel.children.collectAsState().value
        if (children != null) {
            UpdateChildrenContent(
                children = children,
                navigator = navigator
            ) // TODO: Tempat Pemanggilan ViewMdoel
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateChildrenContent(
    children: Children,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    val weight by remember {
        mutableStateOf(children.weight.toString())
    }

    val height by remember {
        mutableStateOf(children.height.toString())
    }

    Column(verticalArrangement = Arrangement.Center, modifier = modifier.padding(12.dp)) {
        TopAppBar(
            title = { Text(text = "Update") },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) { // TODO
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
        OutlinedTextField(
            value = children.name,
            label = { Text("Nama") },
            onValueChange = { },
            readOnly = true,
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = children.age.toString(),
            label = { Text("Usia") },
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            readOnly = true,
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = weight,
            label = { Text(text = "Berat Badan") },
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = height,
                label = { Text(text = "Tinggi Badan") },
                onValueChange = {},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Tombol Kamera")
            }
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier.align(Alignment.CenterHorizontally).padding(8.dp)
        ) {
            Text(text = "UPDATE", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }
    }
}