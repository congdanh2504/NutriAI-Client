package com.project.nutriai.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.project.domain.model.Meal
import com.project.nutriai.databinding.ActivityFavoriteMealBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.meal_detail.MealDetailsActivity
import com.project.nutriai.ui.search.adapter.MealVerticalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMealActivity : BaseActivity<ActivityFavoriteMealBinding, FavoriteMealViewModel>() {

    override val viewModel: FavoriteMealViewModel by viewModels()
    private val adapter by lazy {
        MealVerticalAdapter {
            val intent = MealDetailsActivity.getIntent(this, it.id)
            startActivity(intent)
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityFavoriteMealBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvMeals.adapter = adapter
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindViewModel() {
        viewModel.favoriteMeals.collectIn(this) { favoriteMeals ->
            val simpleMeals = favoriteMeals.map {
                Meal(
                    id = it.id,
                    name = it.name,
                    category = it.category,
                    imageUrl = it.imageUrl
                )
            }
            binding.llNoData.isVisible = simpleMeals.isEmpty()
            adapter.submitList(simpleMeals)
        }
    }
}