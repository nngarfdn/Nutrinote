package com.sv.calorieintakeapps.app.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sv.calorieintakeapps.BuildConfig
import com.sv.calorieintakeapps.library_database.di.dataSourceModule
import com.sv.calorieintakeapps.library_database.di.databaseModule
import com.sv.calorieintakeapps.library_database.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
class BaseApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        lockScreenToPortrait()
        initKoin()
        initTimber()
    }
    
    private fun lockScreenToPortrait() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            
            override fun onActivityStarted(p0: Activity) {
            }
            
            override fun onActivityResumed(p0: Activity) {
            }
            
            override fun onActivityPaused(p0: Activity) {
            }
            
            override fun onActivityStopped(p0: Activity) {
            }
            
            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }
            
            override fun onActivityDestroyed(p0: Activity) {
            }
        })
    }
    
    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    databaseModule,
                    dataSourceModule,
                    networkModule,
                )
            )
        }
    }
    
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            Timber.plant(ReleaseTree())
        }
    }
    
    private class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.ERROR || priority == Log.WARN) {
                if (t == null) {
                    FirebaseCrashlytics.getInstance().log(message)
                } else {
                    FirebaseCrashlytics.getInstance().recordException(t)
                }
            }
        }
    }
    
}