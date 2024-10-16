package com.project.nutriai.ui.questions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.nutriai.ui.questions.diet.DietFragment
import com.project.nutriai.ui.questions.gender.GenderFragment
import com.project.nutriai.ui.questions.health.HealthFragment
import com.project.nutriai.ui.questions.lifestyle.LifestyleFragment
import com.project.nutriai.ui.questions.name.NameFragment
import com.project.nutriai.ui.questions.nutri_object.NutriObjectFragment
import com.project.nutriai.ui.questions.weight_height.WeightHeightFragment

class QuestionPageAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        NAME_AGE_INDEX to { NameFragment() },
        GENDER_INDEX to { GenderFragment() },
        WEIGHT_HEIGHT_INDEX to { WeightHeightFragment() },
        NUTRI_OBJECT_INDEX to { NutriObjectFragment() },
        DIET_INDEX to { DietFragment() },
        LIFESTYLE_INDEX to { LifestyleFragment() },
        HEALTH_INDEX to { HealthFragment() }
    )

    override fun getItemCount(): Int = fragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return fragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        const val NAME_AGE_INDEX = 0
        const val GENDER_INDEX = 1
        const val WEIGHT_HEIGHT_INDEX = 2
        const val NUTRI_OBJECT_INDEX = 3
        const val DIET_INDEX = 4
        const val LIFESTYLE_INDEX = 5
        const val HEALTH_INDEX = 6
    }
}