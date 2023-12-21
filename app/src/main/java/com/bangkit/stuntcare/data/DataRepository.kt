package com.bangkit.stuntcare.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.bangkit.stuntcare.data.pref.UserModel
import com.bangkit.stuntcare.data.pref.UserPreference
import com.bangkit.stuntcare.data.remote.ApiService
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
import com.bangkit.stuntcare.ui.model.Consultation
import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.model.Message
import com.bangkit.stuntcare.ui.model.User
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.model.children.FakeChildren
import com.bangkit.stuntcare.ui.model.dummyDoctor
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DataRepository(
    private val userPreference: UserPreference,
    private val apiServiceFromCc: ApiService,
    private val apiServiceGetStunting: ApiService,
    private val apiServiceFoodClassification: ApiService,
    private val apiServiceHighMeasurement: ApiService,
    private val apiServiceFoodRecommendation: ApiService
) {
    private val childList = mutableListOf<Children>()
    private val doctorList = mutableListOf<Doctor>()

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        if (childList.isEmpty()) {
            FakeChildren.dummyChildren.forEach {
                childList.add(
                    Children(
                        it.id,
                        it.name,
                        it.image,
                        it.status,
                        it.weight,
                        it.height,
                        it.age,
                        it.updatedAt
                    )
                )
            }
        }

        if (doctorList.isEmpty()) {
            dummyDoctor.forEach {
                doctorList.add(it)
            }
        }
    }

    // Data Children
    fun getAllChildren(): Flow<List<Children>> {
        return flowOf(childList)
    }

    suspend fun getAllChildrenNew(): Flow<ChildrenResponse> {
        return flowOf(apiServiceFromCc.getChildren(auth.currentUser?.uid))
    }

    suspend fun getChildrenByIdApi(childrenId: String): Flow<DetailChildrenResponse> {
        return flowOf(
            apiServiceFromCc.getChildrenById(
                auth.currentUser?.uid,
                childrenId,
            )
        )
    }

    suspend fun addChildrenFood(childrenId: String, image: MultipartBody.Part, foodName: RequestBody, schedule: RequestBody): ApiResponse2{
         return apiServiceFromCc.addFoodChildren(auth.currentUser?.uid, childrenId, image, foodName, schedule)
    }

    suspend fun getChildrenFood(childrenId: String): Flow<ChildrenFoodResponse> {
        return flowOf(apiServiceFromCc.getFoodChildren(childrenId, auth.currentUser?.uid))
    }

    suspend fun getChildrenFood2(childrenId: String): ChildrenFoodResponse {
        return apiServiceFromCc.getFoodChildren(childrenId, auth.currentUser?.uid)
    }

    suspend fun updateChildren(id: String, image: MultipartBody.Part, name: RequestBody, weight: RequestBody, height: RequestBody): ApiResponse2 {
        // TODO
        return apiServiceFromCc.updateGrowthChildren(auth.currentUser?.uid, id, image, name, weight, height)
    }

    suspend fun updateChildren(id: String, name: RequestBody, weight: RequestBody, height: RequestBody): ApiResponse2 {
        // TODO
        return apiServiceFromCc.updateGrowthChildren(userId = auth.currentUser?.uid, childrenId = id, name, weight = weight, height = height)
    }

    suspend fun updateChildren(id: String, weight: Float, height: Float): ApiResponse2 {
        // TODO
        return apiServiceFromCc.updateGrowthChildren(userId = auth.currentUser?.uid, childrenId = id, height = weight, weight =  height)
    }

    suspend fun statusChildren(
        age: Int,
        gender: String,
        weight: Float,
        height: Float
    ): ChildrenStatusResponse {
        return apiServiceGetStunting.getStatusChildren(age, gender, weight, height)
    }
    suspend fun addChildren(
        childrenImage: MultipartBody.Part,
        name: RequestBody,
        gender: RequestBody,
        birthDate: RequestBody,
        height: RequestBody,
        weight: RequestBody
    ): ApiResponse2 {
        // TODO
        return apiServiceFromCc.addChildren(
            userId = auth.currentUser?.uid,
            file = childrenImage,
            name = name,
            gender = gender,
            birthDay = birthDate,
            birthWeight = weight,
            birthHeight = height
        )
    }

    fun editChildren(childrenId: Int, height: Float, weight: Float) {
        // TODO
    }

    // Data Doctor
    suspend fun getAllDoctor(): Flow<DoctorResponse> {
        return flowOf(apiServiceFromCc.getDoctor())
    }

    suspend fun getDoctorById(id: String): Flow<DetailDoctorResponse> {
        return flowOf(apiServiceFromCc.getDoctorById(id))
    }

    fun searchDoctor(query: String): List<Doctor> {
        return doctorList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    // Login Method
    suspend fun saveSession(userModel: UserModel) {
        userPreference.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getUser(): UserResponse {
        return apiServiceFromCc.getUser(auth.currentUser?.uid)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }

    suspend fun register(user: User): Flow<ApiResponse2> {
        return flowOf(apiServiceFromCc.register(user))
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getFoodClassification(image: MultipartBody.Part): FoodClassificationResponse{
        return apiServiceFoodClassification.getFoodClassification(image)
    }

    suspend fun getHeightMeasurementPrediction(image: MultipartBody.Part): HighMeasurementPrediction{
        return apiServiceHighMeasurement.getHighMeasurement(image)
    }

    suspend fun getFoodRecommendation(): FoodRecommendationResponse{
        return apiServiceFoodRecommendation.getFoodRecommendation()
    }

    // Chat Feature
    fun showChat(userId: String) {
        firestore
            .collection(Consultation::class.java.simpleName)
            .whereArrayContains("parentId", userId)
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiServiceFromCc: ApiService,
            apiServiceGetStatus: ApiService,
            apiServiceFoodClassification: ApiService,
            apiServiceHighMeasurement: ApiService,
            apiServiceFoodRecommendation: ApiService
        ): DataRepository =
            instance ?: synchronized(this) {
                DataRepository(
                    userPreference,
                    apiServiceFromCc,
                    apiServiceGetStatus,
                    apiServiceFoodClassification,
                    apiServiceHighMeasurement,
                    apiServiceFoodRecommendation
                ).also {
                    instance = it
                }
            }
    }
}