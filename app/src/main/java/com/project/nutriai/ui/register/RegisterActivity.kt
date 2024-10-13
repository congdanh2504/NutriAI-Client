package com.project.nutriai.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.project.nutriai.databinding.ActivityRegisterBinding
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityRegisterBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}
