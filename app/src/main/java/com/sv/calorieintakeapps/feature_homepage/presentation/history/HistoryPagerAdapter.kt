package com.sv.calorieintakeapps.feature_homepage.presentation.history

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sv.calorieintakeapps.R

class HistoryPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    
    @StringRes
    val titleList = intArrayOf(
        R.string.tab_proses,
        R.string.tab_selesai,
    )
    
    private val fragmentList = mutableListOf(
        PendingReportsFragment(),
        CompletedReportsFragment(),
    )
    
    override fun getItemCount(): Int {
        return fragmentList.size
    }
    
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    
}