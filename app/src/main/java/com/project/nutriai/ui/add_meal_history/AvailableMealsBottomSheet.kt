package com.project.nutriai.ui.add_meal_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.domain.model.Meal
import com.project.nutriai.databinding.BottomSheetAvailableMealsBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.extensions.hideKeyboard
import com.project.nutriai.ui.search.adapter.MealVerticalAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.R

@AndroidEntryPoint
class AvailableMealsBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: AddMealHistoryViewModel by activityViewModels()
    private var _binding: BottomSheetAvailableMealsBinding? = null
    private val binding get() = _binding!!
    private var onMealSelected: ((Meal) -> Unit)? = null

    private val mealAdapter by lazy {
        MealVerticalAdapter {
            onMealSelected?.invoke(it)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAvailableMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMeals.adapter = mealAdapter

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString()
                hideKeyboard()
                if (query.isEmpty()) return@setOnEditorActionListener true
                viewModel.searchMeals(query)
            }
            true
        }

        viewModel.searchResult.collectInViewLifecycle(this) { searchState ->
            binding.progressBar.isVisible = searchState.isLoading
            if (!searchState.isLoading) {
                mealAdapter.submitList(searchState.searchResult)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet =
                dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    fun setOnMealSelectedListener(listener: (Meal) -> Unit) {
        onMealSelected = listener
    }

    private fun hideKeyboard() {
        requireActivity().hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AvailableMealsBottomSheet"
    }
}