package com.project.nutriai.ui.questions.lifestyle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.FragmentLifestyleBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.questions.nutri_object.AnswerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LifestyleFragment : BaseFragment<FragmentLifestyleBinding, LifestyleViewModel>() {

    override val viewModel: LifestyleViewModel by viewModels()
    private val adapter = AnswerAdapter {
        viewModel.onLifestyleSelected(it)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLifestyleBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvLifestyle.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLifestyle.adapter = adapter
    }

    private fun bindViewModel() {
        viewModel.lifestyles.collectInViewLifecycle(this) {
            adapter.submitList(it)
        }
    }

}
