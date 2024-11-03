package com.project.nutriai.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.project.domain.model.Category
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivitySearchBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.extensions.hideKeyboard
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.search.adapter.MealVerticalAdapter
import com.project.nutriai.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModels()
    private var category: Category? = null
    private var query: String? = null
    private val mealAdapter by lazy {
        MealVerticalAdapter {
            //startActivity<MealDetailActivity>()
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListeners()
        bindViewModel()
    }

    private fun initView() {
        val categories = AppUtils.category.map { getString(it.name) }.toMutableList()
        categories.add(0, getString(R.string.all))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategories.adapter = adapter
        binding.spCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedCategory =
                    if (position == 0) null else AppUtils.category[position - 1].type
                category = selectedCategory
                viewModel.searchMeals(category, query)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.rvMeals.adapter = mealAdapter

        category = intent.getSerializableExtra(CATEGORY_KEY) as Category?
        query = intent.getStringExtra(QUERY_KEY)
        if (!query.isNullOrBlank()) {
            binding.etSearch.setText(query)
        }

        binding.spCategories.setSelection(category?.ordinal?.plus(1) ?: 0)

        viewModel.searchMeals(category, query)
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString()
                hideKeyboard()
                if (query.isEmpty()) return@setOnEditorActionListener true
                this@SearchActivity.query = query.trim()
                viewModel.searchMeals(category, this@SearchActivity.query)
            }
            true
        }
    }

    private fun bindViewModel() {
        viewModel.searchResult.collectIn(this) { searchState ->
            if (searchState.isLoading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
                binding.llNoData.isVisible = searchState.searchResult.isEmpty()
                binding.rvMeals.isVisible = searchState.searchResult.isNotEmpty()
                mealAdapter.submitList(searchState.searchResult)
            }
        }
    }

    companion object {
        const val CATEGORY_KEY = "category"
        const val QUERY_KEY = "query"
    }
}