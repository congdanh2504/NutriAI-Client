package com.project.nutriai.ui.questions.allergies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.FragmentAllergyBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.questions.nutri_object.AnswerAdapter
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllergyFragment : BaseFragment<FragmentAllergyBinding, AllergyViewModel>() {

    override val viewModel: AllergyViewModel by viewModels()
    private val adapter = AnswerAdapter {
        viewModel.onAllergySelected(it)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAllergyBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvAllergy.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAllergy.adapter = adapter
        viewModel.onAllergySelected(AppPref.userDetail.foodAllergies)
    }

    private fun bindViewModel() {
        viewModel.allergies.collectInViewLifecycle(this) {
            adapter.submitList(it)
        }
    }
}