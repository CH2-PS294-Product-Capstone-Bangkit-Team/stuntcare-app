package com.bangkit.stuntcare.ui.view.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.stuntcare.MainActivity
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.pref.UserModel
import com.bangkit.stuntcare.ui.utils.LoadingState
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginWithGoogleScreen(
    navigateToHomePage: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val status by loginViewModel.loadingState.collectAsState()
    val context = LocalContext.current
    val token = stringResource(id = R.string.default_web_client_id)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            loginViewModel.signInWithGoogleCredential(credential)
        } catch (e: ApiException){
            Log.w("Firebase", "Google Sign In Failed", e)
        }
    }
    
    Button(onClick = {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(googleSignInClient.signInIntent)
    }) {
        Text(text = "Login With Google")
    }

    when (status.status) {
        LoadingState.Status.SUCCESS -> {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null){
                navigateToHomePage()
            }
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        LoadingState.Status.FAILED -> {
            Toast.makeText(context, status.msg ?: "Error", Toast.LENGTH_SHORT).show()
        }
        else -> {}

    }
}