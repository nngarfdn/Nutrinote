plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        applicationId = Configs.APPLICATION_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = Configs.VERSION_CODE
        versionName = Configs.VERSION_NAME

        testInstrumentationRunner = Configs.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            addManifestPlaceholders(
                mapOf(
                    "isAnalyticsEnabled" to BuildTypeRelease.isAnalyticsEnabled,
                    "isCrashlyticsEnabled" to BuildTypeRelease.isCrashlyticsEnabled
                )
            )
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            addManifestPlaceholders(
                mapOf(
                    "isAnalyticsEnabled" to BuildTypeDebug.isAnalyticsEnabled,
                    "isCrashlyticsEnabled" to BuildTypeDebug.isCrashlyticsEnabled
                )
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    /* Common */
    implementation(Dependencies.CORE)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(TestDependencies.JUNIT_EXT)
    androidTestImplementation(TestDependencies.ESPRESSO)
    implementation(Dependencies.KOIN_CORE)
    implementation(Dependencies.KOIN_ANDROID)
    implementation(Dependencies.KOIN_ANDROIDX_COMPOSE)
    implementation(platform(Dependencies.FIREBASE_BOM))
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.FIREBASE_CRASHLYTICS)
    debugImplementation(Dependencies.LEAKCANARY)
    implementation(Dependencies.LOGGER)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.PICASSO)
    /* Database */
    implementation(Dependencies.ROOM_RUNTIME)
    kapt(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.SQLCHIPHER)
    implementation(Dependencies.SQLITE)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.MOSHI)
    kapt(Dependencies.MOSHI_CODEGEN)
    implementation(Dependencies.MOSHI_CONVERTER)
    debugImplementation(Dependencies.CHUCKER)
    releaseImplementation(Dependencies.CHUCKER_NO_OP)
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)
    implementation(Dependencies.COROUTINES_ROOM)
    implementation(Dependencies.LIVEDATA)
    /* Features */
    implementation(Dependencies.BALLOON)
    implementation(Dependencies.CODE_SCANNER)
    implementation(Dependencies.CURVE_BOTTOM_BAR)
}