import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:android_firebase_siwa/android_firebase_siwa.dart';

void main() {
  const MethodChannel channel = MethodChannel('android_firebase_siwa');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AndroidFirebaseSiwa.instance.platformVersion, '42');
  });
}
