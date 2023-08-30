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

class FoodNutritionAdapter :
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
        foodNutrition?.let { holder.bind(it) }
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
        
        fun bind(foodNutrition: FoodNutrition) {
            binding.apply {
                imageView.load(foodNutrition.imageUrl)
                textView.text = foodNutrition.name
                
                root.setOnClickListener {
                    context.startActivity(
                        context.openFoodNutritionDetailsIntent(
                            foodId = foodNutrition.foodId,
                        )
                    )
                }
            }
        }
        
    }
    
}