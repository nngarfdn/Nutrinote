package com.sv.calorieintakeapps.library_database.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.databinding.ItemFoodNutritionBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openFoodNutritionDetailsIntent
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition

class FoodNutritionAdapter(private val merchantId: Int, private val onClick: (Int, Int?) -> Unit) :
    PagingDataAdapter<FoodNutrition, FoodNutritionAdapter.FoodNutritionViewHolder>(
        FoodNutritionComparator
    ) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodNutritionViewHolder {
        val binding = ItemFoodNutritionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodNutritionViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: FoodNutritionViewHolder, position: Int) {
        val foodNutrition = getItem(position)
        foodNutrition?.let { holder.bind(it, onClick) }
    }
    
    object FoodNutritionComparator : DiffUtil.ItemCallback<FoodNutrition>() {
        
        override fun areItemsTheSame(oldItem: FoodNutrition, newItem: FoodNutrition): Boolean {
            return oldItem.foodId == newItem.foodId
        }
        
        override fun areContentsTheSame(oldItem: FoodNutrition, newItem: FoodNutrition): Boolean {
            return oldItem == newItem
        }
        
    }
    
    inner class FoodNutritionViewHolder(
        private val binding: ItemFoodNutritionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val context = binding.root.context
        
        fun bind(foodNutrition: FoodNutrition, onClick: (Int, Int?) -> Unit) {
            binding.apply {
                imageView.load(foodNutrition.imageUrl)
                textView.text = foodNutrition.name
                
                root.setOnClickListener {
                    val foodId = foodNutrition.foodId
                    val merchantId = if (merchantId < 0) null else merchantId
                    onClick.invoke(foodId, merchantId)
                }
            }
        }
        
    }
    
}