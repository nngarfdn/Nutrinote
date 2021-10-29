//package com.sv.calorieintakeapps.feature.home.presentation.home
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.skydoves.balloon.*
//import com.sv.calorieintakeapps.core.common.action.Actions
//import com.sv.calorieintakeapps.R as AppR
//import com.sv.calorieintakeapps.feature.home.R as HomepageR
//import com.sv.calorieintakeapps.feature.home.databinding.FragmentHomeBinding
//import com.sv.calorieintakeapps.feature.home.di.HomepageModule
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//class HomeFragment : Fragment() {
//
//    private lateinit var binding: FragmentHomeBinding
//    private var balloon: Balloon? = null
//    private val viewModel: HomeViewModel by viewModel()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        HomepageModule.load()
//        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.apply {
//            imgProfile.setOnClickListener { showBalloon() }
//            setName()
//            cvMenuMakanan.setOnClickListener { startActivity(Actions.openMerchantListIntent()) }
//            cvBuatLaporan.setOnClickListener { cvMenuMakanan.performClick() }
//        }
//        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
//            if (!it) openLogin()
//        }
//    }
//
//    private fun FragmentHomeBinding.setName() {
//        viewModel.userName.observe(viewLifecycleOwner) {
//            txtNameUser.text = "Halo, $it"
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        binding.setName()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        binding.setName()
//    }
//
//    private fun showBalloon() {
//        balloon = createBalloon(requireContext()) {
//            setArrowSize(10)
//            setWidth(BalloonSizeSpec.WRAP)
//            setHeight(75)
//            setArrowPosition(0.8f)
//            setCornerRadius(4f)
//            setAlpha(0.9f)
//            setPaddingHorizontal(8)
//            setPaddingVertical(4)
//            setMarginHorizontal(24)
//            setLayout(HomepageR.layout.layout_sky)
//            setTextColorResource(AppR.color.white)
//            setTextIsHtml(true)
//            setIconDrawable(ContextCompat.getDrawable(requireContext(), AppR.drawable.ic_profile))
//            setBackgroundColorResource(AppR.color.white)
//            setBalloonAnimation(BalloonAnimation.FADE)
//            setLifecycleOwner(lifecycleOwner)
//
//            balloon?.showAlignBottom(binding.imgProfile)
//            Handler(Looper.getMainLooper()).postDelayed({ balloon?.dismiss() }, 2000)
//        }
//        val profile: TextView = balloon?.getContentView()?.findViewById(HomepageR.id.profile)!!
//        val logout: TextView = balloon?.getContentView()?.findViewById(HomepageR.id.logout)!!
//        profile.setOnClickListener {
//            startActivity(Actions.openProfileIntent())
//        }
//        logout.setOnClickListener {
//            viewModel.logout()
//            openLogin()
//        }
//    }
//
//    private fun openLogin() {
//        startActivity(
//            Actions.openLoginIntent()
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//        )
//    }
//}