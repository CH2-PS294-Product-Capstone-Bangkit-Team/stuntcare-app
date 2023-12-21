package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class DoctorResponse(

	@field:SerializedName("data")
	val data: DoctorData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DoctorData(

	@field:SerializedName("doctor")
	val doctor: List<DoctorItem>
)

data class DoctorItem(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("hospital")
	val hospital: String,

	@field:SerializedName("experience")
	val experience: String,

	@field:SerializedName("no_str")
	val noStr: String,

	@field:SerializedName("status")
	val status: String
)
