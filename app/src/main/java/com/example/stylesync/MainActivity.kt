package com.example.stylesync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stylesync.navigation.Screen
import com.example.stylesync.ui.auth.SignInScreen
import com.example.stylesync.ui.auth.SignUpScreen
import com.example.stylesync.ui.auth.WelcomeScreen
import com.example.stylesync.ui.navigation.StyleSyncBottomNavigation
import com.example.stylesync.ui.screens.AppointmentsScreen
import com.example.stylesync.ui.screens.BookingScreen
import com.example.stylesync.ui.screens.EditProfileScreen
import com.example.stylesync.ui.screens.HomeScreen
import com.example.stylesync.ui.screens.ProfileScreen
import com.example.stylesync.ui.screens.ServicesScreen
import com.example.stylesync.ui.screens.StylistsScreen
import com.example.stylesync.ui.theme.StyleSyncTheme
import com.example.stylesync.viewmodel.AuthViewModel
import com.example.stylesync.viewmodel.ProfileViewModel
import com.example.stylesync.viewmodel.StyleSyncViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StyleSyncApp()
        }
    }
}

@Composable
fun StyleSyncApp() {
    StyleSyncTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val styleSyncViewModel: StyleSyncViewModel = viewModel()
            val authViewModel: AuthViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()

            Scaffold(bottomBar = { StyleSyncBottomNavigation(navController) }) { innerPadding: PaddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Welcome.route,
                    modifier = Modifier.fillMaxSize(),
                    builder = {
                        composable(Screen.SignIn.route) {
                            SignInScreen(
                                viewModel = authViewModel,
                                onNavigateBack = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Welcome.route) { inclusive = true }
                                    }
                                },
                                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                                onSignInSuccess = { navController.navigate(Screen.Home.route) }
                            )
                        }

                        composable(Screen.SignUp.route) {
                            SignUpScreen(
                                viewModel = authViewModel,
                                onNavigateBack = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Welcome.route) { inclusive = true }
                                    }
                                },
                                onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                                onSignUpSuccess = { navController.navigate(Screen.Home.route) }
                            )
                        }

                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigateToStylists = { navController.navigate(Screen.Stylists.route) },
                                onNavigateToServices = { navController.navigate(Screen.Services.route) },
                                onNavigateToAppointments = { navController.navigate(Screen.Appointments.route) }
                            )
                        }

                        composable(Screen.Stylists.route) {
                            StylistsScreen(
                                viewModel = styleSyncViewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onStylistClick = { id -> /* TODO: implement */ }
                            )
                        }

                        composable(Screen.Services.route) {
                            ServicesScreen(
                                viewModel = styleSyncViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.Appointments.route) {
                            AppointmentsScreen(
                                viewModel = styleSyncViewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToBooking = { navController.navigate(Screen.Booking.route) }
                            )
                        }

                        composable(Screen.Booking.route) {
                            BookingScreen(
                                viewModel = styleSyncViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                viewModel = profileViewModel,
                                onNavigateToEditProfile = { navController.navigate(Screen.EditProfile.route) },
                                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                                onSignOut = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Profile.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.EditProfile.route) {
                            EditProfileScreen(
                                profileViewModel = profileViewModel,
                                stylesyncViewModel = styleSyncViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.Favorites.route) {
                            // simple placeholder: navigate back
                            navController.popBackStack()
                        }

                        composable(Screen.Welcome.route) {
                            WelcomeScreen(
                                onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
                            )
                        }
                    }
                )
            }
        }
    }
}
