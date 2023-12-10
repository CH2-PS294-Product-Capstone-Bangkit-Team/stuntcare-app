package com.bangkit.stuntcare.ui.model

import androidx.annotation.DrawableRes
import com.bangkit.stuntcare.R

data class Menu(
    val imageMenu: Int,
    val title: String
)

val dummyMenu = listOf(
    Menu(R.drawable.ic_child_menu,"Konsultasi"),
    Menu(R.drawable.ic_child_menu,"Artikel"),
    Menu(R.drawable.ic_child_menu,"Chat")
)
