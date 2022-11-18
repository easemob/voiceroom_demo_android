package com.easemob.chatroom.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.easemob.baseui.BaseUiActivity
import com.easemob.baseui.general.callback.OnResourceParseCallback
import com.easemob.baseui.general.net.Resource
import com.easemob.buddy.tool.ToastTools.show
import com.easemob.chatroom.R
import com.easemob.chatroom.bean.PageBean
import com.easemob.chatroom.databinding.ChatroomCreateLayoutBinding
import com.easemob.chatroom.general.repositories.PageRepository
import com.easemob.chatroom.model.ChatroomViewModel
import com.easemob.config.RouterParams
import com.easemob.config.RouterPath
import com.easemob.secnceui.utils.DeviceUtils
import com.easemob.secnceui.widget.encryption.ChatroomEncryptionInputView
import com.easemob.secnceui.widget.titlebar.ChatroomTitleBar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.hyphenate.util.EMLog
import tools.bean.VRoomInfoBean
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.roundToInt


@Route(path = RouterPath.ChatroomCreatePath)
class ChatroomCreateActivity : BaseUiActivity<ChatroomCreateLayoutBinding>(),
    ChatroomEncryptionInputView.OnTextChangeListener, RadioGroup.OnCheckedChangeListener,
    ChatroomTitleBar.OnBackPressListener, View.OnClickListener {

    private var chatroomViewModel: ChatroomViewModel? = null
    private var data: ArrayList<PageBean>? = null
    private var roomType = 0
    private var isPublic = true
    private var encryption: String? = null
    private var roomName: String? = null

    override fun getViewBinding(inflater: LayoutInflater): ChatroomCreateLayoutBinding? {
        return ChatroomCreateLayoutBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initListener() {
        binding.edPwd.setOnTextChangeListener(this)
        binding.radioGroupGender.setOnCheckedChangeListener(this)
        binding.titleBar.setOnBackPressListener(this)
        binding.bottomNext.setOnClickListener(this)
        binding.randomLayout.setOnClickListener(this)

        binding.agoraTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.customView != null) {
                    val title = tab.customView!!.findViewById<TextView>(R.id.tab_item_title)
                    val layoutParams = title.layoutParams
                    layoutParams.height = DeviceUtils.dp2px(this@ChatroomCreateActivity, 26f).toInt()
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    title.gravity = Gravity.CENTER
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab.customView != null) {
                    val title = tab.customView!!.findViewById<TextView>(R.id.tab_item_title)
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.vpFragment.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                roomType = data!![position].room_type
                if (roomType == 0) {
                    binding.bottomNext.text = getString(R.string.chatroom_create_next)
                } else {
                    binding.bottomNext.text = getString(R.string.chatroom_create_go_live)
                }
                binding.edRoomName.setText(randomName())
            }
        })

        binding.clRoot.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })

    }

    private fun initData() {
        data = PageRepository.getInstance().getDefaultPageData(this)
        chatroomViewModel = ViewModelProvider(this)[ChatroomViewModel::class.java]
        chatroomViewModel?.createObservable?.observe(
            this
        ) { response: Resource<VRoomInfoBean?>? ->
            parseResource(
                response,
                object : OnResourceParseCallback<VRoomInfoBean?>() {
                    override fun onSuccess(data: VRoomInfoBean?) {
                        if (null != data && null != data.room) {
                            joinRoom(data)
                        }
                    }

                    override fun onError(code: Int, message: String) {
                        dismissLoading()
                        EMLog.d("Create Room onError", "\$code  \$message")
                    }
                })
        }
        setupWithViewPager()
    }

    private fun initView() {
        chickPrivate()
        binding.edRoomName.filters = arrayOf<InputFilter>(EmojiInputFilter(32))
        setTextStyle(binding.titleBar.title, Typeface.BOLD)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onTextChange(pwd: String?) {
        if (pwd?.length!! >= 4) {
            hideKeyboard()
        }
    }

    fun createSpatialRoom() {
        if (isPublic) {
            chatroomViewModel!!.createSpatial(this, roomName, false)
        } else {
            chatroomViewModel!!.createSpatial(this, roomName, true, encryption)
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, checkedId: Int) {
        if (checkedId == R.id.radioButton_private) {
            isPublic = false
        } else if (checkedId == R.id.radioButton_public) {
            isPublic = true
        }
        chickPrivate()
    }

    override fun onBackPress(view: View?) {
        onBackPressed()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.bottom_next) {
            if (roomType == 1) {
                binding.bottomNext.isEnabled = false
                showLoading(false)
            }
            encryption = binding.edPwd.text.toString().trim { it <= ' ' }
            roomName = binding.edRoomName.text.toString().trim { it <= ' ' }
            check()
        } else if (v?.id == R.id.random_layout) {
            binding.edRoomName.setText(randomName())
        }
    }

    private fun check() {
        if (TextUtils.isEmpty(roomName)) {
            show(this, getString(R.string.chatroom_create_empty_name), Toast.LENGTH_LONG)
            binding.bottomNext.isEnabled = true
            dismissLoading()
            return
        }
        if (!isPublic && encryption!!.length != 4) {
            binding.inputTip.visibility = View.VISIBLE
            show(this, getString(R.string.chatroom_room_create_tips), Toast.LENGTH_LONG)
            binding.bottomNext.isEnabled = true
            dismissLoading()
            return
        }
        binding.inputTip.visibility = View.GONE
        if (roomType == 0) {
            val intent = Intent(
                this@ChatroomCreateActivity,
                ChatroomSoundSelectionActivity::class.java
            )
            intent.putExtra(RouterParams.KEY_CHATROOM_CREATE_NAME, roomName)
            intent.putExtra(RouterParams.KEY_CHATROOM_CREATE_IS_PUBLIC, isPublic)
            if (!isPublic) {
                intent.putExtra(RouterParams.KEY_CHATROOM_CREATE_ENCRYPTION, encryption)
            }
            intent.putExtra(RouterParams.KEY_CHATROOM_CREATE_ROOM_TYPE, roomType)
            startActivity(intent)
        } else if (roomType == 1) {
            createSpatialRoom()
        }
    }

    fun randomName(): String? {
        var roomName = ""
        val m = SimpleDateFormat("MM") //获取月份
        val d = SimpleDateFormat("dd") //获取分钟
        val month = m.format(Date())
        val day = d.format(Date())
        roomName = if (roomType == 0) {
            getString(R.string.chatroom_create_chat_room_tag) + "-" + month + day + "-" + (Math.random() * 999 + 1).roundToInt()
        } else {
            getString(R.string.chatroom_create_chat_3d_room) + "-" + month + day + "-" + (Math.random() * 999 + 1).roundToInt()
        }
        return roomName
    }

    fun joinRoom(data: VRoomInfoBean?) {
        binding.bottomNext.isEnabled = true
        dismissLoading()
        Log.e("ChatroomCreateActivity", "joinChatRoom onSuccess")
        ARouter.getInstance()
            .build(RouterPath.ChatroomPath)
            .withSerializable(RouterParams.KEY_CHATROOM_DETAILS_INFO, data)
            .navigation()
        finish()
    }

    private fun chickPrivate() {
        if (isPublic) {
            binding.edPwd.visibility = View.GONE
            binding.clRoot.requestFocus()
            hideKeyboard()
            binding.inputTip.visibility = View.GONE
            binding.tipsLayout.visibility = View.VISIBLE
        } else {
            binding.edPwd.visibility = View.VISIBLE
            binding.edPwd.isFocusable = true
            binding.edPwd.isFocusableInTouchMode = true
            binding.edPwd.requestFocus()
            showKeyboard(binding.edPwd)
            binding.tipsLayout.visibility = View.GONE
        }
    }

    private fun setupWithViewPager() {
        binding.vpFragment.offscreenPageLimit = 1
        val recyclerView: View = binding.vpFragment.getChildAt(0)
        if (recyclerView is RecyclerView) {
            recyclerView.setPadding(
                DeviceUtils.dp2px(this, 30f),
                0,
                DeviceUtils.dp2px(this, 30f),
                0
            )
            recyclerView.clipToPadding = false
        }
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(DeviceUtils.dp2px(this, 16f)))
        binding.vpFragment.setPageTransformer(compositePageTransformer)
        // set adapter
        binding.vpFragment.adapter =
            object : RecyclerView.Adapter<ChatroomCreateActivity.ViewHolder>() {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ChatroomCreateActivity.ViewHolder {
                    val view = LayoutInflater.from(this@ChatroomCreateActivity)
                        .inflate(R.layout.chatroom_create_page_item_layout, parent, false)
                    return ChatroomCreateActivity.ViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: ChatroomCreateActivity.ViewHolder,
                    position: Int
                ) {
                    if (data!![position].room_type == 0) {
    //                  holder.mLayout.setBackgroundResource(R.drawable.icon_create_chat_room);
                        holder.mTitle.text = getString(R.string.chatroom_create_chat_room_tag)
                        holder.mContent.text = getString(R.string.chatroom_create_chat_room_desc)
                    } else if (data!![position].room_type == 1) {
    //                  holder.mLayout.setBackgroundResource(R.drawable.icon_create_3d_room);
                        holder.mTitle.text = getString(R.string.chatroom_create_3d_room)
                        holder.mContent.text = getString(R.string.chatroom_create_3d_room_desc)
                    }
                }

                override fun getItemCount(): Int {
                    return data!!.size
                }
            }

        // set TabLayoutMediator
        val mediator = TabLayoutMediator(
            binding.agoraTabLayout, binding.vpFragment
        ) { tab, position ->
            tab.setCustomView(R.layout.chatroom_create_tab_item_layout)
            val title = tab.customView!!.findViewById<TextView>(R.id.tab_item_title)
            title.text = data!![position].tab_title
        }
        // setup with viewpager2
        mediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.edPwd.rest()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mLayout: ConstraintLayout
        val mTitle: TextView
        val mContent: TextView

        init {
            mLayout = itemView.findViewById(R.id.item_layout)
            mTitle = itemView.findViewById(R.id.item_title)
            mContent = itemView.findViewById(R.id.item_text)
        }
    }

   class EmojiInputFilter(max: Int) : InputFilter.LengthFilter(max) {
        private var emoji = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
        )

       override fun filter(
           source: CharSequence,//即将要输入的字符串
           start: Int,//source的start
           end: Int,//source的end
           dest: Spanned,//输入框中原来的内容
           dstart: Int,//光标所在位置
           dend: Int//光标终止位置
       ): CharSequence {
           var resultInput = source.toString()
           val emojiMatcher: Matcher = emoji.matcher(source)
           if (emojiMatcher.find()) {
               return ""
           }
           return resultInput
       }
    }
}