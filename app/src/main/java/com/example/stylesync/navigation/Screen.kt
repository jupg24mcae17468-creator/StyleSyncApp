package com.example.stylesync.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object SignIn : Screen("signIn")
    object SignUp : Screen("signUp")
    object Home : Screen("home")
    object Stylists : Screen("stylists")
    object Services : Screen("services")
    object Appointments : Screen("appointments")
    object Booking : Screen("booking")
    object Profile : Screen("profile")
    object EditProfile : Screen("editProfile")
    object Favorites : Screen("favorites")
    object StylistDetail : Screen("stylist/{stylistId}") {
        fun createRoute(stylistId: Int) = "stylist/$stylistId"
    }
}
