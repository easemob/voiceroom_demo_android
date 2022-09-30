package io.agora.baseui.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseSheetDialog<B : ViewBinding?> : BottomSheetDialogFragment() {

    var binding: B? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding(inflater, container)
        return this.binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }
        dialog?.setOnShowListener { _: DialogInterface? ->
            (view.parent as ViewGroup).setBackgroundColor(Color.TRANSPARENT)
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onHandleOnBackPressed()
            }
        })
    }

    protected fun setOnApplyWindowInsets(view: View) {
        dialog?.window?.let {
            ViewCompat.setOnApplyWindowInsetsListener(view) { v: View?, insets: WindowInsetsCompat ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                Log.d("setOnApplyWindowInsets", inset.toString())
                view.setPadding(0, 0, 0, inset.bottom + view.paddingBottom)
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B?

    open fun onHandleOnBackPressed() {
        dismiss()
    }
}