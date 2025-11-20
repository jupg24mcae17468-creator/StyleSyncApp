import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/auth_provider.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthProvider>(context);
    return Scaffold(
      appBar: AppBar(title: const Text('Profile'), actions: [IconButton(onPressed: () async { await auth.signOut(); Navigator.pushNamedAndRemoveUntil(context, '/home', (route) => false); }, icon: const Icon(Icons.logout))]),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Name: ${auth.user?.name ?? "Guest"}'),
            const SizedBox(height: 8),
            Text('Preferred Stylist: None'),
            const SizedBox(height: 8),
            Text('Loyalty Points: 0'),
            const SizedBox(height: 24),
            ElevatedButton(onPressed: () => Navigator.pushNamed(context, '/edit'), child: const Text('Edit Profile')),
            const SizedBox(height: 12),
            ElevatedButton(onPressed: () => Navigator.pushNamed(context, '/favorites'), child: const Text('View Favorites')),
          ],
        ),
      ),
    );
  }
}

