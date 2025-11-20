import 'package:flutter/material.dart';
import '../models/stylist.dart';
import '../models/service.dart';
import '../models/appointment.dart';

class DataProvider extends ChangeNotifier {
  final List<Stylist> stylists = [
    Stylist(id: 1, name: 'Emma Thompson', title: 'Hair Styling', bio: 'Expert in modern hair styling with 8 years of experience.'),
    Stylist(id: 2, name: 'John Davis', title: 'Coloring Specialist', bio: 'Color expert with a passion for transformation.'),
    Stylist(id: 3, name: 'Sarah Wilson', title: 'Makeup Artist', bio: 'Professional makeup artist for all occasions.'),
  ];

  final List<ServiceModel> services = [
    ServiceModel(id: 1, title: 'Haircut', price: 45, durationMinutes: 45),
    ServiceModel(id: 2, title: 'Hair Spa', price: 75, durationMinutes: 90),
    ServiceModel(id: 3, title: 'Shave', price: 25, durationMinutes: 30),
    ServiceModel(id: 4, title: 'Makeup', price: 85, durationMinutes: 60),
  ];

  final List<Appointment> appointments = [
    Appointment(id: 1, customerName: 'Alice Brown', serviceTitle: 'Haircut', date: 'Nov 03, 2025', stylistName: 'Emma Thompson'),
    Appointment(id: 2, customerName: 'Bob Wilson', serviceTitle: 'Hair Spa', date: 'Nov 04, 2025', stylistName: 'John Davis'),
  ];

  void addAppointment(Appointment a) {
    appointments.add(a);
    notifyListeners();
  }
}

