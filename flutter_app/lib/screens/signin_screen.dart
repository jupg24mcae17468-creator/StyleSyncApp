import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/auth_provider.dart';

class SignInScreen extends StatefulWidget {
  const SignInScreen({Key? key}) : super(key: key);

  @override
  State<SignInScreen> createState() => _SignInScreenState();
}

class _SignInScreenState extends State<SignInScreen> {
  final _emailCtl = TextEditingController();
  final _pwCtl = TextEditingController();
  bool _loading = false;

  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthProvider>(context, listen: false);
    return Scaffold(
      appBar: AppBar(title: const Text('Sign In')),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(controller: _emailCtl, decoration: const InputDecoration(labelText: 'Email')),
            const SizedBox(height: 12),
            TextField(controller: _pwCtl, decoration: const InputDecoration(labelText: 'Password'), obscureText: true),
            const SizedBox(height: 24),
            ElevatedButton(onPressed: _loading ? null : () async {
              setState(() { _loading = true; });
              try {
                await auth.signIn(_emailCtl.text.trim(), _pwCtl.text);
                Navigator.pushReplacementNamed(context, '/home');
              } catch (e) {
                ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString())));
              } finally {
                setState(() { _loading = false; });
              }
            }, child: Text(_loading ? 'Signing in...' : 'Sign In'))
          ],
        ),
      ),
    );
  }
}

