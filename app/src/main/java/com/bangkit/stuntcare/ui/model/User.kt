package com.bangkit.stuntcare.ui.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("birth_day")
	val birthDay: String,

	@field:SerializedName("celluler_number")
	val cellulerNumber: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: String
)
