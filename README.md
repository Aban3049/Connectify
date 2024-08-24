### Connectify

## Connectify is a Kotlin Multiplatform contacts application designed to manage and store contact information seamlessly across multiple platforms. Utilizing the power of the Room Database, Connectify ensures efficient and robust data persistence.
Features

Multiplatform Support: Built with Kotlin Multiplatform, Connectify runs smoothly on Android, iOS, and other platforms.
Room Database Integration: Leverages Room for efficient local data storage and management.
MVVM Architecture: Implements the Model-View-ViewModel pattern for a clean and maintainable codebase.
User-Friendly Interface: Provides an intuitive and easy-to-use interface for managing contacts.

Getting Started

  Clone the Repository:

    git clone https://github.com/yourusername/connectify.git

  Open in Android Studio: Import the project into Android Studio.
  Build and Run: Sync the project with Gradle files and run the app on your preferred device or emulator.

Dependencies

  Kotlin Multiplatform: For cross-platform development.
  Room Database: For local data storage.
  Kotlin Coroutines: For asynchronous programming.
  Jetpack Compose: For building the UI (if applicable).

Contributing

Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.


This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.
