# StyleSync (Android \- Jetpack Compose)

A personal salon companion Android app rebuilt with Kotlin + Jetpack Compose. Implements authentication, stylist listings, services, appointment booking, and profile management following the original StyleSync design.

## Key features
1. Authentication: Sign In / Sign Up (Firebase optional)
2. Home: quick navigation to Stylists, Services, Appointments
3. Stylists: list with details and booking action
4. Services: service list with price and duration icons
5. Appointments: list, add (floating \+), persistent storage (Firestore or SQLite)
6. Profile: user info, preferred stylist, loyalty points, reminders toggle, logout

## Tech stack
1. Kotlin, Jetpack Compose
2. Navigation Compose
3. DataStore (preferences)
4. WorkManager
5. Coil (image loading)
6. Gson
7. JDK 17, Android SDK 34

## Project layout (important files)
1. `app/src/main/java/com/example/stylesync` \- app code
2. `app/src/main/java/com/example/stylesync/ui/screens` \- Compose screens (Home, Stylists, Services, Appointments, Profile)
3. `app/src/main/java/com/example/stylesync/ui/navigation` \- NavGraph and routes
4. `app/src/main/java/com/example/stylesync/data` \- repositories, models, local DB or Firestore integration
5. `app/build.gradle.kts` \- Gradle configuration and dependencies

## Requirements
1. Android Studio Narwhal 4 Feature Drop \| 2025.1.4 (Windows)
2. JDK 17
3. Android SDK 34
4. Optional: Firebase project (Auth / Firestore) if using cloud backend

## Screenshots
![IMG-20251120-WA0009](https://github.com/user-attachments/assets/57a770c2-7264-4972-b745-0c2d95257d51)
![IMG-20251120-WA0007](https://github.com/user-attachments/assets/73a0d8a0-f172-4622-af3e-9c6939cc293d)
![IMG-20251120-WA0008](https://github.com/user-attachments/assets/8c88ac8d-6694-4d40-a6fb-c1f28d58940a)
![IMG-20251120-WA0006](https://github.com/user-attachments/assets/10de9a33-50d8-466e-adf2-93b420256a8b)
![IMG-20251120-WA0004](https://github.com/user-attachments/assets/d4951009-0ec2-4f2f-9576-7c0ff05ac404)







