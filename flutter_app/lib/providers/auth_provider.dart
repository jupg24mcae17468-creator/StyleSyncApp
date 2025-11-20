import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/user.dart';

class AuthProvider extends ChangeNotifier {
  User? _user;
  bool get isLoggedIn => _user != null;
  User? get user => _user;

  Future<void> loadFromPrefs() async {
    final sp = await SharedPreferences.getInstance();
    final id = sp.getInt('user_id');
    final name = sp.getString('user_name');
    final email = sp.getString('user_email');
    if (id != null && name != null && email != null) {
      _user = User(id: id, name: name, email: email);
      notifyListeners();
    }
  }

  Future<void> signIn(String email, String password) async {
    // For demo: accept any non-empty; in real app verify hashes
    if (email.isNotEmpty && password.length >= 6) {
      final sp = await SharedPreferences.getInstance();
      sp.setInt('user_id', 1);
      sp.setString('user_name', 'Demo User');
      sp.setString('user_email', email);
      _user = User(id: 1, name: 'Demo User', email: email);
      notifyListeners();
    } else {
      throw Exception('Invalid credentials');
    }
  }

  Future<void> signUp(String name, String email, String password) async {
    // Demo: create user locally
    final sp = await SharedPreferences.getInstance();
    sp.setInt('user_id', 1);
    sp.setString('user_name', name);
    sp.setString('user_email', email);
    _user = User(id: 1, name: name, email: email);
    notifyListeners();
  }

  Future<void> signOut() async {
    final sp = await SharedPreferences.getInstance();
    await sp.clear();
    _user = null;
    notifyListeners();
  }
}

