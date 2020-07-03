package dev.britannio.android_firebase_siwa;


import android.app.Activity;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * AndroidFirebaseSiwaPlugin
 */
public class AndroidFirebaseSiwaPlugin implements FlutterPlugin, ActivityAware, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    private Activity activity;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "android_firebase_siwa");
        channel.setMethodCallHandler(this);
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "android_firebase_siwa");
        channel.setMethodCallHandler(new AndroidFirebaseSiwaPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull final Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("signInWithApple")) {
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("apple.com");
            provider.setScopes(new ArrayList<String>());

            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.signOut();

            auth.startActivityForSignInWithProvider(activity, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    AuthCredential appleCredential = authResult.getCredential();
                                    try {
                                        Map credential = reflectAppleCredential(appleCredential);

                                        result.success(credential);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        result.error("Credential reflection failed", e.getMessage(), null);
                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    result.error(e.getClass().getSimpleName(), e.getMessage(), null);
                                }
                            });
        } else {
            result.notImplemented();
        }
    }

    private Map reflectAppleCredential(AuthCredential authCredential) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method getIdToken = authCredential.getClass().getDeclaredMethod("getIdToken");
        Method getAccessToken = authCredential.getClass().getDeclaredMethod("getAccessToken");

        getIdToken.setAccessible(true);
        getAccessToken.setAccessible(true);

        String idToken = (String) getIdToken.invoke(authCredential);
        String accessToken = (String) getAccessToken.invoke(authCredential);

        Map credential = new HashMap();
        credential.put("idToken", idToken);
        credential.put("accessToken", accessToken);

        return credential;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

        activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        activity = null;
    }
}
