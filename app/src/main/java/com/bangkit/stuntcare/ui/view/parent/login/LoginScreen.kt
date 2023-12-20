package com.bangkit.stuntcare.ui.view.parent.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.stuntcare.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserModel
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.theme.Blue50
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    LoginContent(
        loginViewModel = loginViewModel,
        navigateToHome = navigateToHome,
        navigateToRegister = navigateToRegister
    )
}

@Composable
fun LoginContent(
    loginViewModel: LoginViewModel,
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = modifier
                .padding(horizontal = 80.dp, vertical = 32.dp)
                .size(180.dp)
        )

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .wrapContentHeight()
        ) {
            ConstraintLayout(
                modifier = modifier
                    .background(Blue50)
                    .padding(horizontal = 24.dp)
                    .wrapContentHeight()
            ) {
                val (tvLogin, edtEmail, edtPassword, btnLogin, tvForgetPassword, tvRegister) = createRefs()
                Text(
                    text = "Masuk",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = modifier.constrainAs(tvLogin) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start)
                        bottom.linkTo(edtEmail.top, margin = 8.dp)
                    }
                )

                OutlinedTextField(
                    value = email,
                    label = { Text(text = "Email") },
                    placeholder = {
                        Text(
                            text = "Masukkan Email Anda",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic
                        )
                    },
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = modifier
                        .constrainAs(edtEmail) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(edtPassword.top, margin = 8.dp)
                        }
                        .fillMaxWidth()
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
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
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
//                            top.linkTo(edtEmail.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(tvForgetPassword.top)
                        }
                )

                TextButton(
                    onClick = { },
                    modifier = modifier.constrainAs(tvForgetPassword) {
                        bottom.linkTo(btnLogin.top, margin = 8.dp)
                        end.linkTo(parent.end)
                    }
                ) {
                    Text(
                        text = "Lupa Password?",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = {
                        loginViewModel.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    navigateToHome()
                                } else {
                                    showToast(it.exception.toString(), context)
                                }
                            }

                    },
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 24.dp),
                    shape = RoundedCornerShape(0.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(btnLogin) {
//                            top.linkTo(edtPassword.bottom, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(tvRegister.top, margin = 16.dp)
                        }
                ) {
                    Text(text = "Masuk")
                }

                Row(
                    modifier = modifier
                        .constrainAs(tvRegister) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = "Belum Punya Akun?",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Daftar",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navigateToRegister()
                            }
                    )
                }
            }
        }
    }
}