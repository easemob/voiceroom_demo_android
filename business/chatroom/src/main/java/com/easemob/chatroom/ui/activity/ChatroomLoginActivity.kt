package com.easemob.chatroom.ui.activity

import android.graphics.Color
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
import com.easemob.baseui.BaseUiActivity
import com.easemob.baseui.BaseUiTool
import com.easemob.baseui.general.callback.OnResourceParseCallback
import com.easemob.buddy.tool.CountDownTimerUtils
import com.easemob.buddy.tool.ToastTools
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ActivityLoginBinding
import com.easemob.chatroom.model.LoginViewModel
import tools.bean.VRUserBean

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
            parseResource(response,object : OnResourceParseCallback<String>(true){
                override fun onSuccess(data: String?) {
                    code = data
                    timer.start()
                }
            })
        }
        loginViewModel.loginObservable.observe(this){ response ->
            parseResource(response,object :OnResourceParseCallback<VRUserBean>(true){
                override fun onSuccess(data: VRUserBean?) {

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
        if (TextUtils.isEmpty(phoneNumber)){
            ToastTools.show(this,"")
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
        code = binding.etLoginPwd.text.toString().trim { it <= ' ' }
        binding.btLogin.isEnabled = !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(code) && isReady
    }

    private fun checkLogin(): Boolean {
        if (TextUtils.isEmpty(phoneNumber)){
            ToastTools.show(this,"")
            return false
        }
        if (TextUtils.isEmpty(code) || code?.length!! < 3){
            ToastTools.show(this,"")
            return false
        }
        if (!isReady){
            ToastTools.show(this,"")
            return false
        }
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
        val spanStr = SpannableString(getString(R.string.chatroom_login_agreement))
        //设置颜色
        spanStr.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_009FFF)),
            5, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //设置下划线
        spanStr.setSpan(UnderlineSpan(), 5, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanStr.setSpan(object :
            MyClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, 5, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //设置颜色
        spanStr.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_009FFF)),
            14, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //设置下划线
        spanStr.setSpan(UnderlineSpan(), 14, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanStr.setSpan(object :
            MyClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, 14, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanStr
    }

    private abstract class MyClickableSpan : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.bgColor = Color.TRANSPARENT
        }
    }

}