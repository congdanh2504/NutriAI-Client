package com.project.nutriai.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.project.domain.model.Meal
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemMealVerticalBinding
import com.project.nutriai.extensions.toReadableString

class MealVerticalAdapter(
    private val onMealClick: (Meal) -> Unit
) : ListAdapter<Meal, MealVerticalAdapter.MealViewHolder>(MealDiffUtil()) {

    class MealDiffUtil : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding =
            ItemMealVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MealViewHolder(
        private val binding: ItemMealVerticalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meal) {
            binding.ivMeal.load(item.imageUrl) {
                placeholder(R.drawable.meal_placeholder)
                error(R.drawable.meal_placeholder)
                diskCachePolicy(CachePolicy.ENABLED)
                memoryCachePolicy(CachePolicy.ENABLED)
            }
            binding.tvMealName.text = item.name
            binding.tvMealCategory.text = item.category.toReadableString(binding.root.context)
            binding.root.setOnClickListener {
                onMealClick(item)
            }
        }
    }
}