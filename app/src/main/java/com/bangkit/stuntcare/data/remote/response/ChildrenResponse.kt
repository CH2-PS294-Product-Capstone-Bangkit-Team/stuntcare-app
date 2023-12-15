package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChildrenResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("birth_day")
	val birthDay: String,

	@field:SerializedName("growth_history")
	val growthHistory: List<GrowthHistoryItem>,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("parent_id")
	val parentId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("birth_weight")
	val birthWeight: Int,

	@field:SerializedName("food_recommendation")
	val foodRecommendation: List<String>,

	@field:SerializedName("child_daily_menu")
	val childDailyMenu: List<String>,

	@field:SerializedName("bmi_status")
	val bmiStatus: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("birth_height")
	val birthHeight: Int,

	@field:SerializedName("stunting_status")
	val stuntingStatus: String
)

data class GrowthHistoryItem(

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("children_id")
	val childrenId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("height")
	val height: Int
)
