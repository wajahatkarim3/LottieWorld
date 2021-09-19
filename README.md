# Lottie World
A Lottie animations playground app to mess around with animations and clean architecture with MVVM.

[![Tests](https://github.com/wajahatkarim3/LottieWorld/actions/workflows/unit_tests.yml/badge.svg)](https://github.com/wajahatkarim3/LottieWorld/actions/workflows/unit_tests.yml)

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://twitter.com/WajahatKarim">Wajahat Karim</a>
</div>
<br/>

## Video
https://github.com/wajahatkarim3/LottieWorld/blob/main/art/LottieWorld.mp4

<div align="center">
  <img src="/art/Explore-Light.png" width="230px" />  <img src="/art/News-Light.png" width="230px" />  <img src="/art/Animation-Light.png" width="230px" /> <br>
  <img src="/art/Explore-Dark.png" width="230px" />  <img src="/art/Profile-Dark.png" width="230px" />  <img src="/art/Login-Dark.png" width="230px" /> <br>
</div>

<br/>

## Features
* Loads the Featured, Recent, and Popular animations
* Allows users to add animations in Favorites
* Loads Featured Animators.
* Shows a list of news from the Blogs
* Allows users to Play/Pause/Control animation in details screen
* Allows users to scan QR code of animations and play in the app
* Allows users to share the URL of animation
* Comes in both light and dark mode.
* Creates a fake user logged in logout state for demo purposes.

## 📱 Download Demo on Android
Download the [APK file from here](https://github.com/wajahatkarim3/LottieWorld/blob/main/LottieWorld.apk?raw=true) on your Android phone and enjoy the Demo App :)

## Architecture
* Built with Modern Android Development practices (MVVM)
* Utilized Repository and Usecase patterns for data
* Includes unit tests for Repository, ViewModels, Usecases, API Service response.
* Include unit test for the DAO and Room database.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) - For Navigation between screens with Fragments
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps. **This is in the [main branch](https://github.com/wajahatkarim3/Imagine)**.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.
- [MockK](https://mockk.io) - For Mocking and Unit Testing
- [Lottie](https://airbnb.design/lottie/) - For Lottie Animations
- [Zxing Barcode Scanner](https://github.com/dm77/barcodescanner) - For QR Code scanning
- [Coil](https://coil-kt.github.io/coil/) - For Image loading

## 👨 Developed By

<a href="https://twitter.com/WajahatKarim" target="_blank">
  <img src="https://avatars1.githubusercontent.com/u/8867121?s=460&v=4" width="70" align="left">
</a>

**Wajahat Karim**

[![Twitter](https://img.shields.io/badge/-twitter-grey?logo=twitter)](https://twitter.com/WajahatKarim)
[![Web](https://img.shields.io/badge/-web-grey?logo=appveyor)](https://wajahatkarim.com/)
[![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@wajahatkarim3)
[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/wajahatkarim/)