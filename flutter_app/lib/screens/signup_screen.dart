import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/auth_provider.dart';

class SignUpScreen extends StatefulWidget {
  const SignUpScreen({Key? key}) : super(key: key);

  @override
  State<SignUpScreen> createState() => _SignUpScreenState();
}

class _SignUpScreenState extends State<SignUpScreen> {
  final _nameCtl = TextEditingController();
  final _emailCtl = TextEditingController();
  final _pwCtl = TextEditingController();
  final _pw2Ctl = TextEditingController();
  bool _loading = false;

  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthProvider>(context, listen: false);
    return Scaffold(
      appBar: AppBar(title: const Text('Sign Up')),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              TextField(controller: _nameCtl, decoration: const InputDecoration(labelText: 'Name')),
              const SizedBox(height: 12),
              TextField(controller: _emailCtl, decoration: const InputDecoration(labelText: 'Email')),
              const SizedBox(height: 12),
              TextField(controller: _pwCtl, decoration: const InputDecoration(labelText: 'Password'), obscureText: true),
              const SizedBox(height: 12),
              TextField(controller: _pw2Ctl, decoration: const InputDecoration(labelText: 'Confirm Password'), obscureText: true),
              const SizedBox(height: 24),
              ElevatedButton(onPressed: _loading ? null : () async {
                if (_pwCtl.text != _pw2Ctl.text) {
                  ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Passwords do not match')));
                  return;
                }
                setState(() { _loading = true; });
                try {
                  await auth.signUp(_nameCtl.text.trim(), _emailCtl.text.trim(), _pwCtl.text);
                  Navigator.pushReplacementNamed(context, '/home');
                } finally {
                  setState(() { _loading = false; });
                }
              }, child: Text(_loading ? 'Registering...' : 'Register'))
            ],
          ),
        ),
      ),
    );
  }
}

