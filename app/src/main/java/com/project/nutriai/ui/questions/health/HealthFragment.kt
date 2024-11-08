package com.project.nutriai.ui.questions.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.FragmentHealthBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.questions.nutri_object.AnswerAdapter
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : BaseFragment<FragmentHealthBinding, HealthViewModel>() {

    override val viewModel: HealthViewModel by viewModels()
    private val adapter = AnswerAdapter {
        viewModel.onHealthSelected(it)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHealthBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvHealth.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHealth.adapter = adapter
        viewModel.onHealthSelected(AppPref.userDetail.healthConditions)
    }

    private fun bindViewModel() {
        viewModel.healths.collectInViewLifecycle(this) {
            adapter.submitList(it)
        }
    }
}
