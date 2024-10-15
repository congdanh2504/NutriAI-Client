package com.project.nutriai.ui.questions.gender

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemGenderBinding
import java.util.concurrent.Executors

object GenderItemCallback : DiffUtil.ItemCallback<Gender>() {
    override fun areItemsTheSame(oldItem: Gender, newItem: Gender): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: Gender, newItem: Gender): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Gender, newItem: Gender): Any? {
        return if (oldItem.selected != newItem.selected) true else null
    }

}

class GenderAdapter(
    val clickListener: (Int) -> Unit
) : ListAdapter<Gender, GenderAdapter.VH>(
    AsyncDifferConfig.Builder(GenderItemCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {
    class VH(val binding: ItemGenderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemGenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.tvGender.text = binding.tvGender.context.getString(type)
                binding.ivGender.setImageResource(img)
                binding.cardViewGender.setOnClickListener {
                    clickListener(this.type)
                }
                if (selected) {
                    binding.cardViewGender.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardViewGender.context, R.color.cardCheck
                        )
                    )
                    binding.tvGender.setTextColor(
                        ContextCompat.getColor(
                            binding.tvGender.context, R.color.white
                        )
                    )
                    binding.ivCheck.visibility = View.VISIBLE
                } else {
                    binding.cardViewGender.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardViewGender.context, R.color.cardNoCheck
                        )
                    )
                    binding.tvGender.setTextColor(
                        ContextCompat.getColor(
                            binding.tvGender.context, R.color.primaryColor
                        )
                    )
                    binding.ivCheck.visibility = View.INVISIBLE
                }
            }
        }
    }
}