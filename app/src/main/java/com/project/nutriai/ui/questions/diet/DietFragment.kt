package com.project.nutriai.ui.questions.diet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.FragmentDietBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.questions.nutri_object.AnswerAdapter
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DietFragment : BaseFragment<FragmentDietBinding, DietViewModel>() {

    override val viewModel: DietViewModel by viewModels()
    private val adapter = AnswerAdapter {
        viewModel.onDietSelected(it)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDietBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvDiet.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiet.adapter = adapter
        viewModel.onDietSelected(AppPref.userDetail.dietPreference)
    }

    private fun bindViewModel() {
        viewModel.diet.collectInViewLifecycle(this) {
            adapter.submitList(it)
        }
    }
}
