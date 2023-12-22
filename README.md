
# StuntCare App

Repository for Mobile App Capstone Bangkti 2023 Batch 2 CH2-P292

**StuntCare App** is android applications built using Kotlin as the programming language and Jetpack Compose as the UI.


## Screenshots

<img src="https://github.com/CH2-PS294-Product-Capstone-Bangkit-Team/image/blob/main/ui1.png" width="1000"/>

<img src="https://github.com/CH2-PS294-Product-Capstone-Bangkit-Team/image/blob/main/ui2.png" width="1000"/>

## Build With
- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://github.com/topics/jetpack-compose)
- [Retrofit2](https://github.com/square/retrofit)
- [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [GSON](https://github.com/google/gson)
- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
- [OkHttp3](https://square.github.io/okhttp/)
- [Authentication Firebase Service](https://firebase.google.com/docs/auth)
- [Storage Firebase Service](https://firebase.google.com/docs/storage)
- [Firestore Firebase Service](https://firebase.google.com/docs/firestore)
- [Fico Chart](https://github.com/patrykandpatrick/vico)
- [Data Store](https://developer.android.com/jetpack/androidx/releases/datastore?hl=id)

## StuntCare Structure
    .com.bangkit.stuntucare         # Root Package
    |-- data
    |   |--- di                     # Dependency Injection
    |   |--- pref                   # Shared Preference
    |   |--- remote                 # Remote Data Handler
    |
    |-- UI
        |--- common                 # State
        |--- component              # Component UI
        |--- model                  # Model 
        |--- navigation             # Navigation Handler
        |--- theme                  # Style for Compose
        |--- utils                  # Helper Code
        |--- view                   # View for App


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

<img src="https://www.jacklandrin.com/wp-content/uploads/2020/04/D98BE215-15C7-499F-B381-F30B9B1D0A4E.png" width="700"/>
