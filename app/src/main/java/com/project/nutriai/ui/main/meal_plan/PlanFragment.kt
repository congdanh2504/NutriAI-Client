package com.project.nutriai.ui.main.meal_plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.FragmentPlanBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.main.MainViewModel
import com.project.nutriai.ui.meal_detail.MealDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanFragment : BaseFragment<FragmentPlanBinding, MainViewModel>() {

    override val viewModel: MainViewModel by activityViewModels()
    private val adapter: MealPlanAdapter by lazy {
        MealPlanAdapter {
            val intent = MealDetailsActivity.getIntent(requireContext(), it.id)
            startActivity(intent)
        }
    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlanBinding {
        return FragmentPlanBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvPlan.adapter = adapter
        binding.rvPlan.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun bindViewModel() {
        viewModel.state.collectInViewLifecycle(
            this
        ) { state ->
            if (state.isLoading) {
                binding.progressBar.isVisible = true
                binding.rvPlan.isVisible = false
            } else {
                binding.rvPlan.isVisible = true
                state.mealsPlan?.let {
                    adapter.submitList(it.meals)
                }

                binding.progressBar.isVisible = false
                if (state.error.isNotEmpty()) {
                    showErrorMessage(state.error)
                }
            }
        }
    }
}