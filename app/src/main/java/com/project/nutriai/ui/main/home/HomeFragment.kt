package com.project.nutriai.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.project.domain.model.Gender
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentHomeBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.extensions.hideKeyboard
import com.project.nutriai.extensions.startActivity
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.main.home.adapter.CategoryAdapter
import com.project.nutriai.ui.main.home.adapter.MealAdapter
import com.project.nutriai.ui.profile.ProfileActivity
import com.project.nutriai.ui.search.SearchActivity
import com.project.nutriai.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListeners()
        bindViewModel()
    }

    private fun initView() {
        binding.rvCategories.adapter = CategoryAdapter(AppUtils.category) {
            val intent = Intent(requireContext(), SearchActivity::class.java).apply {
                putExtra(SearchActivity.CATEGORY_KEY, it.type)
            }
            startActivity(intent)
        }
    }

    private fun initListeners() {
        binding.tvViewProfile.setOnClickListener {
            startActivity<ProfileActivity>()
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString()
                if (query.isEmpty()) return@setOnEditorActionListener true
                requireActivity().hideKeyboard()
                binding.etSearch.text.clear()
                val intent = Intent(requireContext(), SearchActivity::class.java).apply {
                    putExtra(SearchActivity.QUERY_KEY, query)
                }
                startActivity(intent)
            }
            true
        }
    }

    private fun bindViewModel() {
        viewModel.profile.collectInViewLifecycle(this) { profile ->
            binding.tvHiUser.text = String.format(getString(R.string.hi_s), profile.fullName)
            binding.tvProfile.text = profile.fullName
            binding.ivProfile.setImageResource(if (profile.gender == Gender.MALE) R.drawable.man else R.drawable.woman)
        }
        viewModel.recommendedMeals.collectInViewLifecycle(this) { recommendedMeals ->
            if (recommendedMeals.isNotEmpty()) {
                binding.rvRecommended.adapter = MealAdapter(recommendedMeals)
                binding.rvRecommended.isVisible = true
                binding.shimmerRecommended.isInvisible = true
            }
        }
        viewModel.avoidedMeals.collectInViewLifecycle(this) { avoidedMeals ->
            if (avoidedMeals.isNotEmpty()) {
                binding.rvAvoid.adapter = MealAdapter(avoidedMeals)
                binding.rvAvoid.isVisible = true
                binding.shimmerAvoid.isInvisible = true
            }
        }
    }
}