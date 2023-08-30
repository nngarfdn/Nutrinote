/**
 * Credit to Igor Wojda
 * https://proandroiddev.com/multiple-ways-of-defining-clean-architecture-layers-bbb70afa5d4a
 * */

object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_PARCELIZE = "org.jetbrains.kotlin.plugin.parcelize"
    const val KSP = "com.google.devtools.ksp"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase.crashlytics"
    const val GOOGLE_SERVICES = "com.google.gms.google-services"
}

object Dependencies {
    // Android Jetpack
    val CORE by lazy { "androidx.core:core-ktx:${Versions.CORE}" }
    val APP_COMPAT by lazy { "androidx.appcompat:appcompat:${Versions.APP_COMPAT}" }
    val MATERIAL by lazy { "com.google.android.material:material:${Versions.MATERIAL}" }
    val LIVEDATA by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIVEDATA}" }

    // Data storage
    val ROOM_RUNTIME by lazy { "androidx.room:room-runtime:${Versions.ROOM}" }
    val ROOM_COMPILER by lazy { "androidx.room:room-compiler:${Versions.ROOM}" }

    // Database encryption
    val SQLCHIPHER by lazy { "net.zetetic:android-database-sqlcipher:${Versions.SQLCHIPHER}" }
    val SQLITE by lazy { "androidx.sqlite:sqlite-ktx:${Versions.SQLIITE}" }

    // Networking
    val RETROFIT by lazy { "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}" }

    // Parsing the JSON format
    val MOSHI by lazy { "com.squareup.moshi:moshi:${Versions.MOSHI}" }
    val MOSHI_CODEGEN by lazy { "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}" }
    val MOSHI_CONVERTER by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}" }

    // Network logging
    val CHUCKER by lazy { "com.github.chuckerteam.chucker:library:${Versions.CHUCKER}" }
    val CHUCKER_NO_OP by lazy { "com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER}" }

    // Coroutines Flow
    val COROUTINES_CORE by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}" }
    val COROUTINES_ANDROID by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}" }
    val COROUTINES_ROOM by lazy { "androidx.room:room-ktx:${Versions.ROOM}" }

    // Service locator (DI)
    val KOIN_CORE by lazy { "io.insert-koin:koin-core:${Versions.KOIN}" }
    val KOIN_ANDROID by lazy { "io.insert-koin:koin-android:${Versions.KOIN}" }
    val KOIN_ANDROIDX_COMPOSE by lazy { "io.insert-koin:koin-androidx-compose:${Versions.KOIN}" }

    // Firebase
    val FIREBASE_BOM by lazy { "com.google.firebase:firebase-bom:${Versions.FIREBASE}" }
    val FIREBASE_ANALYTICS by lazy { "com.google.firebase:firebase-analytics-ktx" }
    val FIREBASE_CRASHLYTICS by lazy { "com.google.firebase:firebase-crashlytics-ktx" }

    // Memory leak detection
    val LEAKCANARY by lazy { "com.squareup.leakcanary:leakcanary-android:${Versions.LEAKCANARY}" }

    // Logger
    val TIMBER by lazy { "com.jakewharton.timber:timber:${Versions.TIMBER}" }

    // Balloon popup
    val BALLOON by lazy { "com.github.skydoves:balloon:${Versions.BALLOON}" }

    // Code scanner
    val CODE_SCANNER by lazy { "com.github.yuriy-budiyev:code-scanner:${Versions.CODE_SCANNER}" }

    // Curve bottom bar
    val CURVE_BOTTOM_BAR by lazy { "com.github.Akshay-Katariya:CurveBottomBar:${Versions.CURVE_BOTTOM_BAR}" }

    // Image downloading and caching
    val PICASSO by lazy { "com.squareup.picasso:picasso:${Versions.PICASSO}" }
}

object TestDependencies {
    val JUNIT by lazy { "junit:junit:${Versions.JUNIT}" }
    val JUNIT_EXT by lazy { "androidx.test.ext:junit:${Versions.JUNIT_EXT}" }
    val ESPRESSO by lazy { "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}" }
}