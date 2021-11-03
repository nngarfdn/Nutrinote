package com.sv.calorieintakeapps.feature_homepage.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.FragmentHomeBinding
import com.sv.calorieintakeapps.feature_homepage.di.HomepageModule
import com.sv.calorieintakeapps.library_common.action.Actions
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var balloon: Balloon? = null
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        HomepageModule.load()
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgProfile.setOnClickListener { showBalloon() }
            setName()
            cvMenuMakanan.setOnClickListener {
                startActivity(
                    Actions.openMerchantListIntent(
                        requireContext()
                    )
                )
            }
            cvBuatLaporan.setOnClickListener { cvMenuMakanan.performClick() }
        }
        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (!it) openLogin()
        }
    }

    private fun FragmentHomeBinding.setName() {
        viewModel.userName.observe(viewLifecycleOwner) {
            txtNameUser.text = "Halo, $it"
        }
    }

    override fun onResume() {
        super.onResume()
        binding.setName()
    }

    override fun onStart() {
        super.onStart()
        binding.setName()
    }

    private fun showBalloon() {
        balloon = createBalloon(requireContext()) {
            setArrowSize(10)
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(87)
            setArrowPosition(0.8f)
            setCornerRadius(4f)
            setAlpha(0.9f)
            setPaddingHorizontal(8)
            setPaddingVertical(4)
            setMarginHorizontal(24)
            setLayout(R.layout.layout_sky)
            setTextColorResource(R.color.white)
            setTextIsHtml(true)
            setIconDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_profile))
            setBackgroundColorResource(R.color.white)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)

            balloon?.showAlignBottom(binding.imgProfile)
            Handler(Looper.getMainLooper()).postDelayed({ balloon?.dismiss() }, 2000)
        }
        val profile: TextView = balloon?.getContentView()?.findViewById(R.id.profile)!!
        val logout: TextView = balloon?.getContentView()?.findViewById(R.id.logout)!!
        profile.setOnClickListener {
            startActivity(Actions.openProfileIntent(requireContext()))
        }
        logout.setOnClickListener {
            viewModel.logout()
            openLogin()
        }
    }

    private fun openLogin() {
        startActivity(
            Actions.openLoginIntent(requireContext())
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }



}