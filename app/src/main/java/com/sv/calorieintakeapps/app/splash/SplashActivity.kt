package com.sv.calorieintakeapps.app.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.BuildConfig
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ActivitySplashBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAppVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(openHomepageIntent())
            finish()
        }, 2000)
    }
}