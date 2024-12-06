package com.project.nutriai.ui.main.meal_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.domain.model.Ingredient
import com.project.nutriai.databinding.FragmentShoppingListBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListFragment : BaseFragment<FragmentShoppingListBinding, MainViewModel>() {

    override val viewModel: MainViewModel by activityViewModels()
    private val adapter: IngredientAdapter by lazy {
        IngredientAdapter()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShoppingListBinding {
        return FragmentShoppingListBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvIngredient.adapter = adapter
        binding.rvIngredient.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun bindViewModel() {
        viewModel.state.collectInViewLifecycle(
            this
        ) { state ->
            if (state.isLoading) {
                binding.progressBar.isVisible = true
                binding.rvIngredient.isVisible = false
            } else {
                binding.rvIngredient.isVisible = true
                state.mealsPlan?.let {
                    val ingredients = it.meals.flatMap { meal -> meal.ingredients }
                    val mergedIngredients = mergeIngredients(ingredients)
                    adapter.submitList(mergedIngredients)
                }

                binding.progressBar.isVisible = false
                if (state.error.isNotEmpty()) {
                    showErrorMessage(state.error)
                }
            }
        }
    }

    private fun mergeIngredients(ingredients: List<Ingredient>): List<Ingredient> {
        return ingredients
            .groupBy { it.name to it.unit }
            .map { (key, group) ->
                val totalQuantity = group.sumOf { it.quantity }
                Ingredient(name = key.first, quantity = totalQuantity, unit = key.second)
            }
    }
}