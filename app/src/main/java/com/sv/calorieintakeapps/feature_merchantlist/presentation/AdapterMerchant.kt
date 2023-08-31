package com.sv.calorieintakeapps.feature_merchantlist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ItemMerchantBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openMerchantMenuIntent
import com.sv.calorieintakeapps.library_database.domain.model.Merchant

class AdapterMerchant :
    RecyclerView.Adapter<AdapterMerchant.ViewHolder>() {
    private val listItem = ArrayList<Merchant>()
    private val listItemFiltered = ArrayList<Merchant>()
    var data: List<Merchant>?
        get() = listItem
        @SuppressLint("NotifyDataSetChanged")
        set(listItem) {
            this.listItem.clear()
            this.listItem.addAll(listItem!!)
            listItemFiltered.clear()
            listItemFiltered.addAll(listItem)
            notifyDataSetChanged()
        }

    private var countryFilterList = listOf<Merchant>()

    init {
        countryFilterList = data!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMerchantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countryFilterList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            it.context.startActivity(it.context.openMerchantMenuIntent(item.id))
        }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    class ViewHolder(
        itemView: View, private val binding: ItemMerchantBinding
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Merchant) {

            binding.apply {
                txtName.text = item.name
                imgBanner.setImageResource(R.drawable.img_merchant_banner_512_325)
            }
        }
    }
}