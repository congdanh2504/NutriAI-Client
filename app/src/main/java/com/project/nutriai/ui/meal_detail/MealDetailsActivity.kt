package com.project.nutriai.ui.meal_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import coil.load
import coil.request.CachePolicy
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.project.domain.model.Ingredient
import com.project.domain.model.MealDetails
import com.project.domain.model.NutritionInfo
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityMealDetailsBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon

@AndroidEntryPoint
class MealDetailsActivity : BaseActivity<ActivityMealDetailsBinding, MealDetailsViewModel>() {
    override val viewModel: MealDetailsViewModel by viewModels()
    private val mealId by lazy { intent.getStringExtra(MEAL_ID) }

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityMealDetailsBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        if (mealId == null) {
            finish()
            return
        }
        viewModel.getMealDetailsById(mealId!!)
        initViews()
        initListeners()
        bindViewModel()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivExpand.setOnClickListener {
            viewModel.toggleExpand()
        }
    }

    private fun bindViewModel() {
        viewModel.mealDetails.collectIn(this) { state ->
            if (state.isLoading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
                state.mealDetails?.let {
                    setupMealDetails(it)
                }
                if (state.error.isNotEmpty()) {
                    showErrorMessage(state.error)
                }
            }
        }
        viewModel.isExpand.collectIn(this) { isExpand ->
            binding.ivExpand.setImageResource(
                if (isExpand) R.drawable.ic_sort_up else R.drawable.ic_sort_down
            )
            binding.tvInstructions.isVisible = isExpand
        }
    }

    private fun setupMealDetails(mealDetails: MealDetails) {
        binding.tvMealName.text = mealDetails.name
        binding.ivDish.load(mealDetails.imageUrl) {
            placeholder(R.drawable.meal_placeholder)
            error(R.drawable.meal_placeholder)
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
        setupIngredientsList(mealDetails.ingredients)
        binding.tvInstructions.text = mealDetails.instructions
        val markwon = Markwon.create(this)
        markwon.setMarkdown(binding.tvInstructions, mealDetails.instructions.replace("##", "###"))
        setupChart(mealDetails.nutritionInfo)
    }

    private fun setupIngredientsList(ingredients: List<Ingredient>) {
        val ingredientsList = ingredients.joinToString("\n") {
            "${it.name} - ${it.quantity} ${it.unit}"
        }
        binding.tvIngredientsList.text = ingredientsList
    }

    private fun setupChart(nutritionInfo: NutritionInfo) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(nutritionInfo.protein.toFloat()))
        entries.add(PieEntry(nutritionInfo.carbohydrates.toFloat()))
        entries.add(PieEntry(nutritionInfo.fats.toFloat()))
        entries.add(PieEntry(nutritionInfo.fiber.toFloat()))
        entries.add(PieEntry(nutritionInfo.sugar.toFloat()))

        binding.tvProtein.text = getString(R.string.protein_g, nutritionInfo.protein)
        binding.tvCarbohydrates.text =
            getString(R.string.carbohydrates_g, nutritionInfo.carbohydrates)
        binding.tvFats.text = getString(R.string.fats_g, nutritionInfo.fats)
        binding.tvFiber.text = getString(R.string.fiber_g, nutritionInfo.fiber)
        binding.tvSugar.text = getString(R.string.sugar_g, nutritionInfo.sugar)

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawValues(false)
        dataSet.colors = listOf(
            getColor(R.color.protein),
            getColor(R.color.carbohydrates),
            getColor(R.color.fats),
            getColor(R.color.fiber),
            getColor(R.color.sugar)
        )
        dataSet.valueTextSize = 16f

        val data = PieData(dataSet)
        binding.pieChart.setDrawEntryLabels(false)
        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(android.R.color.transparent)
        binding.pieChart.centerText = "${nutritionInfo.calories} kcal"
        binding.pieChart.setCenterTextSize(18f)

        val legend = binding.pieChart.legend
        legend.isEnabled = false

        binding.pieChart.invalidate()
    }

    companion object {
        private const val MEAL_ID = "meal_id"

        fun getIntent(context: Context, mealId: String) =
            Intent(context, MealDetailsActivity::class.java).apply {
                putExtra(MEAL_ID, mealId)
            }
    }
}