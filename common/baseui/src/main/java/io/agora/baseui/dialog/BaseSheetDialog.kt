package io.agora.baseui.dialog

import android.R
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseSheetDialog<B : ViewBinding?> : BottomSheetDialogFragment() {

    var binding: B? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding(inflater, container)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onHandleOnBackPressed()
            }
        })
        return this.binding?.root
    }

    override fun onStart() {
        super.onStart()
        var bottomSheetBehavior = BottomSheetBehavior.from(view?.parent as View) //dialog的高度
        bottomSheetBehavior.isHideable = false
    }

    protected fun setOnApplyWindowInsets(view: View) {
//        dialog?.window?.let {
//            ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { v: View?, insets: WindowInsetsCompat ->
//                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                Log.d("setOnApplyWindowInsets", inset.toString())
//                view.setPadding(0, 0, 0, inset.bottom)
//                WindowInsetsCompat.CONSUMED
//            }
//        }
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