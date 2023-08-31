package com.sv.calorieintakeapps.feature_homepage.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sv.calorieintakeapps.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }
    
    private fun setupViewPager() {
        binding.apply {
            val pagerAdapter = HistoryPagerAdapter(requireActivity())
            vpReportHistory.adapter = pagerAdapter
            
            val tabLayout =
                TabLayoutMediator(tabReportHistory, vpReportHistory) { tab, position ->
                    tab.setText(pagerAdapter.titleList[position])
                }
            tabLayout.attach()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    
}