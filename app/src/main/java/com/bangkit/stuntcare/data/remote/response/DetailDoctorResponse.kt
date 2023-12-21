package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailDoctorResponse(

	@field:SerializedName("data")
	val data: DetailDoctorData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DetailDoctorData(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("hospital")
	val hospital: String,

	@field:SerializedName("experience")
	val experience: String,

	@field:SerializedName("no_str")
	val noStr: String,

	@field:SerializedName("status")
	val status: String
)
