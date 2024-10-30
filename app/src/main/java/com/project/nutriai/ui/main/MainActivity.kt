package com.project.nutriai.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityMainBinding
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.main.home.HomeFragment
import com.project.nutriai.ui.main.profile.ProfileFragment
import com.project.nutriai.ui.main.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = MainPageAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 4
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
                R.id.profile -> binding.viewPager.currentItem = TabLauncherPage.PROFILE.ordinal
                R.id.settings -> binding.viewPager.currentItem = TabLauncherPage.SETTINGS.ordinal
            }
            true
        }
    }
}

class MainPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = TabLauncherPage.entries.size

    override fun createFragment(position: Int) = when (position) {
        TabLauncherPage.HOME.ordinal -> HomeFragment()
        TabLauncherPage.ANALYTICS.ordinal -> HomeFragment()
        TabLauncherPage.PROFILE.ordinal -> ProfileFragment()
        TabLauncherPage.SETTINGS.ordinal -> SettingsFragment()
        else -> throw IllegalArgumentException("Invalid position")
    }
}

enum class TabLauncherPage {
    HOME, ANALYTICS, PROFILE, SETTINGS
}
