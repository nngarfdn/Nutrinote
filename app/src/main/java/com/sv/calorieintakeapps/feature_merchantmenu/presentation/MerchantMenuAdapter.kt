package com.sv.calorieintakeapps.feature_merchantmenu.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ItemFoodBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openFoodDetailsIntent
import com.sv.calorieintakeapps.library_common.util.loadImage
import com.sv.calorieintakeapps.library_database.domain.enum.FoodLabel
import com.sv.calorieintakeapps.library_database.domain.model.Food

@SuppressLint("NotifyDataSetChanged")
class MerchantMenuAdapter : RecyclerView.Adapter<MerchantMenuAdapter.ViewHolder>() {

    var merchantName: String? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var foods = listOf<Food>()
        set(values) {
            field = values
            notifyDataSetChanged()
        }

    fun submitList(foods: List<Food>?) {
        this.foods = foods ?: listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(food: Food) {
            binding.apply {
                imgItemRiwayat.loadImage(food.image)
                txtTitleRiwayat.text = food.name
                txtLocationRiwayat.text = merchantName
                txtPrice.text = "Rp ${food.price}"
                when (food.label) {
                    FoodLabel.BAD -> imgRating.setImageResource(R.drawable.img_bad)
                    FoodLabel.GOOD -> imgRating.setImageResource(R.drawable.img_ok)
                    FoodLabel.VERY_GOOD -> imgRating.setImageResource(R.drawable.img_good)
                }

                itemView.setOnClickListener {
                    it.context.startActivity(
                        it.context.openFoodDetailsIntent(
                            merchantName = merchantName.orEmpty(),
                            foodId = food.id,
                            foodName = food.name,
                            foodImage = food.image,
                            foodLabel = food.label.name
                        )
                    )
                }
            }
        }
    }
}