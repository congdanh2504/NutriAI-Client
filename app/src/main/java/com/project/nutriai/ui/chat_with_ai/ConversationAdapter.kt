package com.project.nutriai.ui.chat_with_ai

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.project.nutriai.R
import com.project.nutriai.databinding.ItemMessageLeftBinding
import com.project.nutriai.databinding.ItemMessageRightBinding
import io.noties.markwon.Markwon

class ConversationAdapter(
    private val markwon: Markwon
) : ListAdapter<Message, ConversationAdapter.ViewHolder>(MessageDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isSentByUser) {
            VIEW_TYPE_RIGHT
        } else {
            VIEW_TYPE_LEFT
        }
    }

    abstract class ViewHolder(
        itemView: ViewBinding
    ) : RecyclerView.ViewHolder(itemView.root) {
        abstract fun bind(message: Message)
    }

    inner class LeftMessageViewHolder(private val binding: ItemMessageLeftBinding) :
        ViewHolder(binding) {

        override fun bind(message: Message) {
            when (message.status) {
                MessageStatus.SENDING -> {
                    binding.tvMessage.isGone = true
                    binding.progressBar.isVisible = true
                }

                MessageStatus.SENT -> {
                    binding.tvMessage.isVisible = true
                    binding.progressBar.isGone = true
                    markwon.setMarkdown(binding.tvMessage, message.content)
                }

                MessageStatus.FAILED -> {
                    binding.tvMessage.text =
                        binding.root.context.getString(R.string.an_error_occurred_while_sending_the_message_please_try_again)
                }
            }
        }
    }

    class RightMessageViewHolder(private val binding: ItemMessageRightBinding) :
        ViewHolder(binding) {

        override fun bind(message: Message) {
            binding.tvMessage.text = message.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_LEFT) {
            val binding = ItemMessageLeftBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            LeftMessageViewHolder(binding)
        } else {
            val binding = ItemMessageRightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            RightMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    companion object {
        const val VIEW_TYPE_LEFT = 1
        const val VIEW_TYPE_RIGHT = 2
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}
