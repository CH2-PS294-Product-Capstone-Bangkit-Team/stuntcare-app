package com.bangkit.stuntcare.ui.model

import com.bangkit.stuntcare.R

data class Article(
    val id: String,
    val image: String,
    val title: String,
    val description: String,
    val date: String,
    val author: String
)

val dummyArticle = listOf(
    Article(
        "kaljdkajsdkaldj",
        "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/09/05032113/Stunting.jpg",
        "Judul Artilkel",
        "Deskripsi Artikel",
        "24 Aug",
        "Wahyuddin"
    ),
    Article(
        "kaljdkajsdkal2q",
        "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/09/05032113/Stunting.jpg",
        "Judul Artikel",
        "Deskripsi Artikel",
        "24 Aug",
        "Wahyuddin"
    ),
    Article(
        "kaljdkajsdkalbhfd",
        "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/09/05032113/Stunting.jpg",
        "Judul Artikel",
        "Deskripsi Artikel",
        "24 Aug",
        "Wahyuddin"
    ),
    Article(
        "kjladkjaklsdja",
        "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/09/05032113/Stunting.jpg",
        "Judul Artikel",
        "Deskripsi Artikel",
        "24 Aug",
        "Wahyuddin"
    ),
)
