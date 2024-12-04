package com.project.nutriai.ui.fruit_identifier

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.net.toUri
import coil.load
import coil.request.CachePolicy
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.project.domain.model.FruitNutrition
import com.project.domain.model.FruitNutritionInfo
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityFruitIdentifierBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class FruitIdentifierActivity :
    BaseActivity<ActivityFruitIdentifierBinding, FruitIdentifierViewModel>() {

    override val viewModel: FruitIdentifierViewModel by viewModels()
    private val imageUri by lazy { intent.getStringExtra(IMAGE_URI) ?: "" }

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityFruitIdentifierBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListener()
        bindViewModel()
    }

    private fun initView() {
        val file = if (imageUri.contains("file://")) {
            File(imageUri.toUri().path.toString())
        } else {
            getFileFromUriWithTimestamp(this, imageUri.toUri())
        }
        file?.let {
            viewModel.identifyFruit(file)
        } ?: run {
            showErrorMessage(getString(R.string.error_identifying_fruit))
            finish()
        }
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun bindViewModel() {
        viewModel.state.collectIn(this) { state ->
            if (state.isLoading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
                state.fruitNutrition?.let {
                    setupFruitNutrition(it)
                }
                if (state.error.isNotEmpty()) {
                    showErrorMessage(getString(R.string.error_identifying_fruit))
                    finish()
                }
            }
        }
    }

    private fun getFileFromUriWithTimestamp(context: Context, uri: Uri): File? {
        val timestamp = System.currentTimeMillis()
        val fileName = "image_$timestamp.jpg"

        val file = File(context.cacheDir, fileName)
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return if (file.exists()) file else null
        } catch (e: Exception) {
            return null
        }
    }

    private fun setupFruitNutrition(fruitNutrition: FruitNutrition) {
        binding.tvFruitName.text = fruitNutrition.name
        binding.ivFruit.load(fruitNutrition.imageUrl) {
            placeholder(R.drawable.meal_placeholder)
            error(R.drawable.meal_placeholder)
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
        binding.tvBeneFits.text = fruitNutrition.healthBenefits.joinToString("\n") { "- $it" }
        binding.tvHealthWarnings.text =
            fruitNutrition.healthWarnings.joinToString("\n") { "- $it" }
        setupChart(fruitNutrition.nutritionInfoPer100g)
    }

    private fun setupChart(nutritionInfo: FruitNutritionInfo) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(nutritionInfo.protein.toFloat()))
        entries.add(PieEntry(nutritionInfo.carbs.toFloat()))
        entries.add(PieEntry(nutritionInfo.fat.toFloat()))
        entries.add(PieEntry(nutritionInfo.fiber.toFloat()))
        entries.add(PieEntry(nutritionInfo.sugar.toFloat()))

        binding.tvProtein.text = getString(R.string.protein_ff, nutritionInfo.protein)
        binding.tvCarbohydrates.text =
            getString(R.string.carbohydrates_ff, nutritionInfo.carbs)
        binding.tvFats.text = getString(R.string.fats_ff, nutritionInfo.fat)
        binding.tvFiber.text = getString(R.string.fiber_ff, nutritionInfo.fiber)
        binding.tvSugar.text = getString(R.string.sugar_ff, nutritionInfo.sugar)

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

    override fun onDestroy() {
        super.onDestroy()
        FileUtils.clearCache(this)
    }

    companion object {
        const val IMAGE_URI = "image_uri"
    }
}
