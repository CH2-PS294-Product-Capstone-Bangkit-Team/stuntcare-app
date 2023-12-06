package com.bangkit.stuntcare.ui.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.stuntcare.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun LoginScreen(
    loginGoogle: () -> Unit
) {
    LoginContent()
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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
                .clip(RoundedCornerShape(topStart = 100.dp))
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(horizontal = 24.dp)
                    .fillMaxSize()
            ) {
                val (tvLogin, tvEmail, edtEmail, tvPassword, edtPassword, btnLogin, tvRegister) = createRefs()
                Text(
                    text = "Masuk",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = modifier.constrainAs(tvLogin) {
                        top.linkTo(parent.top, margin = 60.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = "Email",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = modifier.constrainAs(tvEmail) {
                        top.linkTo(tvLogin.bottom, margin = 45.dp)
                        start.linkTo(parent.start)
                    }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = modifier
                        .constrainAs(edtEmail) {
                            top.linkTo(tvEmail.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                )

                Text(
                    text = "Password",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = modifier.constrainAs(tvPassword) {
                        top.linkTo(edtEmail.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(edtPassword) {
                            top.linkTo(tvPassword.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 24.dp),
                    modifier = modifier
                        .constrainAs(btnLogin) {
                            top.linkTo(edtPassword.bottom, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(text = "Masuk")
                }
                
                Row(
                    modifier = modifier
                        .constrainAs(tvRegister){
                            bottom.linkTo(parent.bottom, margin = 24.dp)
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
                        modifier = modifier.padding(start = 8.dp).clickable { /*TODO*/ }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    StuntCareTheme {
        LoginContent()
    }
}