package io.agora.chatroom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.BaseUiFragment
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.buddy.tool.*
import io.agora.chatroom.adapter.RoomAudienceListViewHolder
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.databinding.FragmentChatroomAudienceListBinding
import io.agora.chatroom.databinding.ItemChatroomAudienceListBinding
import io.agora.chatroom.general.net.HttpManager
import io.agora.chatroom.model.RoomRankViewModel
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicClickAction
import tools.ValueCallBack
import tools.bean.VMemberBean
import tools.bean.VRoomUserBean

class RoomAudienceListFragment : BaseUiFragment<FragmentChatroomAudienceListBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {

        private const val KEY_ROOM_INFO = "room_info"

        fun getInstance(roomKitBean: RoomKitBean): RoomAudienceListFragment {
            return RoomAudienceListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_ROOM_INFO, roomKitBean)
                }
            }
        }
    }

    private var roomKitBean: RoomKitBean? = null

    private lateinit var roomRankViewModel: RoomRankViewModel

    private var pageSize = 10
    private var cursor = ""
    private var total = 0
    private var isEnd = false

    private var audienceAdapter: BaseRecyclerViewAdapter<ItemChatroomAudienceListBinding, VMemberBean, RoomAudienceListViewHolder>? =
        null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentChatroomAudienceListBinding {
        return FragmentChatroomAudienceListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomRankViewModel = ViewModelProvider(requireActivity())[RoomRankViewModel::class.java]
        arguments?.apply {
            roomKitBean = getSerializable(KEY_ROOM_INFO) as RoomKitBean?
            roomKitBean?.let {
                roomRankViewModel.getMembers(requireContext(), it.roomId, pageSize, cursor)
            }
        }
        binding?.apply {
            initAdapter(rvAudienceList)
            slAudienceList.setOnRefreshListener(this@RoomAudienceListFragment)
        }
        roomRankViewModel.membersObservable().observe(requireActivity()) { response: Resource<VRoomUserBean> ->
            parseResource(response, object : OnResourceParseCallback<VRoomUserBean>() {
                override fun onSuccess(data: VRoomUserBean?) {
                    binding?.slAudienceList?.isRefreshing = false
                    "getMembers cursor：${data?.cursor}，total：${data?.total}".logE()
                    if (data == null) return
                    cursor = data.cursor ?: ""
                    total = data.total
                    checkEmpty()
                    if (!data.members.isNullOrEmpty()) {
                        if (data.members.size < pageSize) {
                            isEnd = true
                        }
                        audienceAdapter?.addItems(data.members)
                    } else {
                        isEnd = true
                    }
                }

                override fun onError(code: Int, message: String?) {
                    super.onError(code, message)
                    binding?.slAudienceList?.isRefreshing = false
                }
            })
        }
    }

    private fun checkEmpty() {
        binding?.apply {
            if (total == 0) {
                ivContributionEmpty.isVisible = true
                mtContributionEmpty.isVisible = true
            } else {
                ivContributionEmpty.isVisible = false
                mtContributionEmpty.isVisible = false
            }
        }
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        audienceAdapter =
            BaseRecyclerViewAdapter(
                null,
                null,
                object : OnItemChildClickListener<VMemberBean> {
                    override fun onItemChildClick(
                        data: VMemberBean?,
                        extData: Any?,
                        view: View,
                        position: Int,
                        itemViewType: Long
                    ) {
                        if (extData is Int) {
                            handleRequest(roomKitBean?.roomId, data?.uid, data?.mic_index, extData)
                        }
                    }
                },
                RoomAudienceListViewHolder::class.java
            )

        recyclerView.layoutManager = LinearLayoutManager(context)
        context?.let {
            recyclerView.addItemDecoration(
                MaterialDividerItemDecoration(it, MaterialDividerItemDecoration.VERTICAL).apply {
                    dividerThickness = 1.dp.toInt()
                    dividerInsetStart = 15.dp.toInt()
                    dividerInsetEnd = 15.dp.toInt()
                    dividerColor = ResourcesTools.getColor(it.resources, R.color.divider_color_F8F5FA)
                }
            )
        }
        recyclerView.adapter = audienceAdapter
    }

    override fun onRefresh() {
        if (isEnd || cursor.isEmpty()) {
            ThreadManager.getInstance().runOnMainThreadDelay({
                binding?.slAudienceList?.isRefreshing = false
            }, 1500)
        } else {
            roomKitBean?.let {
                roomRankViewModel.getMembers(requireContext(), it.roomId, pageSize, cursor)
            }
        }
    }

    private fun handleRequest(roomId: String?, uid: String?, micIndex: Int?, @MicClickAction action: Int) {
        if (roomId.isNullOrEmpty() || uid.isNullOrEmpty()) return
        context?.let {  parentContext ->
            if (action == MicClickAction.Invite) {
                HttpManager.getInstance(parentContext).invitationMic(roomId, uid, object : ValueCallBack<Boolean> {
                    override fun onSuccess(var1: Boolean?) {
                        if (var1 != true) return
                        context?.let {
                            ToastTools.show(it,"invitationMic success")
                        }
                    }

                    override fun onError(var1: Int, var2: String?) {

                    }
                })
            } else if (action == MicClickAction.KickOff) {
                HttpManager.getInstance(parentContext)
                    .kickMic(roomId, uid, micIndex ?: -1, object : ValueCallBack<Boolean> {
                        override fun onSuccess(var1: Boolean?) {
                            if (var1 != true) return
                            context?.let {
                                ToastTools.show(it,"kickMic success")
                            }
                        }

                        override fun onError(var1: Int, var2: String?) {
                            context?.let {
                                ToastTools.show(it,"kickMic onError $var1 $var2")
                            }
                        }
                    })
            }
        }


    }
}