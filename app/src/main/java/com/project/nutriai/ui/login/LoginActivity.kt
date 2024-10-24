package com.project.nutriai.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityLoginBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.extensions.startActivity
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.questions.QuestionsActivity
import com.project.nutriai.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityLoginBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initListener()
        bindViewModel()
    }

    private fun initView() {

    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showErrorMessage("Please fill all fields")
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }
        binding.tvSignUp.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

    private fun bindViewModel() {
        viewModel.loginStatus.collectIn(this) { state ->
            binding.apply {
                if (state.isLoading) {
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                    if (state.isSuccess) {
                        showSuccessMessage("Login success")
                        lifecycleScope.launch {
                            delay(1000)
                            startActivity<QuestionsActivity>(true)
                        }
                    } else if (state.error.isNotEmpty()) {
                        showErrorMessage(state.error)
                    }
                }
            }
        }
    }
}