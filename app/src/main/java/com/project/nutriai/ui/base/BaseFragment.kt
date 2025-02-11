package com.project.nutriai.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.project.nutriai.R
import com.shashank.sony.fancytoastlib.FancyToast

abstract class BaseFragment<ViewBindingType : ViewBinding, ViewModelType : BaseViewModel> :
    Fragment(),
    ViewBindingHolder<ViewBindingType> by ViewBindingHolderImpl() {

    protected abstract val viewModel: ViewModelType

    private var _mContext: Context? = null

    protected val mContext: Context
        get() = requireNotNull(_mContext)

    protected val binding: ViewBindingType
        get() = requireBinding()

    private lateinit var loadingDialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(
        binding = setupViewBinding(inflater, container),
        lifecycle = viewLifecycleOwner.lifecycle,
        className = this::class.simpleName,
        onBound = null
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    abstract fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ViewBindingType

    abstract fun init(savedInstanceState: Bundle?)

    override fun onDetach() {
        _mContext = null
        super.onDetach()
    }

    fun showSuccessMessage(message: String) {
        FancyToast.makeText(
            requireContext(),
            message,
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        ).show()
    }

    fun showErrorMessage(message: String) {
        FancyToast.makeText(
            requireContext(),
            message,
            FancyToast.LENGTH_LONG,
            FancyToast.ERROR,
            false
        ).show()
    }

    fun showLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setContentView(R.layout.loading_dialog)
        loadingDialog.setCancelable(false)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val loadingText = loadingDialog.findViewById<TextView>(R.id.loadingText)
        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse)
        loadingText.startAnimation(pulseAnimation)
        loadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (::loadingDialog.isInitialized && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}
