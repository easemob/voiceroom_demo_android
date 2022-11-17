package com.easemob.chatroom.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.easemob.baseui.BaseUiActivity
import com.easemob.buddy.tool.ResourcesTools.isZh
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ActivityDisclaimerLayoutBinding
import com.easemob.secnceui.widget.titlebar.ChatroomTitleBar

class ChatroomDisclaimerActivity : BaseUiActivity<ActivityDisclaimerLayoutBinding>(),
    ChatroomTitleBar.OnBackPressListener {

    override fun getViewBinding(inflater: LayoutInflater): ActivityDisclaimerLayoutBinding? {
        return ActivityDisclaimerLayoutBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.titleBar.setOnBackPressListener(this)
        initView()
        initData()
    }

    private fun initData() {
        val c = getString(R.string.chatroom_disclaimer_content_1)
        val cBuilder = SpannableStringBuilder(c)
        val styleSpan = StyleSpan(Typeface.BOLD)
        if (isZh(this)) {
            cBuilder.setSpan(styleSpan, 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            cBuilder.setSpan(styleSpan, 0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.content.text = cBuilder

        binding.end.movementMethod = LinkMovementMethod.getInstance()
        val e = getString(R.string.chatroom_disclaimer_email)
        val eBuilder = SpannableStringBuilder(e)
        val graySpan = ForegroundColorSpan(
            resources.getColor(R.color.color_156EF3)
        )
        eBuilder.setSpan(graySpan, e.length - 19, e.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        eBuilder.setSpan(UnderlineSpan(), e.length - 19, e.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        eBuilder.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                Log.e("SpannableStringBuilder", "onClick")
                sendEmail()
            }
        }, e.length - 18, e.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.end.text = eBuilder
    }

    private fun initView() {
        setTextStyle(binding.titleBar.title, Typeface.BOLD)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun sendEmail() {
        val email = Intent(Intent.ACTION_SEND)
        //邮件发送类型：无附件，纯文本
        email.type = "plain/text"
        //邮件接收者（数组，可以是多位接收者）
        val emailReciver = arrayOf("support@easemob.com")

        //设置邮件地址
        email.putExtra(Intent.EXTRA_EMAIL, emailReciver)
        //设置邮件标题
        email.putExtra(Intent.EXTRA_SUBJECT, "")
        //设置发送的内容
        email.putExtra(Intent.EXTRA_TEXT, "")
        //调用系统的邮件系统
        startActivity(
            Intent.createChooser(
                email,
                if (isZh(this)) "请选择邮件发送软件" else "Please select the mail sending software"
            )
        )
    }

    override fun onBackPress(view: View?) {
        onBackPressed()
    }
}