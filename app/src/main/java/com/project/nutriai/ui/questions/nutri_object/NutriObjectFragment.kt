package com.project.nutriai.ui.questions.nutri_object

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.FragmentNutriObjectBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NutriObjectFragment : BaseFragment<FragmentNutriObjectBinding, NutriObjectViewModel>() {

    override val viewModel: NutriObjectViewModel by viewModels()
    private val adapter = AnswerAdapter {
        viewModel.onAnswerSelected(it)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNutriObjectBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvNutriObject.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNutriObject.adapter = adapter
    }

    private fun bindViewModel() {
        viewModel.answers.collectInViewLifecycle(this) {
            adapter.submitList(it)
        }
    }
}