package com.sv.calorieintakeapps.feature_profile.presentation

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityProfileBinding
import com.sv.calorieintakeapps.feature_profile.di.ProfileModule
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.domain.enum.Gender
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_PICK_PROFILE_IMAGE = 100

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModel()
    private var id: Int = 0
    private var profileImageUri = ""
    private var gender = Gender.FEMALE
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ProfileModule.load()
        if (shouldAskPermissions()) askPermissions()
        observe()
        val itemGender = arrayOf("Laki-laki", "Perempuan")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemGender)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerSex.adapter = arrayAdapter
            imgSave.setOnClickListener {
                val name = edtName.text.toString()
                val pass = edtPassword.text.toString()
                if (spinnerSex.selectedItemPosition == 0) gender = Gender.MALE
                if (spinnerSex.selectedItemPosition == 1) gender = Gender.FEMALE
                val age = edtAge.text.toString()
                val height = edtHeight.text.toString()
                val weight = edtWeight.text.toString()
                actionEdit(name, gender, age, pass, profileImageUri, height, weight)
            }
            btnBack.setOnClickListener { onBackPressed() }
            imageButton.setOnClickListener { chooseImage(RC_PICK_PROFILE_IMAGE) }
        }
    }
    
    private fun actionEdit(
        name: String,
        gender: Gender,
        age: String,
        pass: String,
        profileImage: String,
        height: String,
        weight: String,
    ) {
        viewModel.editUserProfile(
            name,
            profileImage,
            gender,
            if (age.isNotEmpty()) age.toInt() else 0,
            pass,
            if (height.isNotEmpty()) height.toInt() else 0,
            if (weight.isNotEmpty()) weight.toInt() else 0,
        )
            .observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Resource.Loading -> {}
                        
                        is Resource.Success -> {
                            showToast("Profil berhasil disimpan")
                            onBackPressed()
                        }
                        
                        is Resource.Error -> {
                            showToast(result.message)
                        }
                    }
                }
            }
    }
    
    private fun observe() {
        viewModel.userProfile.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        binding.apply {
                            val data = result.data
                            id = data?.id!!
                            tvName.text = data.name
                            tvEmail.text = data.email
                            if (data.photo != "") imageButton.load(data.photo)
                            edtName.setText(data.name)
                            edtEmail.setText(data.email)
                            edtWeight.setText(data.weight.toString())
                            edtHeight.setText(data.height.toString())
                            data.age.let { edtAge.setText(it.toString()) }
                            if (data.gender == Gender.MALE) spinnerSex.setSelection(0)
                            if (data.gender == Gender.FEMALE) spinnerSex.setSelection(1)
                        }
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    private fun shouldAskPermissions(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }
    
    @TargetApi(23)
    private fun askPermissions() {
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        val requestCode = 200
        requestPermissions(permissions, requestCode)
    }
    
    private fun chooseImage(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Unggah foto"), requestCode)
    }
    
    private fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = managedQuery(contentUri, proj, null, null, null)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val imageUri = data?.data
            when (requestCode) {
                RC_PICK_PROFILE_IMAGE -> {
                    val dir = getRealPathFromURI(imageUri)
                    if (dir != null) this.profileImageUri = dir
                    binding.imageButton.load(imageUri)
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        ProfileModule.unload()
    }
}