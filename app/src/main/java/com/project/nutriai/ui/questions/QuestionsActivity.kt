package com.project.nutriai.ui.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.project.nutriai.databinding.ActivityQuestionsBinding
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsActivity : BaseActivity<ActivityQuestionsBinding, QuestionsViewModel>() {

    override val viewModel: QuestionsViewModel by viewModels()
    private val questionPageAdapter by lazy { QuestionPageAdapter(this) }
    private var rvPos = 0
        set(value) {
            field = value
            selectPage(value)
        }

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityQuestionsBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListener()
    }

    private fun initView() {
        binding.vpPages.adapter = questionPageAdapter
        binding.vpPages.isUserInputEnabled = false
        binding.vpPages.offscreenPageLimit = questionPageAdapter.itemCount
    }

    private fun initListener() {
        binding.vpPages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                rvPos = position
            }
        })
        binding.cvBack.setOnClickListener {
            if (rvPos > 0) {
                rvPos--
            }
        }
        binding.cvNext.setOnClickListener {
            if (rvPos < questionPageAdapter.itemCount - 1) {
                rvPos++
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectPage(position: Int) {
        binding.tvQuestion.text = getString(viewModel.questions[position])
        binding.tvNumber.text = "${position + 1}/${questionPageAdapter.itemCount}"
        binding.vpPages.setCurrentItem(position, true)
    }

}
