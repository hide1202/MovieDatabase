![logo](arts/logo.png)

# 🎥 Movie Database

![Android CI](https://github.com/hide1202/MovieDatabase/workflows/Android%20CI/badge.svg?branch=develop)
[![codecov](https://codecov.io/gh/hide1202/MovieDatabase/branch/develop/graph/badge.svg)](https://codecov.io/gh/hide1202/MovieDatabase)

- Application for the practice of android development using Movie Database API

## 📋 Prerequisites

- Android Studio Koala | 2024.1.1 (stable)

## 📸 Screenshots
| splash | home | detail | search |
|---|---|---|---|
| ![splash](arts/splash.jpg) | ![home](arts/home.jpg) | ![detail](arts/detail.jpg) | ![search](arts/search.jpg) |


## 🛠️ Tech Stack
- Kotlin, and Coroutines, Flow
- [Compose](https://developer.android.com/compose): a modern toolkit for building native UI using Kotlin
- Jetpack
  - [Lifecycle](): for building lifecycle-aware components
  - [Navigation](https://developer.android.com/guide/navigation): manage a application navigation flow
  - [App Startup](https://developer.android.com/topic/libraries/app-startup): for initializing components
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): for loading and display pages of data from a larger dataset
  - [Room](https://developer.android.com/training/data-storage/room): for data storage persistence
- Architecture
  - MVVM Architecture (Model - View - ViewModel)
  - UDF (Unidirectional Data Flow)
- [Retrofit (w/OkHttp)](https://github.com/square/retrofit): for making network requests and fetching data
- [Moshi](https://github.com/square/moshi): for serialization/deserialization JSON request, or response
- [Glide](https://bumptech.github.io/glide/): for loading images of remote resources
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): for depedency injection
- [Timber](https://github.com/JakeWharton/timber): for logging

## ▶️ How to Run

- Create a file in the root of project with `external.properties` name.
- And write following property with your api key.

```
movie.database.api.key=YOUR_API_KEY
```

## 🚀 Future Plans

We are planning to extend this project to support **Compose Multiplatform**, 
enabling it to run seamlessly across multiple platforms like Android, iOS, and desktop. 

Stay tuned for updates!

## ⭐ Support

If you find this project helpful or interesting, please give it a **Star**! 🌟  
Your support motivates us to improve and add new features.

## References

- The Movie Database API : https://developers.themoviedb.org/3/
- Vector icons : https://www.flaticon.com/kr/
