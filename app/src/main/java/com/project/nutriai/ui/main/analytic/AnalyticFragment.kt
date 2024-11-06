package com.project.nutriai.ui.main.analytic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.nutriai.databinding.FragmentAnalyticBinding
import com.project.nutriai.ui.base.BaseFragment

class AnalyticFragment : BaseFragment<FragmentAnalyticBinding, AnalyticViewModel>() {
    override val viewModel: AnalyticViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticBinding {
        return FragmentAnalyticBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}