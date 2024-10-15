package com.project.nutriai.ui.questions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.nutriai.ui.questions.gender.GenderFragment
import com.project.nutriai.ui.questions.name.NameFragment
import com.project.nutriai.ui.questions.weight_height.WeightHeightFragment

class QuestionPageAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        NAME_AGE_INDEX to { NameFragment() },
        GENDER_INDEX to { GenderFragment() },
        WEIGHT_HEIGHT_INDEX to { WeightHeightFragment() }
    )

    override fun getItemCount(): Int = fragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return fragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        const val NAME_AGE_INDEX = 0
        const val GENDER_INDEX = 1
        const val WEIGHT_HEIGHT_INDEX = 2
    }
}