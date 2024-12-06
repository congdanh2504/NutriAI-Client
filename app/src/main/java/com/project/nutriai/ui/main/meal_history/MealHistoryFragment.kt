package com.project.nutriai.ui.main.meal_history

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentMealHistoryBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.add_meal_history.AddMealHistoryActivity
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.main.meal_history.adapter.MealHistoryParentAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MealHistoryFragment : BaseFragment<FragmentMealHistoryBinding, MealHistoryViewModel>() {

    override val viewModel: MealHistoryViewModel by viewModels()
    private val adapter by lazy {
        MealHistoryParentAdapter {
            val intent = Intent(requireContext(), AddMealHistoryActivity::class.java).apply {
                putExtra(AddMealHistoryActivity.IS_EDIT_MODE, true)
                putExtra(AddMealHistoryActivity.HISTORY_MEAL, it)
            }
            startActivity(intent)
        }
    }
    private var startDate: String? = null
    private var endDate: String? = null

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMealHistoryBinding {
        return FragmentMealHistoryBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListeners()
        bindViewModel()
    }

    private fun initView() {
        binding.rvMealHistory.adapter = adapter
        binding.rvMealHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.llDateRange.setOnClickListener {
            showDateRangePicker()
        }
        binding.root.setOnRefreshListener {
            viewModel.getHistoryMeals(startDate, endDate)
            binding.root.isRefreshing = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        viewModel.getHistoryMeals(startDate, endDate)
        viewModel.state.collectInViewLifecycle(this) { state ->
            if (state.isLoading) {
                binding.progressBar.isVisible = true
                binding.rvMealHistory.isVisible = false
            } else {
                binding.progressBar.isVisible = false
                binding.rvMealHistory.isVisible = true
                adapter.submitList(state.historyMeals)
                binding.tvTotalCalories.text =
                    state.historyMeals.sumOf { it.totalCalories }.toString() + " kcal"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDateRangePicker() {
        val constraintsBuilder = CalendarConstraints.Builder()

        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(getString(R.string.select_date_range))
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        dateRangePicker.show(childFragmentManager, "date_range_picker")

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val startDate = selection?.first ?: return@addOnPositiveButtonClickListener
            val endDate = selection.second ?: return@addOnPositiveButtonClickListener

            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val formattedStartDate = dateFormat.format(Date(startDate))
            val formattedEndDate = dateFormat.format(Date(endDate))

            this.startDate = formattedStartDate
            this.endDate = formattedEndDate
            binding.tvDateRange.text = "$formattedStartDate - $formattedEndDate"

            viewModel.getHistoryMeals(formattedStartDate, formattedEndDate)
        }
    }

}