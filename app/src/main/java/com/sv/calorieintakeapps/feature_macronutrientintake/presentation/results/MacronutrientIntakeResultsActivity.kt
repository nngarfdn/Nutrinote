package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.results

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.ActivityMacronutrientIntakeResultsBinding
import com.sv.calorieintakeapps.feature_homepage.presentation.history.AdapterHistory
import com.sv.calorieintakeapps.feature_macronutrientintake.di.MacronurientIntakeModule
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.bottomsheet.FilterMakronutrienBottomSheet
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.compose.ItemMacronutrient
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model.listFilterMakrotrien
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.domain.model.ItemMakronutrien
import com.sv.calorieintakeapps.library_database.helper.onError
import com.sv.calorieintakeapps.library_database.helper.onLoading
import com.sv.calorieintakeapps.library_database.helper.onSuccess
import com.sv.calorieintakeapps.nutridesign.dialog.LoadingDialog
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
                val filterMakronutrienBottomSheet = FilterMakronutrienBottomSheet(
                    onFilterSelected = { selectedItems ->
                        viewModel.setSelectedMakronutrienName(selectedItems)
                    },
                    onClearClicked = {
                        viewModel.setSelectedMakronutrienName(listFilterMakrotrien)
                    },
                    selectedItems = viewModel.selectedMakronutrienName.value ?: listFilterMakrotrien
                )
                filterMakronutrienBottomSheet.show(
                    supportFragmentManager,
                    filterMakronutrienBottomSheet.tag
                )
            }

            viewModel.selectedMakronutrienName.observe(this@MacronutrientIntakeResultsActivity) { selectedItems ->
                val filteredList = viewModel.listMakronutrien.value?.filter { item ->
                    selectedItems?.any { filter -> filter.title == item.name } == true
                }
                viewModel.setFilteredMakronutrien(filteredList)
            }

            foodConsumedAdapter = AdapterHistory(this@MacronutrientIntakeResultsActivity, true)
            rvFoodConsumed.layoutManager = LinearLayoutManager(
                this@MacronutrientIntakeResultsActivity
            )
            rvFoodConsumed.adapter = foodConsumedAdapter

            observeMacronutrientLists()
        }

        viewModel.setInput(date = date)
    }

    private fun observeMacronutrientLists() {
        viewModel.filteredMakronutrien.observe(this@MacronutrientIntakeResultsActivity) { list ->
            displayMacronutrientList(list)
        }
        viewModel.listMakronutrien.observe(this@MacronutrientIntakeResultsActivity) { list ->
            if (viewModel.filteredMakronutrien.value.isNullOrEmpty()) {
                displayMacronutrientList(list)
            }
        }
    }

    private fun displayMacronutrientList(list: List<ItemMakronutrien>?) {
        list?.let { listItem ->
            binding.composeView.setContent {
                Column {
                    listItem.forEach {
                        ItemMacronutrient(
                            imageRes = it.image,
                            title = it.name,
                            percentage = "${it.percentage}%",
                            value = it.value
                        )
                    }
                }
            }
        }
    }

    private fun observeFoodConsumed() {
        viewModel.reports
            .onLoading {
                LoadingDialog.show(this@MacronutrientIntakeResultsActivity)
            }
            .onSuccess {
                foodConsumedAdapter.data = it
                LoadingDialog.dismiss()
            }
            .onError { message ->
                showToast(message)
                LoadingDialog.dismiss()
            }
    }

    private fun observeMacronutrientIntakePercentage() {
        viewModel.macronutrientIntakePercentage
            .onLoading {
                LoadingDialog.show(this@MacronutrientIntakeResultsActivity)
            }
            .onSuccess { macronutrientIntakePercentage ->
                viewModel.setListMakronutrien(macronutrientIntakePercentage.listMakronutrien)
                LoadingDialog.dismiss()
            }
            .onError { message ->
                LoadingDialog.dismiss()
                showToast(message)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        MacronurientIntakeModule.unload()
    }

    override fun onResume() {
        super.onResume()
        observeFoodConsumed()
        observeMacronutrientIntakePercentage()
    }

}