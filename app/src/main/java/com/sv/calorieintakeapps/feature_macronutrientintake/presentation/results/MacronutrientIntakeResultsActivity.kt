package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.results

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.ActivityMacronutrientIntakeResultsBinding
import com.sv.calorieintakeapps.feature_homepage.presentation.history.AdapterHistory
import com.sv.calorieintakeapps.feature_macronutrientintake.di.MacronurientIntakeModule
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.bottomsheet.FilterMakronutrienBottomSheet
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MacronutrientIntakeResultsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMacronutrientIntakeResultsBinding
    private val viewModel: MacronutrientIntakeResultViewModel by viewModel()
    
    private lateinit var foodConsumedAdapter: AdapterHistory
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMacronutrientIntakeResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        MacronurientIntakeModule.load()
        
        val date = intent.getStringExtra(Actions.EXTRA_DATE).orEmpty()
        
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            btnFilter.setOnClickListener {
                val filterMakronutrienBottomSheet = FilterMakronutrienBottomSheet()
                filterMakronutrienBottomSheet.show(supportFragmentManager, filterMakronutrienBottomSheet.tag)
            }
            
            foodConsumedAdapter = AdapterHistory(this@MacronutrientIntakeResultsActivity, true)
            rvFoodConsumed.layoutManager = LinearLayoutManager(
                this@MacronutrientIntakeResultsActivity
            )
            rvFoodConsumed.adapter = foodConsumedAdapter
        }
        
        
        viewModel.setInput(
            date = date,
        )
        
        observeFoodConsumed()
        observeMacronutrientIntakePercentage()
    }
    
    private fun observeFoodConsumed() {
        viewModel.reports.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        val foodConsumed = result.data.orEmpty()
                        foodConsumedAdapter.data = foodConsumed
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message.orEmpty())
                    }
                }
            }
        }
    }
    
    private fun observeMacronutrientIntakePercentage() {
        viewModel.macronutrientIntakePercentage.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        result.data?.let { macronutrientIntakePercentage ->
                            binding.apply {
                                tvCaloriesPercentage.text =
                                    "${macronutrientIntakePercentage.caloriesPercentage}%"
                                tvCaloriesValue.text =
                                    "${macronutrientIntakePercentage.caloriesIntake} / ${macronutrientIntakePercentage.caloriesNeeds} kkal"
                                tvProteinPercentage.text =
                                    "${macronutrientIntakePercentage.proteinPercentage}%"
                                tvProteinValue.text =
                                    "${macronutrientIntakePercentage.proteinIntake} / ${macronutrientIntakePercentage.proteinNeeds} g"
                                tvFatPercentage.text =
                                    "${macronutrientIntakePercentage.fatPercentage}%"
                                tvFatValue.text =
                                    "${macronutrientIntakePercentage.fatIntake} / ${macronutrientIntakePercentage.fatNeeds} g"
                                tvCarbsPercentage.text =
                                    "${macronutrientIntakePercentage.carbsPercentage}%"
                                tvCarbsValue.text =
                                    "${macronutrientIntakePercentage.carbsIntake} / ${macronutrientIntakePercentage.carbsNeeds} g"
                                tvAirPercentage.text =
                                    "${macronutrientIntakePercentage.waterPercentage}%"
                                tvAirValue.text =
                                    "${macronutrientIntakePercentage.waterIntake} / ${macronutrientIntakePercentage.waterNeeds} ml"
                            }
                        }
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message.orEmpty())
                    }
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        MacronurientIntakeModule.unload()
    }
    
}