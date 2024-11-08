package com.project.nutriai.ui.main.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentSettingsBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.favorite.FavoriteMealActivity
import com.project.nutriai.ui.login.LoginActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        initListeners()
        bindViewModel()
    }

    private fun initListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        binding.cvLanguage.setOnClickListener {
            viewModel.changeLanguage()
        }
        binding.cvFavorite.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteMealActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        viewModel.languageStream.collectInViewLifecycle(this) { language ->
            binding.tvLanguage.text = when (language) {
                LanguageEnum.ENGLISH -> "EN"
                LanguageEnum.VIETNAMESE -> "VI"
            }
            binding.ivFlag.setImageResource(
                when (language) {
                    LanguageEnum.ENGLISH -> R.drawable.ic_usa_flag
                    LanguageEnum.VIETNAMESE -> R.drawable.ic_vietnam_flag
                }
            )
        }
    }
}