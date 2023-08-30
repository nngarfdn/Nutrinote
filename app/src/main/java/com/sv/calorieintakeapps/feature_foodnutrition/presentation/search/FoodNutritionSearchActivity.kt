package com.sv.calorieintakeapps.feature_foodnutrition.presentation.search

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.ActivityFoodNutritionSearchBinding
import com.sv.calorieintakeapps.feature_foodnutrition.di.FoodNutritionModule
import com.sv.calorieintakeapps.library_common.util.hideKeyboard
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.ui.adapter.FoodNutritionAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodNutritionSearchActivity : AppCompatActivity() {
    
    private val viewModel: FoodNutritionSearchViewModel by viewModel()
    
    private lateinit var binding: ActivityFoodNutritionSearchBinding
    private lateinit var foodNutritionAdapter: FoodNutritionAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodNutritionSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        FoodNutritionModule.load()
        
        binding.apply {
            foodNutritionAdapter = FoodNutritionAdapter().apply {
                addLoadStateListener { loadState ->
                    if (loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemCount < 1) {
                        showToast("Data tidak ditemukan, coba gunakan kata kunci lain")
                    } else if (loadState.refresh is LoadState.Error) {
                        showToast("Terjadi kesalahan, coba lagi nanti")
                    }
                }
            }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@FoodNutritionSearchActivity)
                adapter = foodNutritionAdapter
            }
            
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if ((query?.length ?: 0) > 2) {
                        lifecycleScope.launch {
                            viewModel.search(query!!).collectLatest { pagingData ->
                                foodNutritionAdapter.submitData(pagingData)
                            }
                        }
                        searchView.hideKeyboard()
                    }
                    return true
                }
                
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        FoodNutritionModule.unload()
    }
    
}