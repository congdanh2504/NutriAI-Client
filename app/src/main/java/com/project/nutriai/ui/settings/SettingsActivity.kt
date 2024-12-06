package com.project.nutriai.ui.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivitySettingsBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.favorite.FavoriteMealActivity
import com.project.nutriai.ui.login.LoginActivity

class SettingsActivity : BaseActivity<ActivitySettingsBinding, SettingsViewModel>() {
    override val viewModel: SettingsViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater): ActivitySettingsBinding {
        return ActivitySettingsBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initListeners()
        bindViewModel()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.cvLanguage.setOnClickListener {
            viewModel.changeLanguage()
        }
        binding.cvFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteMealActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        viewModel.languageStream.collectIn(this) { language ->
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