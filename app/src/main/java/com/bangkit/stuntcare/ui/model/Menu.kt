package com.bangkit.stuntcare.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import com.bangkit.stuntcare.R

data class Menu(
    val imageMenu: Int,
    val title: String
)

val dummyMenu = listOf(
    Menu(R.drawable.menu_consultation,"Konsultasi"),
    Menu(R.drawable.menu_article,"Artikel"),
    Menu(R.drawable.menu_chat,"Chat")
)
