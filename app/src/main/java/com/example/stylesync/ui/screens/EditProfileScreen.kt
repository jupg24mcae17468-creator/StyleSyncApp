package com.example.stylesync.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.stylesync.ui.components.GradientBackground
import com.example.stylesync.ui.components.StyledButton
import com.example.stylesync.ui.components.StyledTextField
import com.example.stylesync.viewmodel.ProfileViewModel
import com.example.stylesync.viewmodel.StyleSyncViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel,
    stylesyncViewModel: StyleSyncViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val userName by profileViewModel.userName.collectAsState()
    val userGender by profileViewModel.userGender.collectAsState()
    val preferredStylist by profileViewModel.preferredStylist.collectAsState()
    val stylists = stylesyncViewModel.stylists.collectAsState(initial = emptyList())

    var nameInput by remember { mutableStateOf(userName) }
    var selectedGender by remember { mutableStateOf(userGender) }
    var selectedStylist by remember { mutableStateOf(preferredStylist) }
    var isGenderExpanded by remember { mutableStateOf(false) }
    var isStylistExpanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("Male", "Female", "Other", "Prefer not to say")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StyledTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = "Name"
                )

                ExposedDropdownMenuBox(
                    expanded = isGenderExpanded,
                    onExpandedChange = { isGenderExpanded = it }
                ) {
                    StyledTextField(
                        value = selectedGender,
                        onValueChange = {},
                        label = "Gender",
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isGenderExpanded,
                        onDismissRequest = { isGenderExpanded = false }
                    ) {
                        genderOptions.forEach { gender ->
                            DropdownMenuItem(
                                text = { Text(gender) },
                                onClick = {
                                    selectedGender = gender
                                    isGenderExpanded = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = isStylistExpanded,
                    onExpandedChange = { isStylistExpanded = it }
                ) {
                    StyledTextField(
                        value = selectedStylist,
                        onValueChange = {},
                        label = "Preferred Stylist",
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isStylistExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isStylistExpanded,
                        onDismissRequest = { isStylistExpanded = false }
                    ) {
                        stylists.value.forEach { stylist ->
                            DropdownMenuItem(
                                text = { Text(stylist.name) },
                                onClick = {
                                    selectedStylist = stylist.name
                                    isStylistExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StyledButton(
                        text = "Cancel",
                        onClick = onNavigateBack,
                        modifier = Modifier.weight(1f)
                    )

                    StyledButton(
                        text = "Save",
                        onClick = {
                            profileViewModel.updateUserName(nameInput)
                            profileViewModel.updateUserGender(selectedGender)
                            profileViewModel.updatePreferredStylist(selectedStylist)
                            Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                            onNavigateBack()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
