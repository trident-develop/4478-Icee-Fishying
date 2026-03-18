# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Icee Fishying is a casual fishing game for Android, built with Kotlin and Jetpack Compose. The app is in early development — activity shells and game assets (fish sprites, coral, audio) are in place but core game logic is not yet implemented.

**Package:** `photo.editor.photoeditor.filtersforpictu`

## Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew test                   # Run unit tests
./gradlew connectedAndroidTest   # Run instrumented tests (requires device/emulator)
./gradlew app:testDebugUnitTest  # Run a specific test variant
```

## Architecture

- **UI Framework:** Jetpack Compose with Material 3
- **Entry point:** `LoadingActivity` (launcher) → `MainActivity` (main game screen)
- **Theme:** Material 3 with dynamic color support (Android 12+), custom font in `res/font/`
- **Build config:** Kotlin DSL (`.kts`), version catalog at `gradle/libs.versions.toml`
- **Min SDK 28 / Target SDK 36 / Compile SDK 36.1**

## Key Directories

- `app/src/main/java/com/zebr/` — Kotlin source (activities, theme)
- `app/src/main/res/drawable/` — Game assets (fish variants, coral, shells, octopus, UI buttons)
- `app/src/main/res/raw/` — Audio (game_music.mp3, collect_fish.mp3)
- `app/src/test/` — JUnit 4 unit tests
- `app/src/androidTest/` — Instrumented tests (AndroidJUnitRunner)
