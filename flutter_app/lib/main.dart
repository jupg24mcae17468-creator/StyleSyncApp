import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'screens/welcome_screen.dart';
import 'providers/auth_provider.dart';
import 'providers/data_provider.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const StyleSyncApp());
}

class StyleSyncApp extends StatelessWidget {
  const StyleSyncApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthProvider()),
        ChangeNotifierProvider(create: (_) => DataProvider()),
      ],
      child: MaterialApp(
        title: 'StyleSync',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFF3F51B5)),
          useMaterial3: true,
          scaffoldBackgroundColor: const Color(0xFFFFF8F0),
          primaryColor: const Color(0xFF3F51B5),
        ),
        initialRoute: '/welcome',
        routes: {
          '/welcome': (_) => const WelcomeScreen(),
        },
      ),
    );
  }
}

