package com.bangkit.stuntcare.data.remote

import com.bangkit.stuntcare.data.remote.response.ApiResponse
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.data.remote.response.ChildrenFoodResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.data.remote.response.DetailDoctorResponse
import com.bangkit.stuntcare.data.remote.response.DoctorResponse
import com.bangkit.stuntcare.data.remote.response.FoodClassificationResponse
import com.bangkit.stuntcare.data.remote.response.FoodRecommendationResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.data.remote.response.UserResponse
import com.bangkit.stuntcare.ui.model.ChildrenPost
import com.bangkit.stuntcare.ui.model.children.Children
import com.google.firebase.firestore.auth.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    // CC
    @GET("user/{id}/child/{childrenId}")
    suspend fun getChildrenById(
        @Path("id") parentId: String?,
        @Path("childrenId") childrenId: String
    ): DetailChildrenResponse

    @GET("user/{id}/child")
    suspend fun getChildren(
        @Path("id") parentId: String?
    ): ChildrenResponse

    @POST("/register")
    suspend fun register(
        @Body user: com.bangkit.stuntcare.ui.model.User
    ): ApiResponse2

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") userId: String?
    ): UserResponse

    @DELETE("user/{userId}/child/{id}")
    suspend fun deleteChildren(
        @Path("userId") userId: String?,
        @Path("id") childrenId: String
    ): ApiResponse2

    @Multipart
    @POST("user/{id}/child")
    suspend fun addChildren(
        @Path("id") userId: String?,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("birth_day") birthDay: RequestBody,
        @Part("birth_weight") birthWeight: RequestBody,
        @Part("birth_height") birthHeight: RequestBody
    ): ApiResponse2

    @Multipart
    @PUT("user/{id}/child/{childrenId}")
    suspend fun updateGrowthChildren(
        @Path("id") userId: String?,
        @Path("childrenId") childrenId: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("height") height: RequestBody,
        @Part("weight") weight: RequestBody
    ): ApiResponse2

    @Multipart
    @PUT("user/{id}/child/{childrenId}")
    suspend fun updateGrowthChildren(
        @Path("id") userId: String?,
        @Path("childrenId") childrenId: String,
        @Part("name") name: RequestBody,
        @Part("height") height: RequestBody,
        @Part("weight") weight: RequestBody
    ): ApiResponse2

    @Multipart
    @PUT("user/{id}/child/{childrenId}")
    suspend fun updateGrowthChildren(
        @Path("id") userId: String?,
        @Path("childrenId") childrenId: String,
        @Part("weight") weight: Float,
        @Part("height") height: Float,
    ): ApiResponse2

    // STUNTIN STATUS
    @FormUrlEncoded
    @POST("assessment")
    suspend fun getStatusChildren(
        @Field("day") day: Int,
        @Field("gender") gender: String,
        @Field("weight") weight: Float,
        @Field("height") height: Float
    ): ChildrenStatusResponse

    @Multipart
    @POST("prediction")
    suspend fun getFoodClassification(
        @Part file: MultipartBody.Part
    ): FoodClassificationResponse

    @Multipart
    @POST("predict")
    suspend fun getHighMeasurement(
        @Part file: MultipartBody.Part
    ): HighMeasurementPrediction

    @FormUrlEncoded
    @POST("recommendation")
    suspend fun getFoodRecommendation(
        @Field("user_id") userId: Int = 1
    ): FoodRecommendationResponse

    @Multipart
    @POST("user/{userId}/child/{childrenId}/menuharian")
    suspend fun addFoodChildren(
        @Path("userId") userId: String?,
        @Path("childrenId") childrenId: String,
        @Part file: MultipartBody.Part,
        @Part("food_name") foodName: RequestBody,
        @Part("schedule") schedule: RequestBody
    ): ApiResponse2

    @GET("user/{userId}/child/{childrenId}/menuharian")
    suspend fun getFoodChildren(
        @Path("childrenId") childrenId: String,
        @Path("userId") userId: String?
    ): ChildrenFoodResponse

    @GET("doctor")
    suspend fun getDoctor(): DoctorResponse

    @GET("doctor/{doctorId}")
    suspend fun getDoctorById(
        @Path("doctorId") doctorId: String
    ): DetailDoctorResponse
}