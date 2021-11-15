package com.sv.calorieintakeapps.feature_reporting.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityReportingBinding
import com.sv.calorieintakeapps.feature_reporting.di.ReportingModule
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent
import com.sv.calorieintakeapps.library_common.ui.dialog.DatePickerFragment
import com.sv.calorieintakeapps.library_common.ui.dialog.TimePickerFragment
import com.sv.calorieintakeapps.library_common.util.loadImage
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

private const val RC_PICK_PRE_IMAGE = 100
private const val RC_PICK_POST_IMAGE = 101
private const val RC_READ_EXTERNAL_STORAGE_PERMISSION = 200

private const val DATE_PICKER_TAG = "DatePicker"
private const val TIME_PICKER_TAG = "TimePicker"

private const val DATE_FORMAT = "yyyy-MM-dd"
private const val TIME_FORMAT = "HH:mm:00"

class ReportingActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private lateinit var binding: ActivityReportingBinding
    private val viewModel: ReportingViewModel by viewModel()
    private var reportId = -1
    private var foodId = -1
    private var preImageUri = ""
    private var postImageUri = ""

    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReportingModule.load()

        shouldAskStoragePermission(RC_READ_EXTERNAL_STORAGE_PERMISSION)

        isUpdate = intent.hasExtra(Actions.EXTRA_REPORT_ID)
        reportId = intent.getIntExtra(Actions.EXTRA_REPORT_ID, -1)
        foodId = intent.getIntExtra(Actions.EXTRA_FOOD_ID, -1)
        val foodName = intent.getStringExtra(Actions.EXTRA_FOOD_NAME).toString()

        binding.apply {
            tvFoodName.text = foodName
            edtDate.setOnClickListener(this@ReportingActivity)
            edtTime.setOnClickListener(this@ReportingActivity)
            btnPreImage.setOnClickListener(this@ReportingActivity)
            btnPostImage.setOnClickListener(this@ReportingActivity)
            btnSave.setOnClickListener(this@ReportingActivity)
            btnDelete.setOnClickListener(this@ReportingActivity)
            btnBack.setOnClickListener { onBackPressed() }

            if (isUpdate) {
                tvTitle.text = "Edit Laporan"
                btnDelete.visibility = View.VISIBLE
                observeEditReportResult()
                observeGetReportById()
            } else {
                tvTitle.text = "Buat Laporan"
                btnDelete.visibility = View.GONE
                observeAddReportResult()
            }
        }
    }

    private fun observeAddReportResult() {
        viewModel.addReportResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        showToast("Berhasil mengirim laporan")
                        applicationContext.openHomepageIntent()
                    }
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }

    private fun observeEditReportResult() {
        viewModel.editReportResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        showToast("Berhasil mengedit laporan")
                        onBackPressed()
                    }
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }

    private fun observeGetReportById() {
        viewModel.getReportById(reportId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        binding.apply {
                            val report = result.data ?: Report()
                            foodId = report.foodId
                            edtDate.setText(report.getDateOnly())
                            edtTime.setText(report.getTimeOnly())
                            imgPreImage.loadImage(report.preImage)
                            imgPostImage.loadImage(report.postImage)
                        }
                    }
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.edtDate.id -> showDatePicker()
            binding.edtTime.id -> showTimePicker()
            binding.btnPostImage.id -> chooseImage(RC_PICK_POST_IMAGE)
            binding.btnPreImage.id -> chooseImage(RC_PICK_PRE_IMAGE)
            binding.btnSave.id -> saveReport()
            binding.btnDelete.id -> deleteReport()
        }
    }

    private fun saveReport() {
        val date = binding.edtDate.text.toString()
        val time = binding.edtTime.text.toString()

        if (isUpdate) {
            viewModel.editReport(
                reportId = reportId,
                date = date, time = time,
                preImageUri = preImageUri, postImageUri = postImageUri
            )
        } else {
            viewModel.addReport(
                foodId = foodId,
                date = date, time = time,
                preImageUri = preImageUri, postImageUri = postImageUri
            )
        }
    }

    private fun deleteReport() {
        AlertDialog.Builder(this)
            .setTitle("Hapus laporan")
            .setMessage("Apakah kamu yakin ingin menghapus laporan ini?")
            .setPositiveButton("Ya") { _, _ ->
                viewModel.deleteReportById(reportId).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                showToast("Laporan berhasil dihapus")
                                onBackPressed()
                            }
                            is Resource.Error -> {
                                showToast(result.message)
                            }
                        }
                    }
                }
            }
            .setNegativeButton("Tidak", null)
            .create().show()
    }

    private fun showDatePicker() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        binding.edtDate.setText(dateFormat.format(calendar.time))
    }

    private fun showTimePicker() {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, TIME_PICKER_TAG)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        binding.edtTime.setText(dateFormat.format(calendar.time))
    }

    private fun chooseImage(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Unggah foto"), requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val imageUri = data?.data
            when (requestCode) {
                RC_PICK_PRE_IMAGE -> {
                    val dir = getRealPathFromURI(imageUri)
                    if (dir != null) {
                        this.preImageUri = dir
                    }
                    binding.imgPreImage.loadImage(imageUri)
                }
                RC_PICK_POST_IMAGE -> {
                    val dir = getRealPathFromURI(imageUri)
                    if (dir != null) {
                        this.postImageUri = dir
                    }
                    binding.imgPostImage.loadImage(imageUri)
                }
            }
        }
    }

    private fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = managedQuery(contentUri, proj, null, null, null)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        ReportingModule.unload()
    }

    @Suppress("SameParameterValue")
    private fun shouldAskStoragePermission(requestCode: Int): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == RC_READ_EXTERNAL_STORAGE_PERMISSION) recreate()
        }
    }
}