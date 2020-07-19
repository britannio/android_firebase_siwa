# android_firebase_siwa

Provides Sign In With Apple functionality to Android apps using [firebase_auth](https://pub.dev/packages/firebase_auth)

# Usage

```dart
final FirebaseAuth firebaseAuth = FirebaseAuth.instance;
final AndroidFirebaseSiwa androidFirebaseSiwa = AndroidFirebaseSiwa.instance;

await androidFirebaseSiwa.signInWithApple();

final FirebaseUser user = firebaseAuth.currentUser();
```

Scopes are currently set to "name" and "email", more info [here](https://developer.apple.com/documentation/sign_in_with_apple/clientconfigi/3230955-scope)