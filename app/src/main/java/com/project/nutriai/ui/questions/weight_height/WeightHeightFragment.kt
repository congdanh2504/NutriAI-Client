package com.project.nutriai.ui.questions.weight_height

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.project.nutriai.databinding.FragmentWeightHeightBinding
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeightHeightFragment : BaseFragment<FragmentWeightHeightBinding, WeightHeightViewModel>() {

    override val viewModel: WeightHeightViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWeightHeightBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListener()
        bindViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.etWeight.setText(AppPref.userDetail.weight.toString())
        binding.etHeight.setText(AppPref.userDetail.height.toString())
    }

    private fun initListener() {
        binding.etWeight.addTextChangedListener {
            if (it.toString().isEmpty()) return@addTextChangedListener
            viewModel.updateWeight(it.toString().toInt())
        }
        binding.etHeight.addTextChangedListener {
            if (it.toString().isEmpty()) return@addTextChangedListener
            viewModel.updateHeight(it.toString().toInt())
        }
    }

    private fun bindViewModel() {

    }
}