package com.bangkit.stuntcare.ui.model

import com.bangkit.stuntcare.R

data class Article(
    val image: Int,
    val title: String,
    val description: String,
    val date: String
)

val dummyArticle = listOf(
    Article(R.drawable.removebg_preview, "Udin", "Sehat Sehat Lelaki Kecil Ayah", "24 Aug")
)
