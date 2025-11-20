import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/data_provider.dart';

class StylistsScreen extends StatelessWidget {
  const StylistsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final data = Provider.of<DataProvider>(context);
    return Scaffold(
      appBar: AppBar(title: const Text('Stylists')),
      body: ListView.builder(
        itemCount: data.stylists.length,
        itemBuilder: (context, i) {
          final s = data.stylists[i];
          return ListTile(
            title: Text(s.name),
            subtitle: Text('${s.title} - ${s.bio}'),
            trailing: ElevatedButton(onPressed: () {
              // Book
              Navigator.pushNamed(context, '/book', arguments: s.name);
            }, child: const Text('Book')),
          );
        },
      ),
    );
  }
}

