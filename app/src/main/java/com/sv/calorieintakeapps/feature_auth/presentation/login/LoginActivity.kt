package com.sv.calorieintakeapps.feature_auth.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sv.calorieintakeapps.databinding.ActivityLoginBinding
import com.sv.calorieintakeapps.feature_auth.di.AuthModule
import com.sv.calorieintakeapps.library_common.action.Actions.openHomepageIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openRegisterIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openTutorialIntent
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    
    private val viewModel: LoginViewModel by viewModel()
    
    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        AuthModule.load()
        
        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.login(email, password)
            }
            
            btnRegister.setOnClickListener {
                startActivity(openRegisterIntent())
            }
        }
        
        observeLoginResult()
//        binding.apply {
//            edtEmail.setText("nutrinotegama@gmail.com")
//            edtPassword.setText("nutrinotegama@gmail.com")
//            btnLogin.performClick()
//        }
    }
    
    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}
                    
                    is Resource.Success -> {
                        viewModel.nilaigiziComLogin()
                        startActivity(
                            openTutorialIntent().setFlags(
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