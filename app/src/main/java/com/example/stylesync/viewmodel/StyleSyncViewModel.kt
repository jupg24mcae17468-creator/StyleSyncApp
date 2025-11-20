package com.example.stylesync.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylesync.model.AppointmentModel
import com.example.stylesync.model.FakeData
import com.example.stylesync.model.ServiceModel
import com.example.stylesync.model.StylistModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class StyleSyncViewModel : ViewModel() {
    private val _stylists = MutableStateFlow(FakeData.stylists)
    val stylists: StateFlow<List<StylistModel>> = _stylists

    private val _services = MutableStateFlow(FakeData.services)
    val services: StateFlow<List<ServiceModel>> = _services

    private val _appointments = MutableStateFlow(FakeData.appointments)
    val appointments: StateFlow<List<AppointmentModel>> = _appointments

    fun getStylistById(id: Int): StylistModel? {
        return _stylists.value.find { it.id == id }
    }

    fun getServiceByName(name: String): ServiceModel? {
        return _services.value.find { it.name == name }
    }

    fun addAppointment(appointment: AppointmentModel) {
        viewModelScope.launch {
            val newAppointments = _appointments.value.toMutableList()
            newAppointments.add(appointment)
            _appointments.value = newAppointments
        }
    }

    fun createAppointment(
        customerName: String,
        serviceType: String,
        appointmentDateTime: Date,
        stylistName: String
    ): AppointmentModel {
        return AppointmentModel(
            id = (_appointments.value.maxOfOrNull { it.id } ?: 0) + 1,
            customerName = customerName,
            serviceType = serviceType,
            appointmentDateTime = appointmentDateTime,
            stylistName = stylistName
        )
    }
}
