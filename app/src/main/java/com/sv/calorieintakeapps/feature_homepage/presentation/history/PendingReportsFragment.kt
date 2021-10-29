//package com.sv.calorieintakeapps.feature.home.presentation.history
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.sv.calorieintakeapps.core.common.util.TAG
//import com.sv.calorieintakeapps.core.common.util.showToast
//import com.sv.calorieintakeapps.feature.home.databinding.FragmentPendingReportsBinding
//import com.sv.calorieintakeapps.feature.home.di.HomepageModule
//import com.sv.calorieintakeapps.library_database.domain.model.Report
//import com.sv.calorieintakeapps.library_database.vo.Resource
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import timber.log.Timber
//
//class PendingReportsFragment : Fragment() {
//
//    private lateinit var binding: FragmentPendingReportsBinding
//    private val viewModel: HistoryViewModel by viewModel()
//    private lateinit var adapterHistory: AdapterHistory
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentPendingReportsBinding.inflate(layoutInflater, container, false)
//        HomepageModule.load()
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.userPendingReports.observe(viewLifecycleOwner) { result ->
//            if (result != null) {
//                when (result) {
//                    is Resource.Loading -> {
//
//                    }
//                    is Resource.Success -> {
//                        Timber.tag(TAG).d("onViewCreated: ${result.data}")
//                        adapterHistory = AdapterHistory(
//                            requireActivity(),
//                            false,
//                            object : HistoryAdapterListener {
//                                override fun onEditClicked(item: Report) {}
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