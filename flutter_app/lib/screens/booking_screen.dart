import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/data_provider.dart';
import '../models/appointment.dart';

class BookingScreen extends StatefulWidget {
  const BookingScreen({Key? key}) : super(key: key);

  @override
  State<BookingScreen> createState() => _BookingScreenState();
}

class _BookingScreenState extends State<BookingScreen> {
  String? _stylist;
  String? _service;
  DateTime? _date;

  @override
  Widget build(BuildContext context) {
    final data = Provider.of<DataProvider>(context);
    return Scaffold(
      appBar: AppBar(title: const Text('Book Appointment')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            DropdownButtonFormField<String>(
              items: data.stylists.map((s) => DropdownMenuItem(value: s.name, child: Text(s.name))).toList(),
              onChanged: (v) => setState(() => _stylist = v),
              hint: const Text('Choose Stylist'),
            ),
            const SizedBox(height: 12),
            DropdownButtonFormField<String>(
              items: data.services.map((s) => DropdownMenuItem(value: s.title, child: Text(s.title))).toList(),
              onChanged: (v) => setState(() => _service = v),
              hint: const Text('Choose Service'),
            ),
            const SizedBox(height: 12),
            ElevatedButton(onPressed: () async {
              final d = await showDatePicker(context: context, initialDate: DateTime.now(), firstDate: DateTime.now(), lastDate: DateTime.now().add(const Duration(days: 365)));
              if (d != null) setState(() => _date = d);
            }, child: const Text('Pick Date')),
            const SizedBox(height: 24),
            ElevatedButton(onPressed: () {
              if (_stylist == null || _service == null || _date == null) {
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Please select all fields')));
                return;
              }
              final ap = Appointment(id: DateTime.now().millisecondsSinceEpoch, customerName: 'You', serviceTitle: _service!, date: _date!.toLocal().toString().split(' ')[0], stylistName: _stylist!);
              data.addAppointment(ap);
              Navigator.pop(context);
            }, child: const Text('Confirm'))
          ],
        ),
      ),
    );
  }
}

