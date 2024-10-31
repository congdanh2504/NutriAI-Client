package com.project.nutriai.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityProfileBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.extensions.toReadableString
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityProfileBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initListeners()
        bindViewModel()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun bindViewModel() {
        val context = this

        viewModel.profile.collectIn(this) { profile ->
            binding.tvName.text = profile.fullName
            binding.tvAge.text = String.format("%s years", profile.age)
            binding.tvGender.text = profile.gender.toReadableString(context)
            binding.tvHeight.text = String.format("%s cm", profile.height)
            binding.tvWeight.text = String.format("%s kg", profile.weight)
            binding.tvPhysicalActivity.text = profile.physicalActivity.toReadableString(context)
            binding.tvNutritionGoal.text = profile.nutritionGoal.toReadableString(context)
            binding.tvDietPreference.text = profile.dietPreference.toReadableString(context)

            if (profile.foodAllergies.isEmpty()) {
                binding.cvFoodAllergies.isVisible = false
            } else {
                profile.foodAllergies.forEach {
                    addChipToGroup(binding.cgFoodAllergies, it.toReadableString(context))
                }
            }

            if (profile.healthConditions.isEmpty()) {
                binding.cvHealthConditions.isVisible = false
            } else {
                profile.healthConditions.forEach {
                    addChipToGroup(binding.cgHealthConditions, it.toReadableString(context))
                }
            }
        }
    }

    private fun addChipToGroup(chipGroup: ChipGroup, text: String) {
        val chip = Chip(chipGroup.context).apply {
            this.text = text
            this.setTextColor(getColor(R.color.textColor))
        }
        chipGroup.addView(chip)
    }
}