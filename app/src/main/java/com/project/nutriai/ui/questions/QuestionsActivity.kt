package com.project.nutriai.ui.questions

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.project.nutriai.databinding.ActivityQuestionsBinding
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsActivity : BaseActivity<ActivityQuestionsBinding, QuestionsViewModel>() {

    override val viewModel: QuestionsViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityQuestionsBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {

    }

}
