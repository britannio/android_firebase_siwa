import 'dart:async';
import 'dart:io';

import 'package:android_firebase_siwa/src/models/siwa_result.dart';
import 'package:flutter/services.dart';

class AndroidFirebaseSiwa {
  AndroidFirebaseSiwa._();

  static const MethodChannel _channel =
      const MethodChannel('android_firebase_siwa');

  static AndroidFirebaseSiwa instance = AndroidFirebaseSiwa._();

  Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<SIWAResult> signInWithApple() async {
    if (!Platform.isAndroid) throw UnimplementedError();

    final Map<String, String> result =
        await _channel.invokeMapMethod('signInWithApple');

    return SIWAResult.fromJson(result);
  }
}
