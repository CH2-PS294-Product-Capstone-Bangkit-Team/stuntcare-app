package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailChildrenResponse(

	@field:SerializedName("data")
	val data: UserData,

	@field:SerializedName("message")
	val message: String
)

data class UserData(

	@field:SerializedName("birth_day")
	val birthDay: String,

	@field:SerializedName("growth_history")
	val growthHistory: List<GrowthHistoryItem>,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("parent_id")
	val parentId: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("birth_weight")
	val birthWeight: Float,

	@field:SerializedName("food_recommendation")
	val foodRecommendation: List<Any>,

	@field:SerializedName("child_daily_menu")
	val childDailyMenu: List<Any>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("birth_height")
	val birthHeight: Float
)

data class GrowthHistoryItem(

	@field:SerializedName("weight")
	val weight: Float,

	@field:SerializedName("created_at")
	val createdAt: Long,

	@field:SerializedName("children_id")
	val childrenId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("height")
	val height: Float
)
