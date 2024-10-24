package com.project.nutriai.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.nutriai.databinding.ActivityRegisterBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityRegisterBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        bindViewModel()
    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showErrorMessage("Please fill all fields")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showErrorMessage("Password and confirm password must be the same")
                return@setOnClickListener
            }

            viewModel.register(email, username, password)
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun bindViewModel() {
        viewModel.registerViewState.collectIn(this) { state ->
            binding.apply {
                if (state.isLoading) {
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                    if (state.isSuccess) {
                        showSuccessMessage("Register success")
                        lifecycleScope.launch {
                            delay(2000)
                            finish()
                        }
                    } else if (state.error.isNotEmpty()) {
                        showErrorMessage(state.error)
                    }
                }
            }
        }
    }
}
