package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodRecommendationResponse(

	@field:SerializedName("predicted_rating")
	val predictedRating: List<Float>,

	@field:SerializedName("fiber")
	val fiber: List<Float>,

	@field:SerializedName("vitamin_a")
	val vitaminA: List<Float>,

	@field:SerializedName("carbs")
	val carbs: List<Float>,

	@field:SerializedName("food_code")
	val foodCode: List<Int>,

	@field:SerializedName("vitamin_c")
	val vitaminC: List<Float>,

	@field:SerializedName("vitamin_d")
	val vitaminD: List<Float>,

	@field:SerializedName("vitamin_e")
	val vitaminE: List<Float>,

	@field:SerializedName("vitamin_b6")
	val vitaminB6: List<Float>,

	@field:SerializedName("calories")
	val calories: List<Float>,

	@field:SerializedName("type")
	val type: List<String>,

	@field:SerializedName("protein")
	val protein: List<Float>,

	@field:SerializedName("fat")
	val fat: List<Float>,

	@field:SerializedName("name")
	val name: List<String>,

	@field:SerializedName("vitamin_b12")
	val vitaminB12: List<Float>,

	@field:SerializedName("category")
	val category: List<String>,

	@field:SerializedName("sugar")
	val sugar: List<Float>
)
