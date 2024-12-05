package com.sv.calorieintakeapps.feature_homepage.presentation.history

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.ItemHistoryBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openReportDetailsIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openReportEditingIntent
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import java.util.*

class AdapterHistory(
    private val activity: Activity,
    private val isCompletedReport: Boolean,
) :
    RecyclerView.Adapter<AdapterHistory.ViewHolder>(),
    Filterable {
    
    private val listItem = ArrayList<ReportDomainModel>()
    private val listItemFiltered = ArrayList<ReportDomainModel>()
    
    var data: List<ReportDomainModel>?
        get() = listItem
        @SuppressLint("NotifyDataSetChanged")
        set(listItem) {
            this.listItem.clear()
            this.listItem.addAll(listItem!!)
            listItemFiltered.clear()
            listItemFiltered.addAll(listItem)
            notifyDataSetChanged()
        }
    
    var countryFilterList = listOf<ReportDomainModel>()
    
    init {
        countryFilterList = data!!
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            binding.root,
            binding,
            isCompletedReport
        )
    }
    
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = countryFilterList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val isFromLocalDb = !isCompletedReport
            val reportId = if (isCompletedReport) item.id else item.roomId
            activity.startActivity(
                holder.itemView.context.openReportDetailsIntent(
                    reportId!!,
                    item.foodName,
                    isFromLocalDb
                )
            )
        }
    }
    
    override fun getItemCount(): Int {
        return countryFilterList.size
    }
    
    class ViewHolder(
        itemView: View,
        private val binding: ItemHistoryBinding,
        private val isCompletedReport: Boolean,
    ) : RecyclerView.ViewHolder(itemView) {
        
        fun bind(item: ReportDomainModel) {
            binding.apply {
                if (item.preImageFile != null) {
                    imgItemRiwayat.load(item.preImageFile)
                } else if (item.preImageUrl.isNotEmpty()) {
                    imgItemRiwayat.load(item.preImageUrl)
                } else {
                    imgItemRiwayat.setImageResource(R.drawable.ic_placeholder_24)
                }
                txtTitleRiwayat.text = item.foodName.ifBlank { "(Makanan lainnya)" }
                txtLocationRiwayat.text = item.date
                
                imgEditRiwayat.setOnClickListener {
                    val isFromLocalDb = !isCompletedReport
                    val reportId = if (isCompletedReport) item.id else item.roomId
                    it.context.startActivity(
                        itemView.context.openReportEditingIntent(
                            reportId!!,
                            item.foodName,
                            isFromLocalDb,
                            calories = item.calories.split(" ").first(),
                            protein = item.protein.split(" ").first(),
                            fat = item.fat.split(" ").first(),
                            carbs = item.carbs.split(" ").first(),
                        )
                    )
                }
            }
        }
    }
    
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryFilterList = if (charSearch.isEmpty()) {
                    data!!
                } else {
                    val resultList = ArrayList<ReportDomainModel>()
                    for (row in data!!) {
                        if (row.postImageUrl.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }
            
            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?,
            ) {
                countryFilterList = results?.values as ArrayList<ReportDomainModel>
                notifyDataSetChanged()
            }
        }
    }
    
}

interface HistoryAdapterListener {
    
    fun onEditClicked(item: ReportDomainModel)
}