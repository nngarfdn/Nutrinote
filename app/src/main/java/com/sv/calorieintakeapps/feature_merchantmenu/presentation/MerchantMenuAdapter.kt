package com.sv.calorieintakeapps.feature_merchantmenu.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ItemFoodBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openFoodDetailsIntent
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_database.domain.enum.FoodLabel
import com.sv.calorieintakeapps.library_database.domain.model.Food
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class MerchantMenuAdapter : RecyclerView.Adapter<MerchantMenuAdapter.ViewHolder>(), Filterable {
    
    var foodFilterList = ArrayList<Food>()
    
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
        this.foodFilterList = (foods ?: listOf()) as ArrayList<Food>
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodFilterList[position]
        holder.bind(food)
    }
    
    override fun getItemCount(): Int {
        return foodFilterList.size
    }
    
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    foodFilterList = foods as ArrayList<Food>
                } else {
                    val resultList = ArrayList<Food>()
                    for (row in foods) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    foodFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = foodFilterList
                return filterResults
            }
            
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                foodFilterList = results?.values as ArrayList<Food>
                notifyDataSetChanged()
            }
            
        }
    }
    
    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        @SuppressLint("SetTextI18n")
        fun bind(food: Food) {
            binding.apply {
                imgItemRiwayat.load(food.image)
                txtTitleRiwayat.text = food.name
                txtLocationRiwayat.text = merchantName
                txtPrice.text = "Rp ${food.price}"
                when (food.label) {
                    FoodLabel.BAD -> imgRating.setImageResource(R.drawable.ic_food_label_bad)
                    FoodLabel.GOOD -> imgRating.setImageResource(R.drawable.ic_food_label_good)
                    FoodLabel.VERY_GOOD -> imgRating.setImageResource(R.drawable.ic_food_label_very_good)
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