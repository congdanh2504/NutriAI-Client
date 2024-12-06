package com.project.nutriai.ui.chat_with_ai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nutriai.databinding.ActivityChatWithAiBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatWithAiActivity : BaseActivity<ActivityChatWithAiBinding, ChatWithAiViewModel>() {

    override val viewModel: ChatWithAiViewModel by viewModels()
    private val adapter by lazy { ConversationAdapter(Markwon.create(this)) }

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityChatWithAiBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        adjustForKeyboard()
        initListener()
        bindViewModel()
    }

    private fun initView() {
        binding.etMessage.post {
            val height = binding.etMessage.height
            val layoutParams = binding.btnSend.layoutParams
            layoutParams.width = height
            layoutParams.height = height
            binding.btnSend.layoutParams = layoutParams
        }
        binding.rvConversation.layoutManager = LinearLayoutManager(this)
        binding.rvConversation.adapter = adapter
        binding.rvConversation.itemAnimator = null
    }

    private fun adjustForKeyboard() {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
                binding.etMessage.text.clear()
            }
        }
    }

    private fun bindViewModel() {
        viewModel.loading.collectIn(this) { isLoading ->
            binding.btnSend.isEnabled = !isLoading
        }
        viewModel.conversation.collectIn(this) {
            lifecycleScope.launch {
                adapter.submitList(it)
                if (it.isEmpty()) return@launch
                binding.rvConversation.smoothScrollToPosition(it.size - 1)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adjustPanForKeyboard()
    }

    private fun adjustPanForKeyboard() {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}