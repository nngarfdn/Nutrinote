package com.sv.calorieintakeapps.feature_homepage.presentation.history

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sv.calorieintakeapps.databinding.ItemHistoryBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openReportDetailsIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openReportEditingIntent
import com.sv.calorieintakeapps.library_common.util.loadImage
import com.sv.calorieintakeapps.library_database.domain.model.Report
import java.util.*
import kotlin.collections.ArrayList

class AdapterHistory(
    private val activity: Activity,
    private val isCompleteReport: Boolean,
) :
    RecyclerView.Adapter<AdapterHistory.ViewHolder>(),
    Filterable {

    private val listItem = ArrayList<Report>()
    private val listItemFiltered = ArrayList<Report>()

    var data: List<Report>?
        get() = listItem
        @SuppressLint("NotifyDataSetChanged")
        set(listItem) {
            this.listItem.clear()
            this.listItem.addAll(listItem!!)
            listItemFiltered.clear()
            listItemFiltered.addAll(listItem)
            notifyDataSetChanged()
        }

    var countryFilterList = listOf<Report>()

    init {
        countryFilterList = data!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding, isCompleteReport)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countryFilterList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            activity.startActivity(
                holder.itemView.context.openReportDetailsIntent(
                    item.id,
                    item.foodId,
                    item.foodName
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
        private val isPendingReport: Boolean
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Report) {
            binding.apply {
                if(!item.preImage.isEmpty()){
                    imgItemRiwayat.loadImage(item.preImage)
                }
                txtTitleRiwayat.text = item.foodName
                txtLocationRiwayat.text = item.date
                if (isPendingReport) imgEditRiwayat.visibility = View.INVISIBLE
                else {
                    imgEditRiwayat.setOnClickListener {
                        it.context.startActivity(
                            itemView.context.openReportEditingIntent(
                                item.id,
                                item.foodName
                            )
                        )
                    }
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
                    val resultList = ArrayList<Report>()
                    for (row in data!!) {
                        if (row.postImage.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
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
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<Report>
                notifyDataSetChanged()
            }
        }
    }
}

interface HistoryAdapterListener {
    fun onEditClicked(item: Report)
}