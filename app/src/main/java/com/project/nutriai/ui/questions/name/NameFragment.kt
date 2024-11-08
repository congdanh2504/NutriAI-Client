package com.project.nutriai.ui.questions.name

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.project.nutriai.databinding.FragmentNameBinding
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : BaseFragment<FragmentNameBinding, NameViewModel>() {

    override val viewModel: NameViewModel by viewModels()
    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNameBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListener()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.etName.setText(AppPref.userDetail.fullName)
        binding.etAge.setText(AppPref.userDetail.age.toString())
    }

    private fun initListener() {
        binding.etName.addTextChangedListener {
            viewModel.updateName(it.toString())
        }
        binding.etAge.addTextChangedListener {
            if (it.toString().isEmpty()) return@addTextChangedListener
            viewModel.updateAge(it.toString().toInt())
        }
    }

}