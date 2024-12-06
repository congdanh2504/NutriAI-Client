package com.project.nutriai.ui.main.meal_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentMealPlanBinding
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MealPlanFragment : BaseFragment<FragmentMealPlanBinding, MainViewModel>() {

    override val viewModel: MainViewModel by activityViewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMealPlanBinding {
        return FragmentMealPlanBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListeners()
        bindViewModel()
    }

    private fun initView() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.plan)
                1 -> getString(R.string.shopping_list)
                else -> null
            }
        }.attach()

        val currentDate = getCurrentDate()
        binding.tvDate.text = currentDate
        viewModel.getMealsPlan(currentDate)
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            if (getCurrentDate() == binding.tvDate.text.toString()) return@setOnClickListener
            val currentDate = binding.tvDate.text.toString()
            val previousDate = getPreviousDate(currentDate)
            binding.tvDate.text = previousDate
            viewModel.getMealsPlan(previousDate)
        }

        binding.ivNext.setOnClickListener {
            val currentDate = binding.tvDate.text.toString()
            val nextDate = getNextDate(currentDate)
            binding.tvDate.text = nextDate
            viewModel.getMealsPlan(nextDate)
        }
    }

    private fun bindViewModel() {
        // Bind view model
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        return currentDate
    }

    private fun getPreviousDate(currentDate: String): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date = dateFormat.parse(currentDate)
        val calendar = java.util.Calendar.getInstance()
        calendar.time = date
        calendar.add(java.util.Calendar.DATE, -1)
        return dateFormat.format(calendar.time)
    }

    private fun getNextDate(currentDate: String): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date = dateFormat.parse(currentDate)
        val calendar = java.util.Calendar.getInstance()
        calendar.time = date
        calendar.add(java.util.Calendar.DATE, 1)
        return dateFormat.format(calendar.time)
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlanFragment()
            1 -> ShoppingListFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
