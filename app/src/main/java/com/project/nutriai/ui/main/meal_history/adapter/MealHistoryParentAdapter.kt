package com.project.nutriai.ui.main.meal_history.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.domain.model.HistoryMeal
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemMealHistoryParentBinding
import com.project.nutriai.ui.main.meal_history.MealHistoryByDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MealHistoryParentAdapter(
    private val onHistoryMealClick: (HistoryMeal) -> Unit
) :
    ListAdapter<MealHistoryByDay, MealHistoryParentAdapter.MealHistoryParentViewHolder>(
        MealHistoryByDayDiffCallback()
    ) {

    class MealHistoryByDayDiffCallback : DiffUtil.ItemCallback<MealHistoryByDay>() {
        override fun areItemsTheSame(
            oldItem: MealHistoryByDay,
            newItem: MealHistoryByDay
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: MealHistoryByDay,
            newItem: MealHistoryByDay
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHistoryParentViewHolder {
        val binding =
            ItemMealHistoryParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealHistoryParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealHistoryParentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MealHistoryParentViewHolder(
        private val binding: ItemMealHistoryParentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(mealHistoryByDay: MealHistoryByDay) {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            val date = LocalDate.parse(mealHistoryByDay.date, formatter)

            val day =
                if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else date.dayOfMonth.toString()
            val month =
                if (date.monthValue < 10) "0${date.monthValue}" else date.monthValue.toString()
            val year = date.year

            binding.tvDayOfWeek.text = getDayName(mealHistoryByDay.date, binding.root.context)
            binding.tvDay.text = day
            binding.tvMonthYear.text = "$month/$year"
            binding.tvTotalCalories.text = mealHistoryByDay.totalCalories.toString() + " kcal"
            binding.rvMealHistory.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvMealHistory.adapter = MealHistoryChildAdapter(mealHistoryByDay.historyMeals, onHistoryMealClick)
        }

        private fun getDayName(dateString: String, context: Context): String {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            val date = LocalDate.parse(dateString, formatter)
            val today = LocalDate.now()

            return when (date) {
                today -> context.getString(R.string.today)
                today.minus(1, ChronoUnit.DAYS) -> context.getString(R.string.yesterday)
                today.plus(1, ChronoUnit.DAYS) -> context.getString(R.string.tomorrow)
                else -> when (date.dayOfWeek) {
                    DayOfWeek.MONDAY -> context.getString(R.string.monday)
                    DayOfWeek.TUESDAY -> context.getString(R.string.tuesday)
                    DayOfWeek.WEDNESDAY -> context.getString(R.string.wednesday)
                    DayOfWeek.THURSDAY -> context.getString(R.string.thursday)
                    DayOfWeek.FRIDAY -> context.getString(R.string.friday)
                    DayOfWeek.SATURDAY -> context.getString(R.string.saturday)
                    DayOfWeek.SUNDAY -> context.getString(R.string.sunday)
                    else -> ""
                }
            }
        }
    }

}