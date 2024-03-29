package com.sv.calorieintakeapps.feature_homepage.presentation.history


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.FragmentCompletedReportsBinding
import com.sv.calorieintakeapps.feature_homepage.di.HomepageModule
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompletedReportsFragment : Fragment() {

    private lateinit var binding: FragmentCompletedReportsBinding
    private val viewModel: HistoryViewModel by viewModel()
    private lateinit var adapterHistory: AdapterHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedReportsBinding.inflate(layoutInflater, container, false)
        HomepageModule.load()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        HomepageModule.unload()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userCompletedReports.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        adapterHistory = AdapterHistory(requireActivity(), true)
                        binding.apply {
                            rvProsesRiwayat.apply {
                                val lm = LinearLayoutManager(context)
                                layoutManager = lm
                                setHasFixedSize(true)
                                adapterHistory.data = result.data
                                adapter = adapterHistory
                                adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                    is Resource.Error -> {
                        activity?.showToast(result.message)
                    }
                }
            }
        }
    }
}