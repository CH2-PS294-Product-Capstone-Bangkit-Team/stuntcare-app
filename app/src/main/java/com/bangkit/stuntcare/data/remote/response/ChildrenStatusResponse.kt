package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChildrenStatusResponse(

	@field:SerializedName("wasted")
	val wasted: Wasted,

	@field:SerializedName("stunting")
	val stunting: Stunting,

	@field:SerializedName("underweight")
	val underweight: Underweight
)

data class Wasted(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("recommendation")
	val recommendation: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class Underweight(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("recommendation")
	val recommendation: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class Stunting(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("recommendation")
	val recommendation: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
