package com.sv.calorieintakeapps.feature_reporting.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.databinding.ItemUrtFoodBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openMerchantMenuIntent
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFood

class UrtFoodAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<UrtFoodAdapter.ViewHolder>() {

    private val listItem = ArrayList<UrtFood>()
    private val listItemFiltered = ArrayList<UrtFood>()
    var data: List<UrtFood>?
        get() = listItem
        @SuppressLint("NotifyDataSetChanged")
        set(listItem) {
            this.listItem.clear()
            this.listItem.addAll(listItem!!)
            listItemFiltered.clear()
            listItemFiltered.addAll(listItem)
            notifyDataSetChanged()
        }

    private var countryFilterList = listOf<UrtFood>()

    init {
        countryFilterList = data!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUrtFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countryFilterList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    class ViewHolder(
        itemView: View, private val binding: ItemUrtFoodBinding
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: UrtFood) {

            binding.apply {
                tvUrtFoodName.text = item.name
            }
        }
    }
}