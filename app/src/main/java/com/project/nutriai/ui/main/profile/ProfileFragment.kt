package com.project.nutriai.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.project.nutriai.databinding.FragmentProfileBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.extensions.toReadableString
import com.project.nutriai.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        bindViewModel()
    }

    private fun bindViewModel() {
        val context = requireContext()

        viewModel.profile.collectInViewLifecycle(this) { profile ->
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
        }
        chipGroup.addView(chip)
    }
}