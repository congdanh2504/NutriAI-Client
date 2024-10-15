package com.project.nutriai.ui.questions.gender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentGenderBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenderFragment : BaseFragment<FragmentGenderBinding, GenderViewModel>() {

    override val viewModel: GenderViewModel by viewModels()
    private val genderAdapter by lazy {
        GenderAdapter {
            viewModel.setGenderSelected(it)
        }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGenderBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        bindViewModel()
    }

    private fun initView() {
        binding.rvGender.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = genderAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.setGenderSelected(R.string.male)
        viewModel.genderList.collectInViewLifecycle(this) {
            genderAdapter.submitList(it.toMutableList())
        }
    }
}
