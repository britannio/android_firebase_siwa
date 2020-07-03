import Flutter
import UIKit

public class SwiftAndroidFirebaseSiwaPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "android_firebase_siwa", binaryMessenger: registrar.messenger())
    let instance = SwiftAndroidFirebaseSiwaPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
