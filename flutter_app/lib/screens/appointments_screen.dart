import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/data_provider.dart';

class AppointmentsScreen extends StatelessWidget {
  const AppointmentsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final data = Provider.of<DataProvider>(context);
    return Scaffold(
      appBar: AppBar(title: const Text('Appointments')),
      body: ListView.builder(
        itemCount: data.appointments.length,
        itemBuilder: (context, i) {
          final a = data.appointments[i];
          return ListTile(
            title: Text('${a.customerName} - ${a.serviceTitle}'),
            subtitle: Text('${a.date} - Stylist: ${a.stylistName}'),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(onPressed: () => Navigator.pushNamed(context, '/book'), child: const Icon(Icons.add)),
    );
  }
}

