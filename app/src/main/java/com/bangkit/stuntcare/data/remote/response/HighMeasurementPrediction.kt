package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class HighMeasurementPrediction(

	@field:SerializedName("data")
	val data: HighMeasurementData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class HighMeasurementData(

	@field:SerializedName("listHeight")
	val listHeight: List<Any>,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("listWidth")
	val listWidth: List<Any>,

	@field:SerializedName("tinggiBadan")
	val tinggiBadan: Float
)
