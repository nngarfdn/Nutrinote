//package com.sv.calorieintakeapps.feature.home.presentation.history
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.sv.calorieintakeapps.core.common.util.TAG
//import com.sv.calorieintakeapps.core.common.util.showToast
//import com.sv.calorieintakeapps.feature.home.databinding.FragmentCompletedReportsBinding
//import com.sv.calorieintakeapps.feature.home.di.HomepageModule
//import com.sv.calorieintakeapps.library.database.domain.model.Report
//import com.sv.calorieintakeapps.library.database.vo.Resource
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import timber.log.Timber
//
//class CompletedReportsFragment : Fragment() {
//
//    private lateinit var binding: FragmentCompletedReportsBinding
//    private val viewModel: HistoryViewModel by viewModel()
//    private lateinit var adapterHistory: AdapterHistory
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentCompletedReportsBinding.inflate(layoutInflater, container, false)
//        HomepageModule.load()
//        return binding.root
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        HomepageModule.unload()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel.userCompletedReports.observe(viewLifecycleOwner) { result ->
//            if (result != null) {
//                when (result) {
//                    is Resource.Loading -> {
//
//                    }
//                    is Resource.Success -> {
//                        Timber.tag(TAG).d("onViewCreated: ${result.data}")
//                        adapterHistory = AdapterHistory(
//                            requireActivity(),
//                            true,
//                            object : HistoryAdapterListener {
//                                override fun onEditClicked(item: Report) {
//
//                                }
//                            })
//                        binding.apply {
//                            rvProsesRiwayat.apply {
//                                val lm = LinearLayoutManager(context)
//                                layoutManager = lm
//                                setHasFixedSize(true)
//                                adapterHistory.data = result.data
//                                adapter = adapterHistory
//                                adapter?.notifyDataSetChanged()
//                            }
//                        }
//                    }
//                    is Resource.Error -> {
//                        activity?.showToast(result.message)
//                    }
//                }
//            }
//        }
//    }
//}