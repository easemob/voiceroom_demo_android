package io.agora.buddy.tool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.text.StaticLayout
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import io.agora.buddy.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

/**
 * @author create by zhangwei03
 */
class CustomToast private constructor() : PopupWindow(
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
) {
    private var mActivity: WeakReference<Activity>? = null
    private var mView: WeakReference<View>? = null
    private var mImage: WeakReference<ImageView>? = null
    private var mText: WeakReference<TextView>? = null
    private var textQueue: Queue<ArrayList<Any>> = LinkedBlockingQueue()

    // 最大等待显示数目
    private val maxWaitShowNumber = 10

    // 左右内边距之和
    private var paddingWidth = 12

    companion object {
        // 普通类型
        const val Toast_Common = 0

        // 提示类型
        const val Toast_Tips = 1

        // 错误类型
        const val Toast_Error = 2

        fun makeText(
            context: Context,
            text: String,
            type: Int = Toast_Common,
            showLength: Int = Toast.LENGTH_SHORT
        ): CustomToast {
            val instance = Inner.instance
            if (instance.maxWaitShowNumber > instance.textQueue.size) {
                instance.textQueue.offer(arrayListOf(text, showLength * 2 + 1))
            }
            val activity = context as Activity
            if (!Objects.equals(instance.mActivity?.get(), activity)) {
                instance.mActivity = WeakReference(activity)
                instance.initView()
            }
            return instance
        }
    }

    private object Inner {
        var instance: CustomToast = CustomToast()
    }

    @SuppressLint("InflateParams")
    private fun initView() {
        mActivity?.get()?.let {
            val view = LayoutInflater.from(it).inflate(R.layout.view_toast_custom, null, false)
            mView = WeakReference(view)
            mImage = WeakReference(view.findViewById(R.id.ivToast))
            mText = WeakReference(view.findViewById(R.id.tvContent))
            paddingWidth = view.paddingStart + view.paddingEnd

            contentView = view
            // 不设置焦点
            isFocusable = false
            // 点击后退键pop消失
            isTouchable = false
            setBackgroundDrawable(ColorDrawable(0x00000000))
        }
    }

    fun show() {
        if (!isShowing) {
            textQueue.poll()?.let { item ->
                mActivity?.get()?.let {
                    val nText = item[0] as String
                    val showLength = item[1] as Int
                    mText?.get()?.let { textView ->
                        textView.text = nText
                        // 界面宽高
                        val heightPixels = ScreenTools.getScreenHeight(it)
                        val widthPixels = ScreenTools.getScreenWidth(it)
                        // 内容宽度+布局宽度
                        val dw = StaticLayout.getDesiredWidth(nText, textView.paint)
                        val cmpWidth = dw.toInt() + paddingWidth
                        width = if (cmpWidth > widthPixels) widthPixels - paddingWidth else cmpWidth

                        val windowManager = it.getSystemService<WindowManager>(WindowManager::class.java)
                        showAtLocation(it.window.decorView, Gravity.CENTER, 0, heightPixels / 5)
                        CoroutineUtil.execIO {
                            delay(showLength * 1000L)
                            withContext(Dispatchers.Main) {
                                hide()
                                if (textQueue.size > 0) {
                                    if (!it.isFinishing) show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hide() {
        if (isShowing) {
            dismiss()
        }
    }
}