package com.project.nutriai.ui.main.analytic

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.domain.model.Analysis
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentAnalyticBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AnalyticFragment : BaseFragment<FragmentAnalyticBinding, AnalyticViewModel>() {
    override val viewModel: AnalyticViewModel by viewModels()
    private var startDate: String? = null
    private var endDate: String? = null

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticBinding {
        return FragmentAnalyticBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListeners()
        bindViewModel()
    }

    private fun initView() {
        // Initialize view here
    }

    private fun initListeners() {
        binding.llDateRange.setOnClickListener {
            showDateRangePicker()
        }
        binding.root.setOnRefreshListener {
            viewModel.getAnalysis(startDate, endDate)
            binding.root.isRefreshing = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        viewModel.getAnalysis(startDate, endDate)
        viewModel.state.collectInViewLifecycle(this) { state ->
            if (state.isLoading) {
                binding.progressBar.isVisible = true
                binding.nestedScrollView.isVisible = false
            } else {
                binding.progressBar.isVisible = false
                binding.nestedScrollView.isVisible = true
                state.analysis?.let { analysis ->
                    binding.tvTotalCalories.text =
                        analysis.nutritionInfos.sumOf { it.calories }.toString() + " kcal"
                    binding.tvRecommendations.text = analysis.note
                    val markwon = Markwon.create(requireContext())
                    markwon.setMarkdown(
                        binding.tvRecommendations,
                        analysis.note.replace("##", "###")
                    )
                    setupPieChart(analysis)
                    setupBarChart(analysis)
                }
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

            viewModel.getAnalysis(formattedStartDate, formattedEndDate)
        }
    }

    private fun setupPieChart(analysis: Analysis) {
        val proteinSum = analysis.nutritionInfos.sumOf { it.protein }
        val carbohydrateSum = analysis.nutritionInfos.sumOf { it.carbohydrates }
        val fatSum = analysis.nutritionInfos.sumOf { it.fats }
        val fiberSum = analysis.nutritionInfos.sumOf { it.fiber }
        val sugarSum = analysis.nutritionInfos.sumOf { it.sugar }

        val allSum = proteinSum + carbohydrateSum + fatSum + fiberSum + sugarSum
        val proteinPercentage = (proteinSum.toFloat() / allSum * 100)
        val carbohydratePercentage = carbohydrateSum.toFloat() / allSum * 100
        val fatPercentage = fatSum.toFloat() / allSum * 100
        val fiberPercentage = fiberSum.toFloat() / allSum * 100
        val sugarPercentage = sugarSum.toFloat() / allSum * 100

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(proteinPercentage))
        entries.add(PieEntry(carbohydratePercentage))
        entries.add(PieEntry(fatPercentage))
        entries.add(PieEntry(fiberPercentage))
        entries.add(PieEntry(sugarPercentage))

        binding.tvProtein.text = getString(R.string.protein_f, proteinPercentage)
        binding.tvCarbohydrates.text =
            getString(R.string.carbohydrates_f, carbohydratePercentage)
        binding.tvFats.text = getString(R.string.fats_f, fatPercentage)
        binding.tvFiber.text = getString(R.string.fiber_f, fiberPercentage)
        binding.tvSugar.text = getString(R.string.sugar_f, sugarPercentage)

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawValues(false)
        dataSet.colors = listOf(
            requireContext().getColor(R.color.protein),
            requireContext().getColor(R.color.carbohydrates),
            requireContext().getColor(R.color.fats),
            requireContext().getColor(R.color.fiber),
            requireContext().getColor(R.color.sugar)
        )
        dataSet.valueTextSize = 16f

        val data = PieData(dataSet)
        binding.pieChart.setDrawEntryLabels(false)
        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(android.R.color.transparent)
        binding.pieChart.setCenterTextSize(18f)

        val legend = binding.pieChart.legend
        legend.isEnabled = false

        binding.pieChart.invalidate()
    }

    private fun setupBarChart(analysis: Analysis) {
        val proteinEntry = BarEntry(1f, analysis.nutritionInfos.sumOf { it.protein }.toFloat())
        val carbohydrateEntry =
            BarEntry(2f, analysis.nutritionInfos.sumOf { it.carbohydrates }.toFloat())
        val fatEntry = BarEntry(3f, analysis.nutritionInfos.sumOf { it.fats }.toFloat())
        val fiberEntry = BarEntry(4f, analysis.nutritionInfos.sumOf { it.fiber }.toFloat())
        val sugarEntry = BarEntry(5f, analysis.nutritionInfos.sumOf { it.sugar }.toFloat())

        val proteinDataSet = BarDataSet(listOf(proteinEntry), getString(R.string.protein)).apply {
            color = requireContext().getColor(R.color.protein)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val carbohydrateDataSet = BarDataSet(
            listOf(carbohydrateEntry),
            getString(R.string.carbohydrate)
        ).apply {
            color = requireContext().getColor(R.color.carbohydrates)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val fatDataSet = BarDataSet(listOf(fatEntry), getString(R.string.fat)).apply {
            color = requireContext().getColor(R.color.fats)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val fiberDataSet = BarDataSet(listOf(fiberEntry), getString(R.string.fiber)).apply {
            color = requireContext().getColor(R.color.fiber)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val sugarDataSet = BarDataSet(listOf(sugarEntry), getString(R.string.sugar)).apply {
            color = requireContext().getColor(R.color.sugar)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val barData =
            BarData(proteinDataSet, carbohydrateDataSet, fatDataSet, fiberDataSet, sugarDataSet)

        barData.barWidth = 0.8f

        binding.barChart.data = barData

        binding.barChart.description.isEnabled = false
        binding.barChart.axisLeft.setDrawGridLines(false)
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.barChart.xAxis.setDrawGridLines(false)
        binding.barChart.xAxis.granularity = 1f
        binding.barChart.legend.isEnabled = true

        binding.barChart.invalidate()
        binding.barChart.animateY(1000)
    }
}