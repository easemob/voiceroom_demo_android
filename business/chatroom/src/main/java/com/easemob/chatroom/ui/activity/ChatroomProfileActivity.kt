package com.easemob.chatroom.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.easemob.baseui.BaseUiActivity
import com.easemob.baseui.popupwindow.CommonPopupWindow.ViewDataBindingBuilder
import com.easemob.baseui.popupwindow.CommonPopupWindow.ViewEvent
import com.easemob.buddy.tool.ThreadManager
import com.easemob.buddy.tool.ToastTools.show
import com.easemob.chatroom.R
import com.easemob.chatroom.bean.ProfileBean
import com.easemob.chatroom.databinding.ChatroomProfileAvatarBinding
import com.easemob.chatroom.databinding.ChatroomProfileLayoutBinding
import com.easemob.chatroom.general.net.ChatroomHttpManager
import com.easemob.chatroom.general.repositories.ProfileManager
import com.easemob.chatroom.ui.adapter.ChatroomProfileGridAdapter
import com.easemob.config.RouterPath
import com.easemob.secnceui.utils.DeviceUtils
import com.easemob.secnceui.widget.titlebar.ChatroomTitleBar
import com.hyphenate.chat.EMClient
import com.hyphenate.util.EMLog
import org.json.JSONException
import tools.ValueCallBack
import tools.bean.VRUserBean

@Route(path = RouterPath.ChatroomProfilePath)
class ChatroomProfileActivity : BaseUiActivity<ChatroomProfileLayoutBinding>(),
    View.OnClickListener, ChatroomTitleBar.OnBackPressListener, TextView.OnEditorActionListener,
    View.OnTouchListener {

    private var oldNick: String? = null
    private var nick: String? = null

    override fun getViewBinding(inflater: LayoutInflater): ChatroomProfileLayoutBinding? {
        return ChatroomProfileLayoutBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
        initData()
    }

    private fun initData() {
        val bean = ProfileManager.getInstance().profile
        if (bean != null) {
            val mProfileName = ProfileManager.getInstance().profile.portrait
            val name = ProfileManager.getInstance().profile.name
            val mID = ProfileManager.getInstance().profile.uid
            if (!TextUtils.isEmpty(mProfileName)) {
                val resId = resources.getIdentifier(mProfileName, "drawable", packageName)
                if (resId != 0) {
                    binding.avatar.setImageResource(resId)
                }
            }
            binding.nickName.setText(name)
            oldNick = name
            binding.number.text = "ID: $mID"
        }
    }

    private fun initListener() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
        binding.clRoot.setOnTouchListener(this)
        binding.edit.setOnClickListener(this)
        binding.avatar.setOnClickListener(this)
        binding.titleBar.setOnBackPressListener(this)
        binding.disclaimerLayout.setOnClickListener(this)
        binding.nickName.setOnEditorActionListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.avatar -> {
                showDialog(v)
            }
            R.id.edit -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                binding.contentLayout.isFocusable = false
                binding.contentLayout.isFocusableInTouchMode = false
                binding.nickName.isEnabled = true
                binding.nickName.requestFocus()
                binding.nickName.setSelection( binding.nickName.text.length)
                showKeyboard(binding.nickName)
            }
            R.id.disclaimer_layout -> {
                startActivity(
                    Intent(
                        this@ChatroomProfileActivity,
                        ChatroomDisclaimerActivity::class.java
                    )
                )
            }
        }
    }

    override fun onBackPress(view: View?) {
        onBackPressed()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId and EditorInfo.IME_MASK_ACTION) {
            EditorInfo.IME_ACTION_DONE -> {
                // done stuff
                nick = binding.nickName.getText().toString().trim { it <= ' ' }
                val bean = ProfileManager.getInstance().profile
                bean.name = binding.nickName.getText().toString()
                updateProfile(bean)
            }
            EditorInfo.IME_ACTION_NEXT -> {}
        }
        return false
    }

    private fun showDialog(itemView: View) {
        ViewDataBindingBuilder<ChatroomProfileAvatarBinding>()
            .width(ConstraintLayout.LayoutParams.MATCH_PARENT)
            .height(DeviceUtils.dp2px(this, 535f))
            .outsideTouchable(true)
            .focusable(true)
            .animationStyle(com.easemob.secnceui.R.style.BottomDialogFragment_Animation)
            .clippingEnabled(false)
            .alpha(0.618f)
            .layoutId(this, R.layout.chatroom_profile_avatar)
            .intercept(ViewEvent<ChatroomProfileAvatarBinding> { popupWindow, view ->
                val adapter = ChatroomProfileGridAdapter(this@ChatroomProfileActivity)
                val avatarUrl = ProfileManager.getInstance().profile.portrait
                val index = adapter.getSelectedPosition(avatarUrl)
                adapter.setSelectedPosition(index)
                view.gridView.adapter = adapter
                adapter.SetOnItemClickListener { position, bean ->
                    EMLog.d("SetOnItemClickListener", "OnItemClick$position")
                    val checked = bean.isChecked
                    EMLog.d("SetOnItemClickListener", "OnItemClick$checked")
                    bean.isChecked = !checked
                    if (bean.isChecked) {
                        adapter.setSelectedPosition(position)
                    } else {
                        adapter.setSelectedPosition(-1)
                    }
                    val resId = bean.avatarResource
                    if (resId != 0) {
                        binding.avatar.setImageResource(resId)
                        updateProfile(bean)
                    }
                }
            })
            .build<View>(this)
            .showAtLocation(itemView, Gravity.BOTTOM, 0, 0)
    }

    /**
     * 修改头像
     * @param bean
     */
    private fun updateProfile(bean: ProfileBean) {
        try {
            ChatroomHttpManager.getInstance().loginWithToken(
                EMClient.getInstance().deviceInfo.getString("deviceid"),
                bean.avatarName,
                object : ValueCallBack<VRUserBean?> {
                    override fun onSuccess(var1: VRUserBean?) {
                        hideKey()
                        ProfileManager.getInstance().profile = var1
                        show(
                            this@ChatroomProfileActivity,
                            getString(R.string.chatroom_profile_update_name_suc),
                            Toast.LENGTH_SHORT
                        )
                    }

                    override fun onError(var1: Int, var2: String) {
                        show(
                            this@ChatroomProfileActivity,
                            getString(R.string.chatroom_profile_update_name_fail) + ": " + var2,
                            Toast.LENGTH_SHORT
                        )
                        onFail()
                    }
                })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * 修改昵称
     * @param bean
     */
    private fun updateProfile(bean: VRUserBean) {
        try {
            ChatroomHttpManager.getInstance().loginWithToken(
                EMClient.getInstance().deviceInfo.getString("deviceid"),
                bean.portrait,
                object : ValueCallBack<VRUserBean?> {
                    override fun onSuccess(var1: VRUserBean?) {
                        hideKey()
                        ProfileManager.getInstance().profile = var1
                        oldNick = var1?.name
                        show(
                            this@ChatroomProfileActivity,
                            getString(R.string.chatroom_profile_update_name_suc),
                            Toast.LENGTH_SHORT
                        )
                    }

                    override fun onError(var1: Int, var2: String) {
                        show(
                            this@ChatroomProfileActivity,
                            getString(R.string.chatroom_profile_update_name_fail) + ": " + var2,
                            Toast.LENGTH_SHORT
                        )
                        onFail()
                    }
                })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun onFail() {
        val bean = ProfileManager.getInstance().profile
        bean.name = oldNick
        ProfileManager.getInstance().profile = bean
        binding.nickName.setText(oldNick)
    }

    private fun hideKey() {
        ThreadManager.getInstance().runOnMainThread {
            hideKeyboard()
            binding.nickName.isEnabled = true
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        binding.clRoot.isFocusable = true
        binding.clRoot.isFocusableInTouchMode = true
        binding.clRoot.requestFocus()
        binding.nickName.setText(oldNick)
        hideKeyboard()
        return false
    }
}