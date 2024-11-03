package com.project.nutriai.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.domain.model.Gender
import com.project.nutriai.R
import com.project.nutriai.databinding.FragmentHomeBinding
import com.project.nutriai.extensions.flow.collectInViewLifecycle
import com.project.nutriai.extensions.startActivity
import com.project.nutriai.ui.base.BaseFragment
import com.project.nutriai.ui.main.home.adapter.CategoryAdapter
import com.project.nutriai.ui.profile.ProfileActivity

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
        binding.rvCategories.adapter = CategoryAdapter(viewModel.category)
    }

    private fun initListeners() {
        binding.tvViewProfile.setOnClickListener {
            startActivity<ProfileActivity>()
        }
    }

    private fun bindViewModel() {
        viewModel.profile.collectInViewLifecycle(this) { profile ->
            binding.tvHiUser.text = String.format(getString(R.string.hi_s), profile.fullName)
            binding.tvProfile.text = profile.fullName
            binding.ivProfile.setImageResource(if (profile.gender == Gender.MALE) R.drawable.man else R.drawable.woman)
        }
    }
}