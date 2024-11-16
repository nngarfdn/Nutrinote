package com.sv.calorieintakeapps.feature_reportdetails.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ActivityReportDetailsBinding
import com.sv.calorieintakeapps.feature_reportdetails.di.ReportDetailsModule
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.ui.adapter.FoodNutrientAdapter
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportDetailsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityReportDetailsBinding
    private val viewModel: ReportDetailViewModel by viewModel()
    private lateinit var foodNutrientAdapter: FoodNutrientAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ReportDetailsModule.load()
        
        val reportId = intent.getIntExtra(Actions.EXTRA_REPORT_ID, -1)
        val foodId = intent.getIntExtra(Actions.EXTRA_FOOD_ID, -1)
        val foodName = intent.getStringExtra(Actions.EXTRA_FOOD_NAME).orEmpty()
        val isFromLocalDb = intent.getBooleanExtra(Actions.EXTRA_IS_FROM_LOCAL_DB, false)

        binding.txtName.text = foodName
        binding.btnBack.setOnClickListener { onBackPressed() }
        
        foodNutrientAdapter = FoodNutrientAdapter()
        
        observeReportById(reportId, isFromLocalDb)
//        observeNutrition(foodId)
    }
    
    @SuppressLint("SetTextI18n")
    private fun observeReportById(reportId: Int, isFromLocalDb: Boolean) {
        viewModel.getReportById(reportId, isFromLocalDb).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        binding.apply {
                            txtDateTime.text = "${result.data?.date}"
                            val percentage = result.data?.percentage ?: 0
                            foodNutrientAdapter.percentage = percentage
                            
                            val gramTotal = result.data?.gramTotalDikonsumsi
                            val porsiDimakan = result.data?.porsiUrt?.toFloat() ?: 0f
                            val gramPerUrt = result.data?.gramPerUrt
                            foodNutrientAdapter.totalPortion = porsiDimakan
//                            if (result.data?.isUsingUrt == true) {
//                                tvConsumedNutrition.visibility = View.GONE
//                            } else {
//                                tvConsumedNutrition.visibility = View.VISIBLE
//                            }
                            tvGramTotal.text = "Gram total dikonsumsi: $gramTotal g"
                            tvConsumedNutrition.text = "Porsi dimakan: $porsiDimakan"
                            tvConsumedNutrition.text = "Gram tiap URT: $gramPerUrt"
                            val formattedKarbo = String.format("%.1f", result.data?.carbs?.toFloat())
                            tvKarbo.text = "Karbohidrat: $formattedKarbo g"
                            val formattedFat = String.format("%.1f", result.data?.fat?.toFloat())
                            tvFat.text = "Lemak: $formattedFat g"
                            val formattedProtein = String.format("%.1f", result.data?.protein?.toFloat())
                            tvProtein.text = "Protein: $formattedProtein g"
                            val formattedCal = String.format("%.1f", result.data?.calories?.toFloat())
                            tvCalories.text = "Kalori: $formattedCal g"
                            val formattedAir = String.format("%.1f", result.data?.air?.toFloat())
                            tvAir.text = "Air: $formattedAir ml"

                            txtSisaHidangan.text = result.data?.percentage.toString() + "%"
                            
                            if (result.data?.preImageFile != null) {
                                imgSebelum.load(result.data.preImageFile)
                            } else if (result.data?.preImageUrl?.isNotEmpty() == true) {
                                imgSebelum.load(result.data.preImageUrl)
                            } else {
                                imgSebelum.setImageResource(R.drawable.img_no_image_24)
                            }

                            if (result.data?.postImageFile != null) {
                                imgSesudah.load(result.data.postImageFile)
                            } else if (result.data?.preImageUrl?.isNotEmpty() == true) {
                                imgSesudah.load(result.data.postImageUrl)
                            } else {
                                imgSesudah.setImageResource(R.drawable.img_no_image_24)
                            }
                        }
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    @SuppressLint("NotifyDataSetChanged")
    private fun observeNutrition(foodId: Int) {
        viewModel.getFoodNutrientsById(foodId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        binding.apply {
                            binding.apply {
                                rvFoodNutrition.apply {
                                    val lm = LinearLayoutManager(context)
                                    layoutManager = lm
                                    setHasFixedSize(true)
                                    foodNutrientAdapter.submitList(result.data)
                                    adapter = foodNutrientAdapter
                                }
                            }
                        }
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        ReportDetailsModule.unload()
    }
    
}