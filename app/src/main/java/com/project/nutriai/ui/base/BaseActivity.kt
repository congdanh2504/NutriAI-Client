package com.project.nutriai.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.project.nutriai.R
import com.shashank.sony.fancytoastlib.FancyToast

abstract class BaseActivity<ViewBindingType : ViewBinding, ViewModelType : BaseViewModel> :
    AppCompatActivity(),
    ViewBindingHolder<ViewBindingType> by ViewBindingHolderImpl() {

    protected abstract val viewModel: ViewModelType

    protected val binding: ViewBindingType
        get() = requireBinding()

    private lateinit var loadingDialog: Dialog

    abstract fun setupViewBinding(inflater: LayoutInflater): ViewBindingType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            initBinding(
                binding = setupViewBinding(layoutInflater),
                lifecycle = lifecycle,
                className = this::class.simpleName,
                onBound = null
            )
        )
        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

    fun showSuccessMessage(message: String) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
    }

    fun showErrorMessage(message: String) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
    }

    fun showLoadingDialog() {
        loadingDialog = Dialog(this)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setContentView(R.layout.loading_dialog)
        loadingDialog.setCancelable(false)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val loadingText = loadingDialog.findViewById<TextView>(R.id.loadingText)
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        loadingText.startAnimation(pulseAnimation)
        loadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (::loadingDialog.isInitialized && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}
