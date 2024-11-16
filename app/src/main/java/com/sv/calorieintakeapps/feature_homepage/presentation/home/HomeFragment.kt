package com.sv.calorieintakeapps.feature_homepage.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.FragmentHomeBinding
import com.sv.calorieintakeapps.feature_homepage.di.HomepageModule
import com.sv.calorieintakeapps.library_common.action.Actions.openFoodNutritionSearchIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openLoginIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openMacronutrientIntakeInput
import com.sv.calorieintakeapps.library_common.action.Actions.openMerchantListIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openProfileIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openReportingIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openUrtFoodSearchIntent
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnClickListener {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: HomeViewModel by viewModel()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        HomepageModule.load()
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.apply {
            imgProfile.setOnClickListener(this@HomeFragment)
            cvMacronutrientIntake.setOnClickListener(this@HomeFragment)
            cvFoodNutrition.setOnClickListener(this@HomeFragment)
        }
        
        viewModel.userName.observe(viewLifecycleOwner) {
            binding.tvUserName.text = "Halo, $it"
        }
        
        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (!it) openLogin()
        }
    }
    
    override fun onClick(view: View) {
        val balloonProfile = createBalloonProfile()
        when (view.id) {
            binding.imgProfile.id -> {
                balloonProfile.showAlignBottom(binding.imgProfile)
            }
            
            binding.cvMacronutrientIntake.id -> {
                startActivity(requireContext().openMacronutrientIntakeInput())
            }
            
            binding.cvFoodNutrition.id -> {
                startActivity(requireContext().openUrtFoodSearchIntent(isItemClickEnabled = false))
            }
        }
    }
    
    private fun createBalloonProfile(): Balloon {
        val balloon = createBalloon(requireContext()) {
            setArrowSize(10)
            setWidth(190)
            setHeight(110)
            setArrowPosition(0.8f)
            setCornerRadius(16f)
            setAlpha(0.9f)
            setPaddingHorizontal(8)
            setPaddingVertical(4)
            setMarginHorizontal(24)
            setLayout(R.layout.layout_profile_menu)
            setTextColorResource(R.color.white)
            setTextIsHtml(true)
            setIconDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_profile_action_36
                )
            )
            setBackgroundColorResource(R.color.white)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }
        
        val view = balloon.getContentView()
        view.apply {
            val tvProfile: TextView = findViewById(R.id.tv_profile)
            tvProfile.setOnClickListener {
                startActivity(requireContext().openProfileIntent())
                balloon.dismiss()
            }
            
            val tvLogout: TextView = findViewById(R.id.tv_logout)
            tvLogout.setOnClickListener {
                viewModel.logout()
                openLogin()
            }
        }
        
        return balloon
    }
    
    private fun openLogin() {
        startActivity(
            requireContext().openLoginIntent()
                .setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )
        )
    }
    
    override fun onDestroy() {
        super.onDestroy()
        HomepageModule.unload()
        _binding = null
    }
    
}