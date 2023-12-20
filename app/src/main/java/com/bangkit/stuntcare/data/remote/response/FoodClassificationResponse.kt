package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodClassificationResponse(

	@field:SerializedName("data")
	val data: FoodData?,

	@field:SerializedName("status")
	val status: Status
)

data class FoodData(

	@field:SerializedName("nutrition")
	val nutrition: Nutrition,

	@field:SerializedName("confidence")
	val confidence: Any,

	@field:SerializedName("category")
	val category: String
)

data class Nutrition(

	@field:SerializedName("karbohidrat_total__mg")
	val karbohidratTotalMg: String,

	@field:SerializedName("lemak_total__mg")
	val lemakTotalMg: String,

	@field:SerializedName("vitamin_B2__mg")
	val vitaminB2Mg: String,

	@field:SerializedName("kalium__mg")
	val kaliumMg: String,

	@field:SerializedName("natrium__mg")
	val natriumMg: String,

	@field:SerializedName("besi__mg")
	val besiMg: String,

	@field:SerializedName("vitamin_C__mg")
	val vitaminCMg: String,

	@field:SerializedName("kalsium__mg")
	val kalsiumMg: String,

	@field:SerializedName("seng__mg")
	val sengMg: String,

	@field:SerializedName("serat_pangan__mg")
	val seratPanganMg: String,

	@field:SerializedName("protein__mg")
	val proteinMg: String,

	@field:SerializedName("vitamin_B1__mg")
	val vitaminB1Mg: String,

	@field:SerializedName("vitamin_A__mg")
	val vitaminAMg: String,

	@field:SerializedName("vitamin_B3")
	val vitaminB3: String,

	@field:SerializedName("fosfor__mg")
	val fosforMg: String,

	@field:SerializedName("air__mg")
	val airMg: String,

	@field:SerializedName("abu__mg")
	val abuMg: String,

	@field:SerializedName("tembaga__mg")
	val tembagaMg: String,

	@field:SerializedName("energi__kkal")
	val energiKkal: String
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
