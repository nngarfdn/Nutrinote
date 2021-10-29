package com.sv.calorieintakeapps.feature.reportdetails.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.core.common.util.loadImage
import com.sv.calorieintakeapps.core.common.util.showToast
import com.sv.calorieintakeapps.databinding.ActivityReportDetailsBinding
import com.sv.calorieintakeapps.feature_reportdetails.di.ReportDetailsModule
import com.sv.calorieintakeapps.feature_reportdetails.presentation.ReportDetailViewModel
import com.sv.calorieintakeapps.library_common.action.Actions
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

        foodNutrientAdapter = FoodNutrientAdapter()

        observeReportById(reportId)
        observeNutrition(foodId)
    }

    @SuppressLint("SetTextI18n")
    private fun observeReportById(reportId: Int) {
        viewModel.getReportById(reportId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
//                        Timber.tag(TAG).d("onViewCreated: ${result.data}")
                        binding.apply {
                            txtDateTime.text = "${result.data?.date}"
                            val percentage = result.data?.percentage ?: 0
                            foodNutrientAdapter.percentage = percentage
                            txtSisaHidangan.text = result.data?.percentage.toString() + "%"
                            imgSebelum.loadImage(result.data?.preImage)
                            imgSesudah.loadImage(result.data?.postImage)
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
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
//                        Timber.tag(TAG).d("onViewCreated: ${result.data}")
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