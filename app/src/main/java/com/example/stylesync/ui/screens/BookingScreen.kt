package com.example.stylesync.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stylesync.viewmodel.StyleSyncViewModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    viewModel: StyleSyncViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val services by viewModel.services.collectAsStateWithLifecycle(initialValue = emptyList())
    val stylists by viewModel.stylists.collectAsStateWithLifecycle(initialValue = emptyList())

    var name by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf("") }
    var selectedStylist by remember { mutableStateOf("") }
    var isServiceExpanded by remember { mutableStateOf(false) }
    var isStylistExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = isServiceExpanded,
                onExpandedChange = { isServiceExpanded = !isServiceExpanded }
            ) {
                OutlinedTextField(
                    value = selectedService,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Service") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isServiceExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isServiceExpanded,
                    onDismissRequest = { isServiceExpanded = false }
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            text = { Text("${service.name} - $${service.price}") },
                            onClick = {
                                selectedService = service.name
                                isServiceExpanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = isStylistExpanded,
                onExpandedChange = { isStylistExpanded = !isStylistExpanded }
            ) {
                OutlinedTextField(
                    value = selectedStylist,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Stylist") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isStylistExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isStylistExpanded,
                    onDismissRequest = { isStylistExpanded = false }
                ) {
                    stylists.forEach { stylist ->
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

            Button(
                onClick = {
                    if (name.isBlank() || selectedService.isBlank() || selectedStylist.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, 10) // Set appointment for 10 AM
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    val appointment = viewModel.createAppointment(
                        customerName = name,
                        serviceType = selectedService,
                        appointmentDateTime = Date(calendar.timeInMillis),
                        stylistName = selectedStylist
                    )
                    viewModel.addAppointment(appointment)
                    Toast.makeText(context, "Appointment Booked Successfully", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Booking")
            }
        }
    }
}
