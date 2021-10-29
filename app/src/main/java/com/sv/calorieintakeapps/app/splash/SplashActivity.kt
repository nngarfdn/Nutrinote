package com.sv.calorieintakeapps.app.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.library_common.action.Actions

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Actions.openHomepageIntent(this))
            finish()
        }, 2000)
    }
}