package com.sv.calorieintakeapps.feature_add_new_food

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        observeAddNewUrt()
        observeAddNewFood()
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            btnSave.setOnClickListener {
                submitNewUrt()
            }
        }
    }

    private fun observeAddNewUrt() {
        viewModel.addNewUrtResult.observe(this) { result ->
            when(result) {
                ApiResponse.Empty -> {}
                is ApiResponse.Error -> {}
                is ApiResponse.Success -> {
                    val ids = result.data
                    Log.d("FIKRI383", ids.toString())
                    submitNewFood(ids)
                }
            }
        }
    }

    private fun submitNewUrt() {
        binding.apply {
            val urtName1 = edtUrtName1.text.toString()
            val urtName2 = edtUrtName2.text.toString()
            val urtName3 = edtUrtName3.text.toString()
            val urtName4 = edtUrtName4.text.toString()
            val urtName5 = edtUrtName5.text.toString()
            val listUrtName = listOf(urtName1, urtName2, urtName3, urtName4, urtName5)
            Log.d("FIKRI332", listUrtName.toString())

            val urtValue1 = edtUrtValue1.text.toString()
            val urtValue2 = edtUrtValue2.text.toString()
            val urtValue3 = edtUrtValue3.text.toString()
            val urtValue4 = edtUrtValue4.text.toString()
            val urtValue5 = edtUrtValue5.text.toString()
            val listUrtValue = listOf(urtValue1, urtValue2, urtValue3, urtValue4, urtValue5)
            Log.d("FIKRI3324", listUrtValue.toString())
            val listNewUrt = listUrtName.zip(listUrtValue) { name, value -> NewUrt(name, value) }.filter { it.name.isNotEmpty() || it.gramOrMlPerPortion.isNotEmpty() }
            Log.d("FIKRI33243", listNewUrt.toString())
            viewModel.addNewUrt(listNewUrt)
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

    private fun submitNewFood(ids: List<String>) {
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
                urtTersedia = ids.joinToString(",")
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