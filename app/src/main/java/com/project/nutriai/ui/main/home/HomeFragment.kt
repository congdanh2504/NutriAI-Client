package com.project.nutriai.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.nutriai.databinding.FragmentHomeBinding
import com.project.nutriai.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomViewModel>() {

    override val viewModel: HomViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}