package com.sv.calorieintakeapps.feature_merchantmenu.presentation

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.ActivityMerchantMenuBinding
import com.sv.calorieintakeapps.feature_merchantmenu.di.MerchantMenuModule
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MerchantMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMerchantMenuBinding

    private val viewModel: MerchantMenuViewModel by viewModel()
    private lateinit var merchantMenuAdapter: MerchantMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMerchantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MerchantMenuModule.load()

        val merchantId = intent.getIntExtra(Actions.EXTRA_MERCHANT_ID, -1)
        viewModel.setMerchantId(merchantId)

        merchantMenuAdapter = MerchantMenuAdapter()

        binding.apply {
            imgCover.load("https://i.imgur.com/Isc5ySZ.png")
            imgBack.setOnClickListener { onBackPressed() }
        }

        binding.edtSearchMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                merchantMenuAdapter.filter.filter(newText)
                return false
            }
        })

        observeMerchantName()
        observeMerchantMenu()
    }

    private fun observeMerchantName() {
        viewModel.merchantName.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        merchantMenuAdapter.merchantName = result.data
                    }
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }

    private fun observeMerchantMenu() {
        viewModel.merchantMenu.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        binding.apply {
                            rvFood.apply {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                merchantMenuAdapter.submitList(result.data)
                                adapter = merchantMenuAdapter
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
        MerchantMenuModule.unload()
    }
}