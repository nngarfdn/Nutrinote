import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.KSP)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
}

val keysPropertiesFile = file("keys.properties")
val keysProperties = Properties()
if (keysPropertiesFile.canRead()) {
    keysProperties.load(FileInputStream(keysPropertiesFile))
}
fun getKeyProperty(key: String): String {
    return "\"${System.getenv(key) ?: keysProperties[key]}\""
}

android {
    namespace = Configs.APPLICATION_ID
    compileSdk = Configs.COMPILE_SDK
    
    defaultConfig {
        applicationId = Configs.APPLICATION_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = Configs.VERSION_CODE
        versionName = Configs.VERSION_NAME
        
        testInstrumentationRunner = Configs.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
        
        buildConfigField(
            "String",
            "DATABASE_PASSPHRASE",
            getKeyProperty("DATABASE_PASSPHRASE")
        )
        
        buildConfigField(
            "String",
            "GIZI_BASE_URL",
            getKeyProperty("GIZI_BASE_URL")
        )
        buildConfigField(
            "String",
            "GIZI_PUBLIC_KEY_1",
            getKeyProperty("GIZI_PUBLIC_KEY_1")
        )
        buildConfigField(
            "String",
            "GIZI_PUBLIC_KEY_2",
            getKeyProperty("GIZI_PUBLIC_KEY_2")
        )
        buildConfigField(
            "String",
            "GIZI_PUBLIC_KEY_3",
            getKeyProperty("GIZI_PUBLIC_KEY_3")
        )
        buildConfigField(
            "String",
            "GIZI_SECRET_KEY",
            getKeyProperty("GIZI_SECRET_KEY")
        )
        
        buildConfigField(
            "String",
            "NILAIGIZI_COM_BASE_URL",
            getKeyProperty("NILAIGIZI_COM_BASE_URL")
        )
        buildConfigField(
            "String",
            "NILAIGIZI_COM_PUBLIC_KEY_1",
            getKeyProperty("NILAIGIZI_COM_PUBLIC_KEY_1")
        )
        buildConfigField(
            "String",
            "NILAIGIZI_COM_PUBLIC_KEY_2",
            getKeyProperty("NILAIGIZI_COM_PUBLIC_KEY_2")
        )
        buildConfigField(
            "String",
            "NILAIGIZI_COM_PUBLIC_KEY_3",
            getKeyProperty("NILAIGIZI_COM_PUBLIC_KEY_3")
        )
        
        buildConfigField(
            "String",
            "NILAIGIZI_COM_EMAIL",
            getKeyProperty("NILAIGIZI_COM_EMAIL")
        )
        buildConfigField(
            "String",
            "NILAIGIZI_COM_PASSWORD",
            getKeyProperty("NILAIGIZI_COM_PASSWORD")
        )
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            addManifestPlaceholders(
                mapOf(
                    "isAnalyticsEnabled" to BuildTypeDebug.isAnalyticsEnabled,
                    "isCrashlyticsEnabled" to BuildTypeDebug.isCrashlyticsEnabled
                )
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /* Common */
    implementation(Dependencies.CORE)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.SECURITY_CRYPTO)
    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(TestDependencies.JUNIT_EXT)
    androidTestImplementation(TestDependencies.ESPRESSO)
    implementation(Dependencies.KOIN_CORE)
    implementation(Dependencies.KOIN_ANDROID)
    implementation(Dependencies.KOIN_ANDROIDX_COMPOSE)
    implementation(platform(Dependencies.FIREBASE_BOM))
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.FIREBASE_CRASHLYTICS)
    //debugImplementation(Dependencies.LEAKCANARY)
    implementation(Dependencies.PAGING)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.COIL)
    implementation(Dependencies.FLEXBOX)
    /* Database */
    implementation(Dependencies.ROOM_RUNTIME)
    ksp(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.SQLCHIPHER)
    implementation(Dependencies.SQLITE)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.MOSHI)
    ksp(Dependencies.MOSHI_CODEGEN)
    implementation(Dependencies.MOSHI_CONVERTER)
    debugImplementation(Dependencies.CHUCKER)
    releaseImplementation(Dependencies.CHUCKER_NO_OP)
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)
    implementation(Dependencies.COROUTINES_ROOM)
    implementation(Dependencies.LIFECYCLE_LIVEDATA)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    /* Features */
    implementation(Dependencies.BALLOON)
    implementation(Dependencies.CODE_SCANNER)
    implementation(Dependencies.CURVE_BOTTOM_BAR)
    implementation(Dependencies.YOUTUBE_PLAYER)
}