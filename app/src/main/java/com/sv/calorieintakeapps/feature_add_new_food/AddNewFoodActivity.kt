package com.sv.calorieintakeapps.feature_add_new_food

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityAddNewFoodBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewFoodActivity : AppCompatActivity() {

    private val viewModel: AddNewFoodViewModel by viewModel()
    private lateinit var binding: ActivityAddNewFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AddNewFoodModule.load()
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            btnSave.setOnClickListener { submitNewFood() }
        }
    }

    private fun submitNewFood() {
        binding.apply {
            val foodName = edtFoodName.text.toString()
            val calories = edtCalories.text.toString()
            val carbs = edtCarbs.text.toString()
            val protein = edtProtein.text.toString()
            val fat = edtFat.text.toString()
            val water = edtWater.text.toString()

            val newFood = NewFood(
                name = foodName,
                calories = calories,
                carbs = carbs,
                protein = protein,
                fat = fat,
                water = water,
            )
            viewModel.addNewFood(newFood)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AddNewFoodModule.unload()
    }
}