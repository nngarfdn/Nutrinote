package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.bottomsheet

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sv.calorieintakeapps.R
import com.sv.calorieintakeapps.databinding.FilterMakronutrienBottomSheetBinding


class FilterMakronutrienBottomSheet : BottomSheetDialogFragment() {

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
        val nutrients = listOf(
            "vit_c", "niasin", "rifobla", "thiamin", "karoten_total",
//            "beta_karoten", "retinol", "seng", "tembaga", "kalium",
//            "natrium", "besi", "fosfor", "kalsium", "abu", "serat",
//            "karbohidrat", "protein", "lemak", "energi", "air"
        )
        addChips(nutrients)
    }

    private fun addChips(nutrients: List<String>) {
        val marginLayoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 4, 8, 4)
        }

        nutrients.forEach { nutrient ->
            val chip = TextView(requireContext()).apply {
                text = nutrient
                setPadding(16, 8, 16, 8)
                background = ContextCompat.getDrawable(context, R.drawable.bg_chip_unselected)
                setTextColor(ContextCompat.getColor(context, R.color.black))
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                gravity = Gravity.CENTER
                typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                setOnClickListener {
                    val isSelected = tag as? Boolean ?: false
                    if (isSelected) {
                        background = ContextCompat.getDrawable(context, R.drawable.bg_chip_unselected)
                        setTextColor(ContextCompat.getColor(context, R.color.black))
                    } else {
                        background = ContextCompat.getDrawable(context, R.drawable.bg_chip_selected)
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                    }
                    tag = !isSelected
                }
                layoutParams = marginLayoutParams
            }

            binding.chipContainer.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}