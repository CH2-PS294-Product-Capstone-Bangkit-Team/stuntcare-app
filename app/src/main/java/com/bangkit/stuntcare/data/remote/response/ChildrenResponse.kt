package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChildrenResponse(

	@field:SerializedName("data")
	val data: DataChildrenResponse,

	@field:SerializedName("message")
	val message: String
)

data class DataChildrenResponse(

	@field:SerializedName("child")
	val child: List<ChildItem>
)

data class ChildItem(

	@field:SerializedName("birth_day")
	val birthDay: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("parent_id")
	val parentId: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("height")
	val height: Float,

	@field:SerializedName("weight")
	val weight: Float,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
)
