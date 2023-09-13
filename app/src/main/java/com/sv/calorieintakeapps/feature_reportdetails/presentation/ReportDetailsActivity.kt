package com.sv.calorieintakeapps.feature_reportdetails.presentation

import android.annotation.SuppressLint
import android.os.Bundle
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
        
        binding.txtName.text = foodName
        binding.btnBack.setOnClickListener { onBackPressed() }
        
        foodNutrientAdapter = FoodNutrientAdapter()
        
        observeReportById(reportId)
        observeNutrition(foodId)
    }
    
    @SuppressLint("SetTextI18n")
    private fun observeReportById(reportId: Int) {
        viewModel.getReportById(reportId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        binding.apply {
                            txtDateTime.text = "${result.data?.date}"
                            val percentage = result.data?.percentage ?: 0
                            foodNutrientAdapter.percentage = percentage
                            foodNutrientAdapter.totalPortion = result.data?.portionCount ?: 1f
                            txtSisaHidangan.text = result.data?.percentage.toString() + "%"
                            if (result.data?.preImage?.isEmpty() == false) {
                                imgSebelum.load(result.data.preImage)
                            } else {
                                imgSebelum.setImageResource(R.drawable.img_no_image_24)
                            }
                            if (result.data?.postImage?.isEmpty() == false) {
                                imgSesudah.load(result.data.postImage)
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