package com.sv.calorieintakeapps.feature_auth.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityRegisterBinding
import com.sv.calorieintakeapps.feature_auth.di.AuthModule
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    
    private val viewModel: RegisterViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        AuthModule.load()
        
        binding.apply {
            btnRegister.setOnClickListener {
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val pass = edtPassword.text.toString()
                val passConf = edtPasswordConf.text.toString()
                
                if (name.isEmpty() || email.isEmpty() ||
                    pass.isEmpty() || passConf.isEmpty()
                ) {
                    showToast("Lengkapi semua data")
                } else {
                    if (pass == passConf) viewModel.register(name, email, pass)
                    else showToast("Konfirmasi Password Tidak Sama")
                }
            }
            
            btnLogin.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        
        observeRegisterResult()
    }
    
    private fun observeRegisterResult() {
        viewModel.registerResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        showToast("Daftar berhasil, silakan login")
                        startActivity(
                            openHomepageIntent()
                                .setFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                )
                        )
                    }
                    
                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        AuthModule.unload()
    }
}