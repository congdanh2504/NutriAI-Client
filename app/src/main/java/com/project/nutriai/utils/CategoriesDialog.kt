package com.project.nutriai.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.project.nutriai.R
import com.project.nutriai.databinding.DialogCategoriesBinding

class CategoriesDialog : DialogFragment() {

    private lateinit var binding: DialogCategoriesBinding
    private var onNextClick: (() -> Unit)? = null
    private var recommendedCalories = 0

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(width, height)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val mDismissedField = DialogFragment::class.java.getDeclaredField("mDismissed")
        mDismissedField.isAccessible = true
        mDismissedField.setBoolean(this, false)

        val mShownByMeField = DialogFragment::class.java.getDeclaredField("mShownByMe")
        mShownByMeField.isAccessible = true
        mShownByMeField.setBoolean(this, true)

        manager.beginTransaction()
            .add(this, tag)
            .commitAllowingStateLoss()
    }

    fun show(frgManager: FragmentManager) {
        if (!isAdded) {
            show(frgManager, TAG)
        }
    }

    fun setOnNextClick(onNextClick: (() -> Unit)) {
        this.onNextClick = onNextClick
    }

    fun setRecommendedCalories(recommendedCalories: Int) {
        this.recommendedCalories = recommendedCalories
    }

    private fun safeDismiss() {
        if (isAdded) {
            dismiss()
        }
    }

    private fun initView() {
        binding.tvRecommendedCategories.text = getString(
            R.string.based_on_your_survey_answers_we_recommend_that_you_consume_calories_per_day,
            recommendedCalories
        )

        binding.btnNext.setOnClickListener {
            safeDismiss()
            onNextClick?.invoke()
        }
    }

    companion object {
        private const val TAG = "CategoriesDialog"
    }
}