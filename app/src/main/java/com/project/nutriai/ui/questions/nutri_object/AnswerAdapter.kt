package com.project.nutriai.ui.questions.nutri_object

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemAnswerBinding

class AnswerAdapter(
    private val onAnswerSelected: (Answer) -> Unit
) : ListAdapter<Answer, AnswerAdapter.AnswerViewHolder>(NutriObjectDiffUtil()) {

    class NutriObjectDiffUtil : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem == newItem
        }
    }

    inner class AnswerViewHolder(private val binding: ItemAnswerBinding) :
        ViewHolder(binding.root) {

        fun bind(item: Answer) {
            binding.apply {
                tvAnswer.text = root.context.getString(item.name)
                ivCheck.isVisible = item.isSelected
                val textColor = if (item.isSelected) {
                    R.color.white
                } else {
                    R.color.darkGray
                }
                val backgroundColor = if (item.isSelected) {
                    R.color.primaryColor
                } else {
                    R.color.lightGray
                }

                tvAnswer.setTextColor(root.context.getColor(textColor))
                root.setCardBackgroundColor(root.context.getColor(backgroundColor))

                root.setOnClickListener {
                    onAnswerSelected(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
