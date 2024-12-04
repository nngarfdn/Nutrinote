package com.sv.calorieintakeapps.app.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ActivityTutorialBinding
import com.sv.calorieintakeapps.feature_homepage.presentation.HomepageActivity
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent

class TutorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            lifecycle.addObserver(youtubePlayerView)
            btnHomePage.setOnClickListener {
                startActivity(
                    openHomepageIntent().setFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                )
            }
        }
    }
}