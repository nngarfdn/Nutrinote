//package com.sv.calorieintakeapps.feature_home.presentation.history
//
//import android.content.Context
//import androidx.fragment.app.FragmentPagerAdapter
//import androidx.annotation.StringRes
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import com.sv.calorieintakeapps.R
//
//class HistoryPagerAdapter(private val context: Context, fm: FragmentManager) :
//    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    override fun getItem(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = PendingReportsFragment()
//            1 -> fragment = CompletedReportsFragment()
//        }
//        return fragment!!
//    }
//
//    override fun getCount(): Int {
//        return 2
//    }
//
//    @StringRes
//    private val TAB_TITLES = intArrayOf(
//        R.string.tab_proses,
//        R.string.tab_selesai
//    )
//
//    override fun getPageTitle(position: Int): CharSequence {
//        return context.resources.getString(TAB_TITLES[position])
//    }
//}