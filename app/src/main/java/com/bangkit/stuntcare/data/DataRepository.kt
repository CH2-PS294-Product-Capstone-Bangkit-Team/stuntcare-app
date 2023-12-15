package com.bangkit.stuntcare.data

import com.bangkit.stuntcare.data.pref.UserModel
import com.bangkit.stuntcare.data.pref.UserPreference
import com.bangkit.stuntcare.data.remote.ApiService
import com.bangkit.stuntcare.data.remote.response.ApiResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenResponse
import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.model.children.FakeChildren
import com.bangkit.stuntcare.ui.model.dummyDoctor
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DataRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val childList = mutableListOf<Children>()
    private val doctorList = mutableListOf<Doctor>()

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

    fun getChildrenById(id: String): Children {
        return childList.first {
            it.id == id
        }
    }

    suspend fun getChildrenByIdApi (id: String): ChildrenResponse {
        return apiService.getChildrenById(id)
    }

    fun getFirstChildren(): Children {
        return childList[0]
    }

    suspend fun updateChildren(id: String, weight: Float, height: Int): ApiResponse {
        // TODO
        return ApiResponse("success", "Data anak berhasil di update")
    }

    suspend fun addChildren(
        childrenImage: MultipartBody.Part,
        name: RequestBody,
        birthDate: RequestBody,
        height: RequestBody,
        weight: RequestBody
    ): ApiResponse {
        // TODO
        return ApiResponse("success", "Data anak berhasil di Tambahkan")
    }

    fun editChildren(childrenId: Int, height: Float, weight: Float) {
        // TODO
    }

    // Data Doctor
    fun getAllDoctor(): Flow<List<Doctor>> {
        return flowOf(doctorList)
    }

    fun getDoctorById(id: Int): Doctor {
        return doctorList.first {
            it.id == id
        }
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

    fun login(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): DataRepository =
            instance ?: synchronized(this) {
                DataRepository(userPreference, apiService).also {
                    instance = it
                }
            }
    }
}