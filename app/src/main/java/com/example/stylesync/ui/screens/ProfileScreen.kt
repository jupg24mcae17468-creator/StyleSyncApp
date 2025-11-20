package com.example.stylesync.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.stylesync.ui.components.GradientBackground
import com.example.stylesync.ui.components.StyledButton
import com.example.stylesync.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onSignOut: () -> Unit
) {
    val userName by viewModel.userName.collectAsState()
    val preferredStylist by viewModel.preferredStylist.collectAsState()
    val notificationEnabled by viewModel.notificationEnabled.collectAsState()
    val profilePictureUri by viewModel.profilePictureUri.collectAsState()
    val loyaltyPoints by viewModel.loyaltyPoints.collectAsState()

    var showSignOutDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updateProfilePictureUri(it.toString()) }
    }

    val scope = rememberCoroutineScope()

    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            title = { Text("Sign Out") },
            text = { Text("Are you sure you want to sign out?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Clear user preferences stored under "UserPrefs"
                        val sp = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        sp.edit().clear().apply()

                        // Also call viewModel signOut to clear any app state
                        scope.launch {
                            viewModel.signOut()
                        }

                        // Dismiss dialog and navigate
                        showSignOutDialog = false
                        onSignOut()

                        // User feedback
                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Sign Out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignOutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    IconButton(onClick = { showSignOutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        GradientBackground(
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    onClick = { imagePicker.launch("image/*") }
                ) {
                    if (profilePictureUri.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(profilePictureUri),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Add Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Info
                Text(
                    text = userName.ifEmpty { "Add Your Name" },
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Preferred Stylist: ${'$'}{preferredStylist.ifEmpty { \"None\" }}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Loyalty Points: ${'$'}loyaltyPoints",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Actions
                StyledButton(
                    text = "Edit Profile",
                    onClick = onNavigateToEditProfile
                )

                Spacer(modifier = Modifier.height(16.dp))

                StyledButton(
                    text = "View Favorites",
                    onClick = onNavigateToFavorites
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Notification Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                        Text("Appointment Reminders")
                    }
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = viewModel::updateNotificationEnabled
                    )
                }
            }
        }
    }
}
