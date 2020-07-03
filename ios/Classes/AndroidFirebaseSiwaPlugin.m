#import "AndroidFirebaseSiwaPlugin.h"
#if __has_include(<android_firebase_siwa/android_firebase_siwa-Swift.h>)
#import <android_firebase_siwa/android_firebase_siwa-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "android_firebase_siwa-Swift.h"
#endif

@implementation AndroidFirebaseSiwaPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAndroidFirebaseSiwaPlugin registerWithRegistrar:registrar];
}
@end
