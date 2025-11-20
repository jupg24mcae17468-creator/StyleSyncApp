import 'package:flutter/material.dart';
import 'signin_screen.dart';
import 'signup_screen.dart';

class WelcomeScreen extends StatelessWidget {
  const WelcomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final primary = const Color(0xFF3F51B5);
    return Scaffold(
      body: Container(
        width: double.infinity,
        padding: const EdgeInsets.symmetric(horizontal: 24),
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            colors: [Color(0xFFF3F1FF), Color(0xFFFFF1E0)],
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
          ),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.content_cut, size: 96, color: primary),
            const SizedBox(height: 24),
            Text('Welcome to StyleSync', style: Theme.of(context).textTheme.headlineSmall?.copyWith(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Text('Your personal salon companion', style: Theme.of(context).textTheme.bodyMedium, textAlign: TextAlign.center),
            const SizedBox(height: 48),
            ElevatedButton(
              style: ElevatedButton.styleFrom(minimumSize: const Size.fromHeight(48), backgroundColor: primary, shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(24))),
              onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const SignInScreen())),
              child: const Text('Sign In'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              style: ElevatedButton.styleFrom(minimumSize: const Size.fromHeight(48), backgroundColor: primary, shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(24))),
              onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const SignUpScreen())),
              child: const Text('Sign Up'),
            ),
          ],
        ),
      ),
    );
  }
}

