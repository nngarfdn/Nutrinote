package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sv.calorieintakeapps.databinding.FilterMakronutrienBottomSheetBinding
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.compose.ChipSheetComponent
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model.FilterMakrotrienUiModel
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model.listFilterMakrotrien
import com.sv.calorieintakeapps.nutridesign.bottomsheet.BottomSheetHandle
import com.sv.calorieintakeapps.nutridesign.button.NutriSelectorButton
import com.sv.calorieintakeapps.nutridesign.text.NutriText


class FilterMakronutrienBottomSheet(
    private val onFilterSelected: (List<FilterMakrotrienUiModel>) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FilterMakronutrienBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterMakronutrienBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                var selectedItems by remember { mutableStateOf<List<FilterMakrotrienUiModel>>(emptyList()) }
                Column {
                    BottomSheetHandle(modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.padding(8.dp))
                    NutriText(
                        text = "Pilih Asupan yang Ditampilkan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    ChipSheetComponent(
                        items = listFilterMakrotrien,
                        selectedItems = selectedItems,
                        onSelectionChanged = { selectedItems = it },
                    )
                    NutriSelectorButton(
                        text = "Terapkan",
                        onClick = {
                            println("selectedItems: $selectedItems")
                            onFilterSelected(selectedItems)
                            dismiss()
                        }
                    )
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}