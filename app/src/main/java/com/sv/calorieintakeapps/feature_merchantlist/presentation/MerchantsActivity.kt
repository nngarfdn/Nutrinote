package com.sv.calorieintakeapps.feature_merchantlist.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.ActivityMerchantsBinding
import com.sv.calorieintakeapps.feature_merchantlist.di.MerchantListModule
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MerchantsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMerchantsBinding
    private val viewModel: MerchantListViewModel by viewModel()
    private lateinit var adapterMerchant: AdapterMerchant
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMerchantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MerchantListModule.load()
        observe()
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.allMerchants.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        adapterMerchant = AdapterMerchant()
                        binding.apply {
                            rvRestauranList.apply {
                                val lm = LinearLayoutManager(context)
                                layoutManager = lm
                                setHasFixedSize(true)
                                adapterMerchant.data = result.data
                                adapter = adapterMerchant
                                adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MerchantListModule.unload()
    }
}