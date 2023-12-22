package com.bangkit.stuntcare.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import com.bangkit.stuntcare.R

@Composable
fun GlideSplashScreen(
    navigateToHomePage: () -> Unit,
    navigateToWelcome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    Column {
        Box(
            modifier = modifier.fillMaxSize()
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                Icon(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = modifier.size(200.dp))
            }
        }
        LaunchedEffect(key1 = context){
            delay(2000)
            if (currentUser != null){
                navigateToHomePage()
            }else{
                navigateToWelcome()
            }
        }
    }
}