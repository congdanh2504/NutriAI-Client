package com.project.nutriai.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityMainBinding
import com.project.nutriai.extensions.startActivity
import com.project.nutriai.ui.add_meal_history.AddMealHistoryActivity
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.main.analytic.AnalyticFragment
import com.project.nutriai.ui.main.home.HomeFragment
import com.project.nutriai.ui.main.meal_history.MealHistoryFragment
import com.project.nutriai.ui.main.meal_plan.MealPlanFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListeners()
    }

    private fun initView() {
        binding.bottomNavigation.background = null
        setupViewPager()
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity<AddMealHistoryActivity>()
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> binding.viewPager.currentItem = TabLauncherPage.HOME.ordinal
                R.id.analytics -> binding.viewPager.currentItem = TabLauncherPage.ANALYTICS.ordinal
                R.id.history -> binding.viewPager.currentItem = TabLauncherPage.HISTORY.ordinal
                R.id.meal_plan -> binding.viewPager.currentItem = TabLauncherPage.MEAL_PLAN.ordinal
            }
            true
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = MainPageAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 4
    }
}

class MainPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = TabLauncherPage.entries.size

    override fun createFragment(position: Int) = when (position) {
        TabLauncherPage.HOME.ordinal -> HomeFragment()
        TabLauncherPage.ANALYTICS.ordinal -> AnalyticFragment()
        TabLauncherPage.HISTORY.ordinal -> MealHistoryFragment()
        TabLauncherPage.MEAL_PLAN.ordinal -> MealPlanFragment()
        else -> throw IllegalArgumentException("Invalid position")
    }
}

enum class TabLauncherPage {
    HOME, MEAL_PLAN, HISTORY, ANALYTICS
}
