package com.sv.calorieintakeapps.feature_reporting.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.sv.calorieintakeapps.databinding.ActivityUrtFoodSearchBinding
import com.sv.calorieintakeapps.feature_reporting.di.UrtFoodModule
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_FOOD_ID
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_FOOD_NAME
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_IS_CLICK_ENABLED
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_AIR
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_CALORIES
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_CARBS
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_FAT
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_FOOD_ID
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_PROTEIN
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_URT_LIST
import com.sv.calorieintakeapps.library_common.action.Actions.openAddNewFoodIntent
import com.sv.calorieintakeapps.library_common.util.hideKeyboard
import com.sv.calorieintakeapps.library_common.util.showKeyboard
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.Urt
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFood
import com.sv.calorieintakeapps.library_database.ui.adapter.FoodNutritionAdapter
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UrtFoodSearchActivity : AppCompatActivity() {
    
    private val viewModel: UrtFoodSearchViewModel by viewModel()
    
    private lateinit var binding: ActivityUrtFoodSearchBinding
    private lateinit var adapterUrt: UrtFoodAdapter
    private lateinit var moshiAdapter: JsonAdapter<List<Urt>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUrtFoodSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        UrtFoodModule.load()

        val isItemClickEnabled = intent.getBooleanExtra(EXTRA_IS_CLICK_ENABLED, false)

        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, Urt::class.java)
        moshiAdapter = moshi.adapter(listType)

        observerUrtFoodSearch()
        if (isItemClickEnabled) observerUrtFoodDetail()

        binding.apply {
            recyclerView.apply {
                adapterUrt = UrtFoodAdapter { foodId ->
                    if (isItemClickEnabled) viewModel.setFoodDetailId(foodId)
                }
                layoutManager = LinearLayoutManager(this@UrtFoodSearchActivity)
                adapter = adapterUrt
            }
            
            searchView.showKeyboard()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if ((query?.length ?: 0) > 1) {
                        lifecycleScope.launch {
                            viewModel.setFoodNameQuery(query!!)
                        }
                        searchView.hideKeyboard()
                    } else {
                        showToast("Masukkan minimal 2 karakter")
                    }
                    return true
                }
                
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

            btnAddNewFood.setOnClickListener {
                startActivity(openAddNewFoodIntent())
            }
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun observerUrtFoodSearch() {
        viewModel.searchResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    ApiResponse.Empty -> {
                        binding.btnAddNewFood.visibility = View.VISIBLE
                    }
                    is ApiResponse.Error -> showToast(result.errorMessage)
                    is ApiResponse.Success -> {
                        lifecycleScope.launch {
                            binding.btnAddNewFood.visibility = View.GONE
                            binding.recyclerView.apply {
                                adapterUrt.data = result.data
                                adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observerUrtFoodDetail() {
        viewModel.foodDetailResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    ApiResponse.Empty -> {}
                    is ApiResponse.Error -> showToast(result.errorMessage)
                    is ApiResponse.Success -> {
                        val returnIntent = Intent()
                        result.data.let { food ->
                            val urtAsString = moshiAdapter.toJson(food.urt)
                            returnIntent.putExtra(EXTRA_URT_LIST, urtAsString)
                            returnIntent.putExtra(EXTRA_FOOD_NAME, food.name)
                            returnIntent.putExtra(EXTRA_FOOD_ID, food.id)
                            returnIntent.putExtra(EXTRA_NILAIGIZI_COM_CALORIES, food.energi)
                            returnIntent.putExtra(EXTRA_NILAIGIZI_COM_PROTEIN, food.protein)
                            returnIntent.putExtra(EXTRA_NILAIGIZI_COM_FAT, food.lemak)
                            returnIntent.putExtra(EXTRA_NILAIGIZI_COM_CARBS, food.karbohidrat)
                            returnIntent.putExtra(EXTRA_NILAIGIZI_COM_AIR, food.air)
                        }
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    }
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        UrtFoodModule.unload()
    }
}