package com.bangkit.stuntcare.ui.model

import com.google.gson.annotations.SerializedName

data class ChildrenPost(

	@field:SerializedName("birth_day")
	val birthDay: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("birth_weight")
	val birthWeight: Float,

	@field:SerializedName("img_base64")
	val imgBase64: String,

	@field:SerializedName("birth_height")
	val birthHeight: Float
)
