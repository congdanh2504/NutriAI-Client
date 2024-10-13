package com.project.nutriai.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityLoginBinding
import com.project.nutriai.extensions.startActivity
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun initView() {

    }

    private fun initListener() {
        binding.tvSignUp.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }
}