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
import com.sv.calorieintakeapps.library_common.action.Actions.openReportingIntent

class HomepageActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    View.OnClickListener {
    
    private lateinit var binding: ActivityHomepageBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.apply {
            navbarHomepage.setOnItemSelectedListener(this@HomepageActivity)
            navbarHomepage.selectedItemId = R.id.menu_home
            
            btnReport.setOnClickListener(this@HomepageActivity)
        }
    }
    
    override fun onClick(view: View) {
        when (view.id) {
            binding.btnReport.id -> startActivity(openReportingIntent(
                expectSearch = true,
                writeFoodName = false
            ))
        }
    }
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> loadFragment(HomeFragment())
            R.id.menu_report_history -> loadFragment(HistoryFragment())
        }
        return true
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flayout, fragment)
            .commit()
    }
    
}