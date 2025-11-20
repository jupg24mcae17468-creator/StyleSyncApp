package com.example.stylesync.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stylesync.navigation.Screen
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size

@Composable
fun StyleSyncBottomNavigation(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Stylists,
        BottomNavItem.Services,
        BottomNavItem.Appointments,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = items.map { it.route }
    if (currentRoute in bottomNavRoutes) {
        // Light gray background; indicator color will be applied per-item
        NavigationBar(
            containerColor = Color(0xFFF5F5F5) // light gray background
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(18.dp) // slightly smaller icon to free space for label
                        )
                    },
                    label = {
                        // Simple centered label to avoid using complex measurement APIs
                        Text(
                            text = item.title,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF512DA8), // deep purple for selected content
                        selectedTextColor = Color(0xFF512DA8),
                        unselectedIconColor = Color(0xFF616161), // gray
                        unselectedTextColor = Color(0xFF616161),
                        indicatorColor = Color(0xFFD1C4E9) // light purple selected highlight
                    ),
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    object Home : BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home)
    object Stylists : BottomNavItem(Screen.Stylists.route, "Stylists", Icons.Default.Person)
    object Services : BottomNavItem(Screen.Services.route, "Services", Icons.Default.ContentCut)
    object Appointments : BottomNavItem(Screen.Appointments.route, "Appointments", Icons.Default.Schedule)
    object Profile : BottomNavItem(Screen.Profile.route, "Profile", Icons.Default.AccountCircle)
}
