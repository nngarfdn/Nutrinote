package com.sv.calorieintakeapps.library_database.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ItemFoodNutrientBinding
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient

@SuppressLint("NotifyDataSetChanged")
class FoodNutrientAdapter : RecyclerView.Adapter<FoodNutrientAdapter.ViewHolder>() {

    var percentage = 100
        set(values) {
            field = values
            notifyDataSetChanged()
        }

    private var foodNutrients = listOf<FoodNutrient>()
        set(values) {
            field = values
            notifyDataSetChanged()
        }

    fun submitList(foodNutrients: List<FoodNutrient>?) {
        this.foodNutrients = foodNutrients ?: listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFoodNutrientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodNutrient = foodNutrients[position]
        holder.bind(foodNutrient)
    }

    override fun getItemCount(): Int {
        return foodNutrients.size
    }

    inner class ViewHolder(private val binding: ItemFoodNutrientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodNutrient: FoodNutrient) {
            binding.apply {
                txtNutritionName.text = foodNutrient.nutrientName
                txtPercentageValue.text = foodNutrient.akgDay + if(!foodNutrient.akgDay.equals("-")) " %" else ""
                txtNutritionValue.text = itemView.context.getString(
                    R.string.nutrient_value,
                    foodNutrient.value * percentage.toDouble() / 100,
                    foodNutrient.nutrientUnit
                )
            }
        }
    }
}