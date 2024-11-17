package com.sv.calorieintakeapps.feature_add_new_food

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityAddNewFoodBinding
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openReportingIntent
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewFoodActivity : AppCompatActivity() {

    private val viewModel: AddNewFoodViewModel by viewModel()
    private lateinit var binding: ActivityAddNewFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AddNewFoodModule.load()
        observeAddNewFood()
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            btnSave.setOnClickListener { submitNewFood() }
        }
    }

    private fun observeAddNewFood() {
        viewModel.addNewFoodResult.observe(this) { result ->
            when(result) {
                ApiResponse.Empty -> {}
                is ApiResponse.Error -> {}
                is ApiResponse.Success -> {
                    showToast("Berhasil menambah makanan")
                    showAlertDialog()
                }
            }
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

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Makanan telah ditambahkan, mau submit laporan makanan juga?")

        builder.setPositiveButton("Ya") { dialog, _ ->
            finish()
            startActivity(openReportingIntent(
                expectSearch = true,
                writeFoodName = false
            ))
            dialog.dismiss()
        }

        builder.setNegativeButton("Tidak") { dialog, _ ->
            startActivity(
                openHomepageIntent().setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )
            )
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}