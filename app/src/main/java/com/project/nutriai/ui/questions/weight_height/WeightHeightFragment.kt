package com.project.nutriai.ui.questions.weight_height

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.nutriai.databinding.FragmentWeightHeightBinding
import com.project.nutriai.ui.base.BaseFragment
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
        bindViewModel()
    }

    private fun initView() {

    }

    private fun bindViewModel() {

    }
}