package io.agora.baseui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.agora.buddy.tool.ResourcesTools
import io.agora.buddy.tool.ScreenTools

/**
 * @author create by zhangwei03
 */
abstract class BaseFixedHeightSheetDialog<B : ViewBinding?> : BaseSheetDialog<B>() {

    private val heightRadio = 0.7

    private var dpiRatio: Float = 1.0f

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val heightPixels = ScreenTools.getScreenHeight(requireActivity())
        val widthPixels = ScreenTools.getScreenWidth(requireActivity())
        dpiRatio = heightPixels * 1.0f / widthPixels
        return FixedHeightSheetDialog(requireContext(), theme, (heightPixels * heightRadio).toInt(), dpiRatio)
    }
}

internal class FixedHeightSheetDialog constructor(
    context: Context,
    @StyleRes theme: Int,
    private val fixedHeight: Int,
    private val dpiRatio: Float,
) : BottomSheetDialog(context, theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPeekHeight()
        setMaxHeight(fixedHeight)
    }

    override fun onStart() {
        super.onStart()
    }

    private fun setPeekHeight() {
        if (dpiRatio > 1.78) return
//        getBottomSheetBehavior()?.peekHeight = peekHeight
    }

    private fun setMaxHeight(maxHeight: Int) {
        if (maxHeight <= 0) return
        window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight)
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun getBottomSheetBehavior(): BottomSheetBehavior<View>? {
        val view: View? = window?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        return view?.let { BottomSheetBehavior.from(view) }
    }
}