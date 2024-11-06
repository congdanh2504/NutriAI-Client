package com.project.nutriai.ui.add_meal_history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.card.MaterialCardView
import com.project.domain.model.HistoryMeal
import com.project.domain.model.HistoryMealType
import com.project.domain.model.NutritionInfo
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityAddMealHistoryBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.add_meal_history.AvailableMealsBottomSheet.Companion.TAG
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddMealHistoryActivity :
    BaseActivity<ActivityAddMealHistoryBinding, AddMealHistoryViewModel>() {

    override val viewModel: AddMealHistoryViewModel by viewModels()
    private var selectedCategory = HistoryMealType.BREAKFAST

    override fun setupViewBinding(inflater: LayoutInflater): ActivityAddMealHistoryBinding {
        return ActivityAddMealHistoryBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initViews()
        initListeners()
        bindViewModel()
    }

    private fun initViews() {
        binding.tvDate.text = getCurrentDate()
        binding.tvTime.text = getCurrentTime()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.tvTime.setOnClickListener {
            showTimePickerDialog()
        }
        binding.btnAvailableMeals.setOnClickListener {
            showAvailableMealsBottomSheet()
        }
        binding.btnSave.setOnClickListener {
            addHistoryMeal()
        }
        binding.cvBreakfast.setOnClickListener {
            selectCategory(HistoryMealType.BREAKFAST)
        }
        binding.cvLunch.setOnClickListener {
            selectCategory(HistoryMealType.LUNCH)
        }
        binding.cvDinner.setOnClickListener {
            selectCategory(HistoryMealType.DINNER)
        }
        binding.cvSnack.setOnClickListener {
            selectCategory(HistoryMealType.SNACK)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        viewModel.searchMeals(null)
        viewModel.mealDetails.collectIn(this) {
            if (it.isLoading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
                it.mealDetails?.let { mealDetails ->
                    binding.etMealName.setText(mealDetails.name)
                    binding.etCalories.setText(mealDetails.nutritionInfo.calories.toString())
                    binding.etProtein.setText(mealDetails.nutritionInfo.protein.toString())
                    binding.etCarbohydrates.setText(mealDetails.nutritionInfo.carbohydrates.toString())
                    binding.etFats.setText(mealDetails.nutritionInfo.fats.toString())
                    binding.etFiber.setText(mealDetails.nutritionInfo.fiber.toString())
                    binding.etSugar.setText(mealDetails.nutritionInfo.sugar.toString())
                }
            }
        }
        viewModel.addMealHistory.filterNotNull().collectIn(this) {
            if (it.isLoading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
                if (it.error.isNotEmpty()) {
                    showErrorMessage(it.error)
                } else {
                    showSuccessMessage(getString(R.string.meal_added_successfully))
                    finish()
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        return currentDate
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = timeFormat.format(Date())
        return currentTime
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.tvDate.text = formattedDate
            }, year, month, day)
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, hourOfDay, currentMinute ->
            val formatterMinute =
                if (currentMinute < 10) "0$currentMinute" else currentMinute.toString()
            val time = "$hourOfDay:$formatterMinute"
            binding.tvTime.text = time
        }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun showAvailableMealsBottomSheet() {
        val bottomSheet = AvailableMealsBottomSheet()
        bottomSheet.setOnMealSelectedListener {
            viewModel.getMealDetailsById(it.id)
        }
        bottomSheet.show(supportFragmentManager, TAG)
    }

    private fun addHistoryMeal() {
        val mealName = binding.etMealName.text.toString()
        if (mealName.isEmpty()) {
            showErrorMessage(getString(R.string.please_enter_meal_name))
            return
        }

        val calories = binding.etCalories.text.toString().toIntSafe()
        val protein = binding.etProtein.text.toString().toIntSafe()
        val carbohydrates = binding.etCarbohydrates.text.toString().toIntSafe()
        val fats = binding.etFats.text.toString().toIntSafe()
        val fiber = binding.etFiber.text.toString().toIntSafe()
        val sugar = binding.etSugar.text.toString().toIntSafe()
        val dateTime = binding.tvDate.text.toString() + " " + binding.tvTime.text.toString()

        val nutritionInfo = NutritionInfo(
            calories,
            protein,
            carbohydrates,
            fats,
            fiber,
            sugar
        )

        val meal = HistoryMeal(
            mealName,
            nutritionInfo,
            viewModel.mealDetails.value.mealDetails?.suitableFor ?: emptyList(),
            viewModel.mealDetails.value.mealDetails?.healthWarnings ?: emptyList(),
            selectedCategory,
            dateTime
        )
        viewModel.addHistoryMeal(meal)
    }

    private fun String.toIntSafe(): Int {
        return try {
            toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun selectCategory(category: HistoryMealType) {
        selectedCategory = category
        val selectedCardViewId = when (category) {
            HistoryMealType.BREAKFAST -> binding.cvBreakfast.id
            HistoryMealType.LUNCH -> binding.cvLunch.id
            HistoryMealType.DINNER -> binding.cvDinner.id
            HistoryMealType.SNACK -> binding.cvSnack.id
        }

        val selectedTextViewId = when (category) {
            HistoryMealType.BREAKFAST -> binding.tvBreakfast.id
            HistoryMealType.LUNCH -> binding.tvLunch.id
            HistoryMealType.DINNER -> binding.tvDinner.id
            HistoryMealType.SNACK -> binding.tvSnack.id
        }

        val cardViews = listOf(
            binding.cvBreakfast,
            binding.cvLunch,
            binding.cvDinner,
            binding.cvSnack
        )
        val textViews = listOf(
            binding.tvBreakfast,
            binding.tvLunch,
            binding.tvDinner,
            binding.tvSnack
        )

        cardViews.forEach {
            if (it.id == selectedCardViewId) {
                selectCardView(it)
            } else {
                unSelectCardView(it)
            }
        }

        textViews.forEach {
            if (it.id == selectedTextViewId) {
                selectTextView(it)
            } else {
                unSelectTextView(it)
            }
        }
    }

    private fun selectCardView(cardView: MaterialCardView) {
        cardView.setCardBackgroundColor(getColor(R.color.lightGreen))
        cardView.strokeColor = getColor(R.color.primaryColor)
    }

    private fun unSelectCardView(cardView: MaterialCardView) {
        cardView.setCardBackgroundColor(getColor(R.color.white))
        cardView.strokeColor = getColor(R.color.darkGray)
    }

    private fun selectTextView(textView: TextView) {
        textView.setTextColor(getColor(R.color.primaryColor))
    }

    private fun unSelectTextView(textView: TextView) {
        textView.setTextColor(getColor(R.color.darkGray))
    }
}