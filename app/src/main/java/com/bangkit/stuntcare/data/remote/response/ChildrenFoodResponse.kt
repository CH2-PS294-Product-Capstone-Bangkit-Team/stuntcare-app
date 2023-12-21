package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChildrenFoodResponse(

	@field:SerializedName("error")
	var error: Boolean = false,

	@field:SerializedName("data")
	val data: ChildrenFoodData,

	@field:SerializedName("message")
	val message: String
)

data class FoodItem(

	@field:SerializedName("schedule")
	val schedule: String,

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("image_url")
	val imageUrl: String?,

	@field:SerializedName("created_at")
	val createdAt: Long,

	@field:SerializedName("children_id")
	val childrenId: String
)

data class ChildrenFoodData(

	@field:SerializedName("food")
	val food: List<FoodItem>
)
