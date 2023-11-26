package com.bangkit.stuntcare.data

import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.model.children.FakeChildren
import com.bangkit.stuntcare.ui.model.dummyDoctor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DataRepository {
    private val childList = mutableListOf<Children>()
    private val doctorList = mutableListOf<Doctor>()

    init {
        if (childList.isEmpty()){
            FakeChildren.dummyChildren.forEach {
                childList.add(Children(it.id, it.name, it.image, it.status, it.weight, it.height, it.age, it.updatedAt))
            }
        }

        if (doctorList.isEmpty()){
            dummyDoctor.forEach {
                doctorList.add(it)
            }
        }
    }

    // Data Children
    fun getAllChildren(): Flow<List<Children>>{
        return flowOf(childList)
    }

    fun getChildrenById(id: Int): Children{
        return childList.first {
            it.id == id
        }
    }

    fun getFirstChildren(): Children{
        return childList[0]
    }

    // Data Doctor
    fun getAllDoctor(): Flow<List<Doctor>>{
        return flowOf(doctorList)
    }

    fun getDoctorById(id: Int): Doctor{
        return doctorList.first{
            it.id == id
        }
    }

    fun searchDoctor(query: String): List<Doctor>{
        return doctorList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(): DataRepository =
            instance ?: synchronized(this) {
                DataRepository().apply {
                    instance =   this
                }
            }
    }
}