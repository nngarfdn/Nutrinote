package com.sv.calorieintakeapps.feature_homepage.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ActivityHomepageBinding
import com.sv.calorieintakeapps.feature_homepage.presentation.history.HistoryFragment
import com.sv.calorieintakeapps.feature_homepage.presentation.home.HomeFragment
import com.sv.calorieintakeapps.library_common.action.Actions.openScannerIntent

class HomepageActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    View.OnClickListener {

    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomBar.inflateMenu(R.menu.menu_homepage)
        binding.bottomBar.setCurveRadius(60)
        loadFragment(HomeFragment())
        binding.bottomBar.setOnItemSelectedListener(this)

        binding.scan.setOnClickListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_riwayat -> loadFragment(HistoryFragment())
            R.id.action_beranda -> loadFragment(HomeFragment())
        }
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flayout, fragment)
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        loadFragment(HomeFragment())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.scan -> startActivity(openScannerIntent())
        }
    }
}