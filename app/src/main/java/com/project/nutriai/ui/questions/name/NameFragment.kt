package com.project.nutriai.ui.questions.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.nutriai.databinding.FragmentNameBinding
import com.project.nutriai.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : BaseFragment<FragmentNameBinding, NameViewModel>() {

    override val viewModel: NameViewModel by viewModels()
    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNameBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {

    }

}