package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.input

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityMacronutrientIntakeInputBinding
import com.sv.calorieintakeapps.feature_macronutrientintake.di.MacronurientIntakeModule
import com.sv.calorieintakeapps.library_common.action.Actions.openMacronutrientIntakeResults
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.helper.toReadableDate
import com.sv.calorieintakeapps.library_database.vo.Resource
import com.sv.calorieintakeapps.nutridesign.dialog.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class MacronutrientIntakeInputActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMacronutrientIntakeInputBinding
    private val viewModel: MacronutrientIntakeInputViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMacronutrientIntakeInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        MacronurientIntakeModule.load()
        
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            
            btnShowData.setOnClickListener {
                val date = viewModel.date
                
                if (date != null) {
                    startActivity(
                        openMacronutrientIntakeResults(
                            date = date,
                        )
                    )
                }
            }
        }
        
        viewModel.inputDate.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        LoadingDialog.show(this@MacronutrientIntakeInputActivity)
                    }
                    
                    is Resource.Success -> {
                        val inputDateArray = result.data?.toTypedArray().orEmpty()
                        val spinnerDateAdapter = ArrayAdapter(
                            this@MacronutrientIntakeInputActivity,
                            android.R.layout.simple_spinner_dropdown_item,
                            inputDateArray.map { it.toReadableDate() },
                        )
                        binding.apply {
                            spinnerDate.adapter = spinnerDateAdapter
                            spinnerDate.onItemSelectedListener = object : OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long,
                                ) {
                                    if (position >= 0) {
                                        viewModel.date = inputDateArray[position]
                                    }
                                }
                                
                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                            if (spinnerDate.adapter.count > 0) {
                                spinnerDate.setSelection(0)
                            }
                            
                            if (inputDateArray.isEmpty()) {
                                btnShowData.isEnabled = false
                                spinnerDate.isEnabled = false
                                lytDate.isEnabled = false
                                showToast("Belum ada riwayat makanan yang dikonsumsi")
                            }
                        }
                        LoadingDialog.dismiss()
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message)
                        LoadingDialog.dismiss()
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