package com.easemob.chatroom.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.easemob.baseui.BaseUiActivity
import com.easemob.baseui.BaseUiTool
import com.easemob.baseui.general.callback.OnResourceParseCallback
import com.easemob.buddy.tool.CountDownTimerUtils
import com.easemob.buddy.tool.ResourcesTools
import com.easemob.buddy.tool.ThreadManager
import com.easemob.buddy.tool.ToastTools
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ActivityLoginBinding
import com.easemob.chatroom.general.repositories.ProfileManager
import com.easemob.chatroom.model.LoginViewModel
import com.easemob.config.RouterPath
import com.hyphenate.EMCallBack
import com.hyphenate.util.EMLog
import manager.ChatroomHelper
import tools.bean.VRUserBean
import java.util.regex.Matcher
import java.util.regex.Pattern

@Route(path = RouterPath.ChatroomLoginPath)
class ChatroomLoginActivity : BaseUiActivity<ActivityLoginBinding>(), TextWatcher,
    CompoundButton.OnCheckedChangeListener {

    private lateinit var loginViewModel: LoginViewModel
    private var code: String? = ""
    private var phoneNumber: String? = ""
    private var isReady: Boolean = false
    private lateinit var timer: CountDownTimerUtils

    override fun getViewBinding(inflater: LayoutInflater): ActivityLoginBinding? {
        return ActivityLoginBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
        initView()
        loginViewModel = BaseUiTool.getViewModel(LoginViewModel::class.java, this)
        loginViewModel.verificationCodeObservable.observe(this){ response ->
            parseResource(response,object : OnResourceParseCallback<Boolean>(true){
                override fun onSuccess(data: Boolean?) {
                    timer.start()
                }

                override fun onError(code: Int, message: String?) {
                    message?.let { ToastTools.show(this@ChatroomLoginActivity, it) }
                }
            })
        }
        loginViewModel.loginObservable.observe(this){ response ->
            parseResource(response,object :OnResourceParseCallback<VRUserBean>(true){
                override fun onSuccess(data: VRUserBean?) {
                    ProfileManager.getInstance().profile = data
                    ChatroomHelper.getInstance().login(data?.chat_uid, data?.im_token, object : EMCallBack {
                        override fun onSuccess() {
                            ThreadManager.getInstance().runOnMainThread(Runnable {
                                EMLog.e("Login", "onSuccess: $data.chat_uid  $data.im_token")
                                mJumpPage()
                            })
                        }
                        override fun onError(code: Int, error: String) {
                            EMLog.e("Login", "onError: $code  $error")
                            dismissLoading()
                        }
                    })
                }

                override fun onError(code: Int, message: String?) {
                    message?.let { ToastTools.show(this@ChatroomLoginActivity, it) }
                    dismissLoading()
                }
            })

        }
    }

    private fun initListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
        binding.let {
            it.etLoginName.addTextChangedListener(this)
            it.etLoginPwd.addTextChangedListener(this)
            it.cbSelect.setOnCheckedChangeListener(this)
            it.btLogin.setOnClickListener(View.OnClickListener {
                if (checkLogin()){
                    loginViewModel.loginFromServer(phoneNumber,code)
                }
            })
            it.loginClear.setOnClickListener(View.OnClickListener {
                binding.etLoginName.setText("")
                phoneNumber = ""
            })
            it.getCode.setOnClickListener(View.OnClickListener {
                getCode()
            })
        }
    }

    private fun initView(){
        timer = CountDownTimerUtils(this,binding.getCode, 60000, 1000)
        binding.tvAgreement.text = getSpannable()
        binding.tvAgreement.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun getCode(){
        if (TextUtils.isEmpty(phoneNumber) || !isTelPhoneNumber(phoneNumber)){
            ToastTools.show(this,getString(R.string.chatroom_correct_number))
        }else{
            loginViewModel.postVerificationCode(phoneNumber)
        }

    }

    //检查输入内容
    private fun checkEditContent() {
        phoneNumber = binding.etLoginName.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(phoneNumber)){
            binding.loginClear.visibility = View.GONE
        }else{
            binding.loginClear.visibility = View.VISIBLE
        }
    }

    private fun checkLogin(): Boolean {
        if (TextUtils.isEmpty(phoneNumber) || !isTelPhoneNumber(phoneNumber)){
            ToastTools.show(this,getString(R.string.chatroom_correct_number))
            return false
        }
        code = binding.etLoginPwd.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(code) || code?.length!! < 6){
            ToastTools.show(this,getString(R.string.chatroom_code_error))
            return false
        }
        if (!isReady){
            ToastTools.show(this,getString(R.string.chatroom_login_ready))
            return false
        }
        showLoading(false)
        return true
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        checkEditContent()
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0?.id) {
            R.id.cb_select -> checkbox(p1)
        }
    }

    private fun checkbox(isSelect: Boolean ){
        isReady = isSelect
        checkEditContent()
    }

    private fun getSpannable(): SpannableString? {
        var index1 = 0
        var index2 = 0
        var index3 = 0
        val spanStr = SpannableString(getString(R.string.chatroom_login_agreement))
        EMLog.d("Login ","getSpannable: " + spanStr.length)
        if (ResourcesTools.isZh(this)) {
            index1 = 5
            index2 = 13
            index3 = 14
        } else {
            index1 = 29
            index2 = 46
            index3 = 50
        }
        //设置颜色
        spanStr.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_009FFF)),
            index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //设置下划线
        spanStr.setSpan(UnderlineSpan(), index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanStr.setSpan(object :
            MyClickableSpan() {
            override fun onClick(widget: View) {
                mJumpTerms()
            }
        }, index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //设置颜色
        spanStr.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_009FFF)),
            index3, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //设置下划线
        spanStr.setSpan(UnderlineSpan(), index3, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanStr.setSpan(object :
            MyClickableSpan() {
            override fun onClick(widget: View) {
                mJumpProtocol()
            }
        }, index3, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanStr
    }

    private abstract class MyClickableSpan : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.bgColor = Color.TRANSPARENT
        }
    }

    fun isTelPhoneNumber(value: String?): Boolean {
        if (value != null && value.length == 11) {
            val pattern: Pattern = Pattern.compile("^((1[1-9][0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}\$")
            val matcher: Matcher = pattern.matcher(value)
            return matcher.matches()
        }
        return false
    }

    private fun mJumpPage() {
        ARouter.getInstance().build(RouterPath.ChatroomListPath).navigation()
        dismissLoading()
        finish()
    }

    private fun mJumpProtocol(){
        var protocolUrl = "https://www.easemob.com/protocol "
        val uri = Uri.parse(protocolUrl)
        val it = Intent(Intent.ACTION_VIEW, uri)
        startActivity(it)
    }

    private fun mJumpTerms(){
        var termsUrl = "https://www.easemob.com/terms/im"
        val uri = Uri.parse(termsUrl)
        val it = Intent(Intent.ACTION_VIEW, uri)
        startActivity(it)
    }

}