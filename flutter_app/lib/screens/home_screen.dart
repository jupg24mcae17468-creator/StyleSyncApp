import 'package:flutter/material.dart';
import 'stylists_screen.dart';
import 'services_screen.dart';
import 'appointments_screen.dart';
import 'profile_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _index = 0;
  final List<Widget> _pages = const [
    Center(child: Text('Home Content')),
    StylistsScreen(),
    ServicesScreen(),
    AppointmentsScreen(),
    ProfileScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_index],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _index,
        selectedItemColor: const Color(0xFF3F51B5),
        unselectedItemColor: Colors.grey,
        onTap: (i) => setState(() => _index = i),
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Stylists'),
          BottomNavigationBarItem(icon: Icon(Icons.room_service), label: 'Services'),
          BottomNavigationBarItem(icon: Icon(Icons.calendar_today), label: 'Appointments'),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline), label: 'Profile'),
        ],
      ),
      floatingActionButton: _index == 3 ? FloatingActionButton(
        onPressed: () => Navigator.pushNamed(context, '/book'),
        child: const Icon(Icons.add),
      ) : null,
    );
  }
}

