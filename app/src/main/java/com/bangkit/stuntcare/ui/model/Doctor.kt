package com.bangkit.stuntcare.ui.model

data class Doctor(
    val id: Int,
    val name: String,
    val type: String,
    val image: String,
    val longExperience: Int,
    val university: String,
    val hospital: String,
    val strNumber: String,
    val price: String
)

val dummyDoctor = listOf<Doctor>(
    Doctor(
        1,
        "Dr. Udinda",
        "Dokter Anak",
        "https://media.licdn.com/dms/image/D5603AQGY7iHqYdVvlg/profile-displayphoto-shrink_800_800/0/1692083961975?e=1706140800&v=beta&t=k1PQSKGHPpz4MqVr0zLdRQOC_gyRonTm_wSDFi9LgV4",
        5,
        "Universitas Saranjana",
        "Rumah Sakit Saranjana",
        "12143214324",
        "Rp. 50.000"
    ),
    Doctor(
        2,
        "Dr. Zadry",
        "Dokter Gigi",
        "https://media.licdn.com/dms/image/D5603AQGir-vAUsDIww/profile-displayphoto-shrink_100_100/0/1690906713193?e=1706140800&v=beta&t=F5m3lQURYbjZleULr4tXDZXc8DTg66Z_rLEy5Fo4Swk",
        5,
        "Universitas Saranjana",
        "Rumah Sakit Saranjana",
        "12143214324",
        "Rp. 10.000"
    ),
    Doctor(
        3,
        "Dr. Afrizal",
        "Dokter Psikolog",
        "https://media.licdn.com/dms/image/C4D03AQGofD1RiINXzQ/profile-displayphoto-shrink_100_100/0/1628790933226?e=1706140800&v=beta&t=f0gUr6yd3z4fVdP3M3W1upQyMV2DeODxpJGKcjJyCHc",
        5,
        "Universitas Saranjana",
        "Rumah Sakit Saranjana",
        "12143214324",
        "Rp. 30.000"
    ),
    Doctor(
        4,
        "Dr. Afdal",
        "Dokter Saraf",
        "https://media.licdn.com/dms/image/D5603AQEFnxszIIg58A/profile-displayphoto-shrink_100_100/0/1696212541873?e=1706140800&v=beta&t=M6miNsXqi_WHWh2fQ-iy3ctEsjlefUVSec9aLbdAQoc",
        5,
        "Universitas Saranjana",
        "Rumah Sakit Saranjana",
        "12143214324",
        "Rp. 50.0540"
    ),
    Doctor(
        5,
        "Dr. Nasrul",
        "Dokter Tulang",
        "https://media.licdn.com/dms/image/D5603AQFBDD5O-3FugA/profile-displayphoto-shrink_100_100/0/1684221099887?e=1706140800&v=beta&t=oToOE6Xm0hBjJkRIpSb_xn285VTnzTX0UE1PrCs6CNM",
        5,
        "Universitas Saranjana",
        "Rumah Sakit Saranjana",
        "12143214324",
        "Rp. 50.5400"
    ),
    Doctor(
        6,
        "Dr. Fatkhul",
        "Dokter Jantung",
        "https://media.licdn.com/dms/image/D5603AQH70dQiIlPArw/profile-displayphoto-shrink_100_100/0/1676145794655?e=1706140800&v=beta&t=_gmTiO8DN-dvjX0on7P4ALUL5vj0CrBhILV8H07q-0E",
        5,
        "Universitas Saranjana",
        "Rumah Sakit Saranjana",
        "12143214324",
        "Rp. 51.000"
    ),
)