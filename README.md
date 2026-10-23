# Jeano A.I Assistant

Android chat app with AI assistants powered by Google Gemini. Includes multiple chat personas, local message history and a developers page.

## Features

- Splash and landing screens
- Chat with Jeano, plus separate Breiah and Lee chat screens
- Gemini-generated replies via the Google Generative AI SDK
- Chat history stored locally with Room
- Bottom sheet UI
- Developers screen

## Tech stack

- Kotlin, Jetpack Compose, Material 3
- MVVM (ViewModel, Repository, DAO)
- Room (local database)
- Navigation Compose
- Google Generative AI SDK (`com.google.ai.client.generativeai`)
- Min SDK 30, compile SDK 35

## Structure

```
app/src/main/java/com/example/jeano/
  model/        Message models, Room database, DAO, repository, developers data
  viewmodel/    JeanoChatViewModel
  view/         Compose screens (Landing, Splash, Jeano/Breiah/Lee chat, Developers, BottomSheet)
  navigations/  Screens and NavGraph
  ui/theme/     Colors, typography, theme
```

## Setup

1. Open in Android Studio.
2. Add your own Gemini API key (see `JeanoChatViewModel.kt`). Do not commit real keys.
3. Sync Gradle, then run on a device or emulator (Android 11+).
