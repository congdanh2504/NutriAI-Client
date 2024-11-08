package com.project.nutriai.ui.questions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.project.nutriai.databinding.ActivityQuestionsBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.main.MainActivity
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class QuestionsActivity : BaseActivity<ActivityQuestionsBinding, QuestionsViewModel>() {

    override val viewModel: QuestionsViewModel by viewModels()
    private val questionPageAdapter by lazy { QuestionPageAdapter(this) }
    private val isEditMode by lazy { intent.getBooleanExtra(IS_EDIT_MODE, false) }
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
        bindViewModel()
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
            } else {
                viewModel.updateUserDetail()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectPage(position: Int) {
        binding.tvQuestion.text = getString(viewModel.questions[position])
        binding.tvNumber.text = "${position + 1}/${questionPageAdapter.itemCount}"
        binding.vpPages.setCurrentItem(position, true)
    }

    private fun bindViewModel() {
        viewModel.updateStatus.filterNotNull().collectIn(this) {
            if (it) {
                if (!isEditMode) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            } else {
                showErrorMessage("Failed to update user detail")
            }
        }
    }

    companion object {
        const val IS_EDIT_MODE = "is_edit_mode"
    }
}
