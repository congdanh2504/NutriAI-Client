package com.project.nutriai.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.project.domain.model.Gender
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityProfileBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.extensions.toReadableString
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.questions.QuestionsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    private val updateLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.onProfileUpdated()
        }

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityProfileBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListeners()
        bindViewModel()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivEdit.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra(QuestionsActivity.IS_EDIT_MODE, true)
            }
            updateLauncher.launch(intent)
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
            binding.ivProfile.setImageResource(if (profile.gender == Gender.MALE) R.drawable.man else R.drawable.woman)

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

        viewModel.state.collectIn(this) { state ->
            state.todayCalories?.let {
                binding.tvCaloriesIntake.text = getString(
                    R.string.today_calories_intake_kcal,
                    it.todayCalories,
                    it.dailyCalories
                )
                binding.lpiCaloriesIntake.progress =
                    (it.todayCalories.toFloat() / it.dailyCalories * 100).toInt()
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