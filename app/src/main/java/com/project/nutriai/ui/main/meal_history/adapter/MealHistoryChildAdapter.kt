package com.project.nutriai.ui.main.meal_history.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.domain.model.HistoryMeal
import com.project.domain.model.HistoryMealType
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemMealHistoryChildBinding
import com.project.nutriai.extensions.toReadableString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MealHistoryChildAdapter(
    private val historyMeals: List<HistoryMeal>,
    private val onHistoryMealClick: (HistoryMeal) -> Unit
) : RecyclerView.Adapter<MealHistoryChildAdapter.MealHistoryChildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHistoryChildViewHolder {
        val binding =
            ItemMealHistoryChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealHistoryChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealHistoryChildViewHolder, position: Int) {
        holder.bind(historyMeals[position])
    }

    override fun getItemCount(): Int {
        return historyMeals.size
    }

    inner class MealHistoryChildViewHolder(
        private val binding: ItemMealHistoryChildBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(historyMeal: HistoryMeal) {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val dateTime = LocalDateTime.parse(historyMeal.dateTime, formatter)
            val hourMinute = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            binding.tvTime.text = hourMinute
            binding.tvMealType.text = historyMeal.category.toReadableString(binding.root.context)
            binding.tvTotalCalories.text = historyMeal.nutritionInfo.calories.toString() + " kcal"
            binding.ivMealType.setImageResource(
                when (historyMeal.category) {
                    HistoryMealType.BREAKFAST -> R.drawable.ic_breakfast
                    HistoryMealType.LUNCH -> R.drawable.ic_lunch
                    HistoryMealType.DINNER -> R.drawable.ic_dinner
                    HistoryMealType.SNACK -> R.drawable.ic_snack
                }
            )
            binding.root.setOnClickListener {
                onHistoryMealClick(historyMeal)
            }
        }
    }

}