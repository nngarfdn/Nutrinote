// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.GRADLE apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.KSP) version Versions.KSP apply false
    id(Plugins.GOOGLE_SERVICES) version Versions.GOOGLE_SERVICES apply false
    id(Plugins.FIREBASE_CRASHLYTICS) version Versions.FIREBASE_CRASHLYTICS apply false
}