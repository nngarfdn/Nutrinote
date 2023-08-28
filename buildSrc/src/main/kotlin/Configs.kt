object Configs {
    const val APPLICATION_ID = "com.sv.calorieintakeapps"

    const val COMPILE_SDK = 33
    const val MIN_SDK = 21
    const val TARGET_SDK = 33

    const val VERSION_CODE = 6
    const val VERSION_NAME = "0.1.6"

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isAnalyticsEnabled: Boolean
    val isCrashlyticsEnabled: Boolean
    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isAnalyticsEnabled = false
    override val isCrashlyticsEnabled = true
    override val isMinifyEnabled = true
}

object BuildTypeRelease : BuildType {
    override val isAnalyticsEnabled = true
    override val isCrashlyticsEnabled = true
    override val isMinifyEnabled = true
}