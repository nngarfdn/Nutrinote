package com.sv.calorieintakeapps.feature_reporting.presentation

import android.Manifest
import android.R
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.sv.calorieintakeapps.databinding.ActivityReportingBinding
import com.sv.calorieintakeapps.feature_foodnutrition.data.OnClickItemMode
import com.sv.calorieintakeapps.feature_reporting.di.ReportingModule
import com.sv.calorieintakeapps.library_common.action.Actions
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_EXPECT_SEARCH
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_CALORIES
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_CARBS
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_FAT
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_PROTEIN
import com.sv.calorieintakeapps.library_common.action.Actions.openFoodNutritionSearchIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent
import com.sv.calorieintakeapps.library_common.ui.dialog.DatePickerFragment
import com.sv.calorieintakeapps.library_common.ui.dialog.TimePickerFragment
import com.sv.calorieintakeapps.library_common.util.gone
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.domain.model.Report
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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
    private val reportingViewModel: ReportingViewModel by viewModel()
    private var reportId = -1
    private var foodId = -1
    private var nilaigiziComFoodId = -1
    private var preImageUri: Uri? = null
    private var preImageFile: File? = null
    private var postImageUri: Uri? = null
    private var postImageFile: File? = null
    private var merchantId = -1
    private var foodName: String? = null

    private var isUpdate = false
    
    private var mood = ""
//    private var percentage: Int? = null;
    
    private var calories: String? = null
    private var protein: String? = null
    private var fat: String? = null
    private var carbs: String? = null

    private var expectSearch: Boolean = false

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    val itemMood = arrayOf("Senang/Semangat", "Sedih/Sakit", "Biasa Saja")

    private val cameraIntentLauncherPreImage = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
        if (isSaved) {
            binding.imgPreImage.load(preImageFile)
        }
    }

    private val galleryIntentLauncherPreImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            imageUri?.let {
                this.preImageUri = it
                this.preImageFile = uriToFile(this, it)
                binding.imgPreImage.load(preImageFile)
            }
        }
    }

    private val fileManagerIntentLauncherPreImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            this.preImageUri = it
            this.preImageFile = uriToFile(this, it)
            binding.imgPreImage.load(preImageFile)
        }
    }

    private val cameraIntentLauncherPostImage = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
        if (isSaved) {
            binding.imgPostImage.load(postImageFile)
        }
    }

    private val galleryIntentLauncherPostImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            imageUri?.let {
                this.postImageUri = it
                this.postImageFile = uriToFile(this, it)
                binding.imgPostImage.load(postImageFile)
            }
        }
    }

    private val fileManagerIntentLauncherPostImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            this.postImageUri = it
            this.postImageFile = uriToFile(this, it)
            binding.imgPostImage.load(postImageFile)
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        // Get the content resolver
        val contentResolver: ContentResolver = context.contentResolver

        // Create a temporary file to save the content from URI
        val file = File(context.cacheDir, "temp_image_file.jpg") // Adjust name or extension as needed
        try {
            // Open an input stream from the URI
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            // Create an output stream to write the file
            val outputStream = FileOutputStream(file)

            // Copy the content from the input stream to the output stream
            inputStream?.copyTo(outputStream)

            // Close the streams
            inputStream?.close()
            outputStream.close()

            return file // Return the file created
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ReportingModule.load()
        
        shouldAskStoragePermission(RC_READ_EXTERNAL_STORAGE_PERMISSION)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                foodId = result.data!!.getIntExtra(Actions.EXTRA_FOOD_ID, -1)
                foodName = result.data!!.getStringExtra(Actions.EXTRA_FOOD_NAME)
                nilaigiziComFoodId = result.data!!.getIntExtra(Actions.EXTRA_NILAIGIZI_COM_FOOD_ID, -1)
                merchantId = result.data!!.getIntExtra(Actions.EXTRA_MERCHANT_ID, -1)
                calories = result.data!!.getStringExtra(EXTRA_NILAIGIZI_COM_CALORIES)
                protein = result.data!!.getStringExtra(EXTRA_NILAIGIZI_COM_PROTEIN)
                fat = result.data!!.getStringExtra(EXTRA_NILAIGIZI_COM_FAT)
                carbs = result.data!!.getStringExtra(EXTRA_NILAIGIZI_COM_CARBS)

                binding.apply {
                    edtFoodName.setText(foodName)

                    if (nilaigiziComFoodId != -1) {
                        edtPortionSize.setText("100 g/porsi")
                        edtPortionSize.isEnabled = false
                    }

                    if (foodId != -1) {
                        edtPortionSize.gone()
                    }
                }
            }
        }
        
        isUpdate = intent.hasExtra(Actions.EXTRA_REPORT_ID)
        reportId = intent.getIntExtra(Actions.EXTRA_REPORT_ID, -1)
        foodId = intent.getIntExtra(Actions.EXTRA_FOOD_ID, -1)
        foodName = intent.getStringExtra(Actions.EXTRA_FOOD_NAME)
        nilaigiziComFoodId = intent.getIntExtra(Actions.EXTRA_NILAIGIZI_COM_FOOD_ID, -1)
        merchantId = intent.getIntExtra(Actions.EXTRA_MERCHANT_ID, -1)
        
        calories = intent.getStringExtra(EXTRA_NILAIGIZI_COM_CALORIES)
        protein = intent.getStringExtra(EXTRA_NILAIGIZI_COM_PROTEIN)
        fat = intent.getStringExtra(EXTRA_NILAIGIZI_COM_FAT)
        carbs = intent.getStringExtra(EXTRA_NILAIGIZI_COM_CARBS)
        expectSearch = intent.getBooleanExtra(EXTRA_EXPECT_SEARCH, false)
        
        bindUI()
    }

    private fun bindUI() {
        binding.edtPercent.filters = arrayOf<InputFilter>(MinMaxFilter(0, 100))

        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, itemMood)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerMood.adapter = arrayAdapter
            edtDate.setOnClickListener(this@ReportingActivity)
            edtTime.setOnClickListener(this@ReportingActivity)
            edtPercent.setOnClickListener(this@ReportingActivity)
            btnPreImage.setOnClickListener(this@ReportingActivity)
            btnPostImage.setOnClickListener(this@ReportingActivity)
            btnSave.setOnClickListener(this@ReportingActivity)
            btnDelete.setOnClickListener(this@ReportingActivity)
            btnBack.setOnClickListener { onBackPressed() }
            btn0Percent.setOnClickListener {
                edtPercent.setText("0")
            }
            btn25Percent.setOnClickListener {
                edtPercent.setText("25")
            }
            btn50Percent.setOnClickListener {
                edtPercent.setText("50")
            }
            btn75Percent.setOnClickListener {
                edtPercent.setText("75")
            }
            btn95Percent.setOnClickListener {
                edtPercent.setText("95")
            }
            btn100Percent.setOnClickListener {
                edtPercent.setText("100")
            }

            if (isUpdate) {
                tvTitle.text = "Edit Laporan"
                btnDelete.visibility = View.VISIBLE
                observeEditReportResult()
                observeGetReportById()

                edtFoodName.isEnabled = false
            } else {
                tvTitle.text = "Buat Laporan"
                btnDelete.visibility = View.GONE
                observeAddReportResult()

                edtFoodName.isEnabled = foodName == null
            }

            edtFoodName.setText(foodName)

            if (nilaigiziComFoodId != -1) {
                edtPortionSize.setText("100 g/porsi")
                edtPortionSize.isEnabled = false
            }

            if (foodId != -1) {
                edtPortionSize.gone()
            }

            if (expectSearch) {
                edtFoodName.isClickable = true
                edtFoodName.setOnClickListener {
                    activityResultLauncher.launch(openFoodNutritionSearchIntent(null, OnClickItemMode.RETURN_DATA))
                }
            }
        }
    }

    private fun showImagePickerDialog(viewId: Int) {
        val options = arrayOf("Camera", "Gallery", "File Manager")
        val dialog = AlertDialog.Builder(this).setTitle("Select Image Source")
        when(viewId) {
            binding.btnPreImage.id -> {
                dialog.setItems(options) { _, which ->
                        when (which) {
                            0 -> openCameraPreImage()
                            1 -> openGalleryPreImage()
                            2 -> openFileManagerPreImage()
                        }
                    }
                    .show()

            }
            binding.btnPostImage.id -> {
                dialog.setItems(options) { _, which ->
                        when (which) {
                            0 -> openCameraPostImage()
                            1 -> openGalleryPostImage()
                            2 -> openFileManagerPostImage()
                        }
                    }
                    .show()
            }
        }
    }

    private fun openCameraPreImage() {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        preImageFile = File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", storageDir)
        preImageUri = FileProvider.getUriForFile(this, "${this.packageName}.fileprovider", preImageFile!!)
        cameraIntentLauncherPreImage.launch(preImageUri)
    }

    private fun openGalleryPreImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntentLauncherPreImage.launch(galleryIntent)
    }

    private fun openFileManagerPreImage() {
        fileManagerIntentLauncherPreImage.launch("image/*")
    }

    private fun openCameraPostImage() {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        postImageFile = File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", storageDir)
        postImageUri = FileProvider.getUriForFile(this, "${this.packageName}.fileprovider", postImageFile!!)
        cameraIntentLauncherPostImage.launch(postImageUri)
    }

    private fun openGalleryPostImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntentLauncherPostImage.launch(galleryIntent)
    }

    private fun openFileManagerPostImage() {
        fileManagerIntentLauncherPostImage.launch("image/*")
    }

    inner class MinMaxFilter() : InputFilter {
        private var intMin: Int = 0
        private var intMax: Int = 0
        
        // Initialized
        constructor(minValue: Int, maxValue: Int) : this() {
            this.intMin = minValue
            this.intMax = maxValue
        }
        
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dStart: Int,
            dEnd: Int,
        ): CharSequence? {
            try {
                val input = Integer.parseInt(dest.toString() + source.toString())
                if (isInRange(intMin, intMax, input)) {
                    return null
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return ""
        }
        
        // Check if input c is in between min a and max b and
        // returns corresponding boolean
        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }
    
    private fun observeAddReportResult() {
        reportingViewModel.addReportResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        binding.btnSave.isEnabled = false
                    }
                    
                    is Resource.Success -> {
                        showToast("Berhasil mengirim laporan")
                        startActivity(
                            openHomepageIntent().setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                        Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        )
                    }
                    
                    is Resource.Error -> {
                        binding.btnSave.isEnabled = true
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    private fun observeEditReportResult() {
        reportingViewModel.editReportResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        binding.btnSave.isEnabled = false
                        binding.btnDelete.isEnabled = false
                    }
                    
                    is Resource.Success -> {
                        showToast("Berhasil mengedit laporan")
                        onBackPressed()
                    }
                    
                    is Resource.Error -> {
                        binding.btnSave.isEnabled = true
                        binding.btnDelete.isEnabled = true
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    private fun observeGetReportById() {
        reportingViewModel.getReportById(reportId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        binding.apply {
                            val report = result.data ?: Report()
                            foodId = report.foodId ?: -1
                            edtDate.setText(report.getDateOnly())
                            edtTime.setText(report.getTimeOnly())
                            
                            edtPercent.setText(report.percentage.toString())
                            if (report.preImageFile != null) {
                                imgPreImage.load(report.preImageFile)
                            } else if (report.preImageUrl.isNotEmpty()) {
                                imgPreImage.load(report.preImageUrl)
                            } else {
                                imgPreImage.setImageResource(com.sv.calorieintakeapps.R.drawable.img_no_image_24)
                            }
                            if (report.postImageFile != null) {
                                imgPostImage.load(report.postImageFile)
                            } else if (report.preImageUrl.isNotEmpty()) {
                                imgPostImage.load(report.postImageUrl)
                            } else {
                                imgPostImage.setImageResource(com.sv.calorieintakeapps.R.drawable.img_no_image_24)
                            }
                            when (report.mood) {
                                "Senang/Semangat" -> spinnerMood.setSelection(0)
                                "Sedih/Sakit" -> spinnerMood.setSelection(1)
                                "Biasa Saja" -> spinnerMood.setSelection(2)
                            }
                            
                            edtPortionCount.setText((report.portionCount ?: 1).toString())
                            nilaigiziComFoodId = report.nilaigiziComFoodId ?: -1
                            if (report.nilaigiziComFoodId != null) {
                                edtPortionSize.setText("100 g/porsi")
                                edtPortionSize.isEnabled = false
                            }
                            
                            edtPortionSize.gone()
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
            binding.edtPercent.id -> view as EditText
            binding.btnPreImage.id -> showImagePickerDialog(binding.btnPreImage.id)
            binding.btnPostImage.id -> showImagePickerDialog(binding.btnPostImage.id)
            binding.btnSave.id -> saveReport()
            binding.btnDelete.id -> deleteReport()
        }
    }
    
    private fun saveReport() {
        val date = binding.edtDate.text.toString()
        val time = binding.edtTime.text.toString()
        val percentage: Int? =
            if (!binding.edtPercent.text.toString().isEmpty()) binding.edtPercent.text.toString()
                .toInt() else null;
        mood = itemMood[binding.spinnerMood.selectedItemPosition]
        val foodId = if (foodId != -1) foodId else null
        val nilaigiziComFoodId = if (nilaigiziComFoodId != -1) nilaigiziComFoodId else null
        val foodName = binding.edtFoodName.text.toString()
        val portionSize = binding.edtPortionSize.text.toString()
        val portionCount = binding.edtPortionCount.text.toString().ifBlank { "1" }.toFloat()
        val merchantId = if (merchantId != -1) merchantId else null
        
        if (isUpdate) {
            reportingViewModel.editReport(
                reportId = reportId,
                date = date,
                time = time,
                percentage = percentage,
                mood = mood,
                preImageFile = preImageFile,
                postImageFile = postImageFile,
                foodId = foodId,
                nilaigiziComFoodId = nilaigiziComFoodId,
                portionCount = portionCount,
            )
        } else {
            reportingViewModel.addReport(
                foodId = foodId,
                date = date,
                time = time,
                percentage = percentage,
                mood = mood,
                preImageFile = preImageFile,
                postImageFile = postImageFile,
                nilaigiziComFoodId = nilaigiziComFoodId,
                portionCount = portionCount,
                foodName = foodName,
                portionSize = portionSize,
                merchantId = merchantId,
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
            )
        }
    }
    
    private fun deleteReport() {
        AlertDialog.Builder(this)
            .setTitle("Hapus laporan")
            .setMessage("Apakah kamu yakin ingin menghapus laporan ini?")
            .setPositiveButton("Ya") { _, _ ->
                reportingViewModel.deleteReportById(reportId).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Resource.Loading -> {}
                            
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
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == RC_READ_EXTERNAL_STORAGE_PERMISSION) recreate()
        }
    }
}