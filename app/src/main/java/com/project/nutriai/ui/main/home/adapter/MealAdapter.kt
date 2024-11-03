package com.project.nutriai.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.project.domain.model.Meal
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemMealBinding

class MealAdapter(
    private val meals: List<Meal>,
    private val onMealClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount() = meals.size

    inner class MealViewHolder(
        private val binding: ItemMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.tvDishName.text = meal.name
            binding.ivMeal.load(meal.imageUrl) {
                placeholder(R.drawable.meal_placeholder)
                error(R.drawable.meal_placeholder)
                diskCachePolicy(CachePolicy.ENABLED)
                memoryCachePolicy(CachePolicy.ENABLED)
            }
            binding.root.setOnClickListener {
                onMealClick(meal)
            }
        }
    }
}