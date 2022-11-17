package com.easemob.chatroom.ui.activity

import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.alibaba.android.arouter.launcher.ARouter
import com.easemob.baseui.BaseUiActivity
import com.easemob.baseui.general.callback.OnResourceParseCallback
import com.easemob.baseui.general.net.Resource
import com.easemob.buddy.tool.FastClickTools.isFastClick
import com.easemob.buddy.tool.ThreadManager
import com.easemob.buddy.tool.ToastTools.show
import com.easemob.chatroom.ChatroomApplication
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ChatroomSoundSelectionLayoutBinding
import com.easemob.chatroom.model.ChatroomViewModel
import com.easemob.chatroom.ui.adapter.ChatroomSoundSelectionAdapter
import com.easemob.config.ConfigConstants
import com.easemob.config.RouterParams
import com.easemob.config.RouterPath
import com.easemob.secnceui.bean.SoundSelectionBean
import com.easemob.secnceui.ui.soundselection.RoomSoundSelectionConstructor.builderSoundSelectionList
import com.easemob.secnceui.widget.titlebar.ChatroomTitleBar
import com.hyphenate.util.EMLog
import tools.bean.VRoomInfoBean

class ChatroomSoundSelectionActivity : BaseUiActivity<ChatroomSoundSelectionLayoutBinding>(),
    ChatroomSoundSelectionAdapter.OnItemClickListener, ChatroomTitleBar.OnBackPressListener,
    View.OnClickListener {
    private var roomName: String? = null
    private var isPublic = true
    private var encryption: String? = null
    private var roomType = 0
    private var adapter: ChatroomSoundSelectionAdapter? = null
    private var chatroomViewModel: ChatroomViewModel? = null
    private var soundEffect: String? = null
    private var sound_effect = ConfigConstants.SoundSelection.Social_Chat

    override fun getViewBinding(inflater: LayoutInflater): ChatroomSoundSelectionLayoutBinding? {
        return ChatroomSoundSelectionLayoutBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntent()
        initView()
        initListener()
        initData()
    }

    private fun initData() {
        getSoundSelectionData(this)
        adapter?.setSelectedPosition(0)
        soundEffect = ConfigConstants.SoundSelectionText.Social_Chat
        chatroomViewModel = ViewModelProvider(this)[ChatroomViewModel::class.java]
        chatroomViewModel?.createObservable?.observe(this,
            Observer { response: Resource<VRoomInfoBean?>? ->
                parseResource(
                    response,
                    object : OnResourceParseCallback<VRoomInfoBean?>() {
                        override fun onSuccess(data: VRoomInfoBean?) {
                            if (null != data && null != data.room) {
                                joinRoom(data)
                            }
                        }

                        override fun onError(code: Int, message: String?) {
                            dismissLoading()
                            EMLog.d("Go Live onError", "$code  $message")
                        }
                    })
            })
    }

    private fun initListener() {
        adapter?.SetOnItemClickListener(this)
        binding.titleBar.setOnBackPressListener(this)
        binding.bottomGoLive.setOnClickListener(this)
    }

    private fun initView() {
        val offsetPx = resources.getDimension(R.dimen.space_84dp)
        binding.list.layoutManager = LinearLayoutManager(this)
        val bottomOffsetDecoration = BottomOffsetDecoration(offsetPx.toInt())
        adapter = ChatroomSoundSelectionAdapter(this)
        binding.list.addItemDecoration(bottomOffsetDecoration)
        binding.list.adapter = adapter
        setTextStyle(binding.titleBar.title, Typeface.BOLD)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun initIntent() {
        if (null != intent) {
            roomName = intent.getStringExtra(RouterParams.KEY_CHATROOM_CREATE_NAME)
            isPublic = intent.getBooleanExtra(RouterParams.KEY_CHATROOM_CREATE_IS_PUBLIC, true)
            encryption = intent.getStringExtra(RouterParams.KEY_CHATROOM_CREATE_ENCRYPTION)
            roomType = intent.getIntExtra(RouterParams.KEY_CHATROOM_CREATE_ROOM_TYPE, 0)
        }
    }

    internal class BottomOffsetDecoration(private val mBottomOffset: Int) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val dataSize = state.itemCount
            val position = parent.getChildAdapterPosition(view)
            if (dataSize > 0 && position == dataSize - 1) {
                outRect[0, 0, 0] = mBottomOffset
            } else {
                outRect[0, 0, 0] = 0
            }
        }
    }

    private fun createNormalRoom(allow_free_join_mic: Boolean, sound_effect: String?) {
        showLoading(false)
        if (isPublic) {
            chatroomViewModel!!.createNormalRoom(
                this,
                roomName,
                false,
                allow_free_join_mic,
                sound_effect
            )
        } else {
            if (!TextUtils.isEmpty(encryption) && encryption!!.length == 4) {
                chatroomViewModel!!.createNormalRoom(
                    this,
                    roomName,
                    true,
                    encryption,
                    allow_free_join_mic,
                    sound_effect
                )
            } else {
                show(this, getString(R.string.chatroom_room_create_tips), Toast.LENGTH_LONG)
            }
        }
    }

    fun joinRoom(data: VRoomInfoBean?) {
        ThreadManager.getInstance().runOnMainThread {
            dismissLoading()
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withSerializable(RouterParams.KEY_CHATROOM_DETAILS_INFO, data)
                .navigation()
            finishCreateActivity()
            finish()
        }
    }

    /**
     * 结束创建activity
     */
    private fun finishCreateActivity() {
        val lifecycleCallbacks = ChatroomApplication.getInstance().lifecycleCallbacks
        if (lifecycleCallbacks == null) {
            finish()
            return
        }
        val activities = lifecycleCallbacks.activityList
        if (activities == null || activities.isEmpty()) {
            finish()
            return
        }
        for (activity in activities) {
            if (activity !== lifecycleCallbacks.current() && activity is ChatroomCreateActivity) {
                activity.finish()
            }
        }
    }

    private fun getSoundSelectionData(context: Context?) {
        val soundSelectionList: List<SoundSelectionBean> = builderSoundSelectionList(
            context!!, ConfigConstants.SoundSelection.Social_Chat
        )
        Log.e("ChatroomSoundSelectionActivity", " getData" + soundSelectionList.size)
        adapter?.data = soundSelectionList
    }

    override fun OnItemClick(position: Int, bean: SoundSelectionBean?) {
        adapter?.setSelectedPosition(position)
        sound_effect = bean!!.soundSelectionType
    }

    override fun onBackPress(view: View?) {
        onBackPressed()
    }

    override fun onClick(view: View?) {
        if (roomType == 0) {
            soundEffect =
                when (sound_effect) {
                    ConfigConstants.SoundSelection.Karaoke -> ConfigConstants.SoundSelectionText.Karaoke
                    ConfigConstants.SoundSelection.Gaming_Buddy -> ConfigConstants.SoundSelectionText.Gaming_Buddy
                    ConfigConstants.SoundSelection.Professional_Broadcaster -> ConfigConstants.SoundSelectionText.Professional_Broadcaster
                    else -> ConfigConstants.SoundSelectionText.Social_Chat
                }
            if (view?.let { isFastClick(it, 1000) } == false) createNormalRoom(false, soundEffect)
        }
    }
}