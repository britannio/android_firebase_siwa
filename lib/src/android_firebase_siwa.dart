import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';

class AndroidFirebaseSiwa {
  AndroidFirebaseSiwa._();

  static const MethodChannel _channel = MethodChannel('android_firebase_siwa');

  static AndroidFirebaseSiwa instance = AndroidFirebaseSiwa._();

  Future<void> signInWithApple() async {
    if (!Platform.isAndroid) throw UnimplementedError();
    await _channel.invokeMethod('signInWithApple');
  }
}
