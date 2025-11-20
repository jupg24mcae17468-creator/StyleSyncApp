import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/data_provider.dart';

class ServicesScreen extends StatelessWidget {
  const ServicesScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final data = Provider.of<DataProvider>(context);
    return Scaffold(
      appBar: AppBar(title: const Text('Services')),
      body: ListView.builder(
        itemCount: data.services.length,
        itemBuilder: (context, i) {
          final s = data.services[i];
          return ListTile(
            leading: const Icon(Icons.access_time),
            title: Text('${s.title} - \$${s.price}'),
            subtitle: Text('${s.durationMinutes} min'),
            trailing: ElevatedButton(onPressed: () => Navigator.pushNamed(context, '/book', arguments: s.title), child: const Text('Book')),
          );
        },
      ),
    );
  }
}

