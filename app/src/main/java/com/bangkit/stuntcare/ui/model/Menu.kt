package com.bangkit.stuntcare.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import com.bangkit.stuntcare.R

data class Menu(
    val id: Int,
    val imageMenu: Int,
    val title: String
)

val dummyMenu = listOf(
    Menu(1, R.drawable.menu_statistic_growth,"Statistik Pertumbuhan"),
    Menu(2, R.drawable.menu_children_data,"Data Anak"),
    Menu(3, R.drawable.menu_consultation,"Konsultasi"),
    Menu(4, R.drawable.menu_article,"Komunitas")
)
