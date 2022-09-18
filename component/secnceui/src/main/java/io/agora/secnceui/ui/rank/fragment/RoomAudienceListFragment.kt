package io.agora.secnceui.ui.rank.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.BaseUiFragment
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.buddy.tool.ViewTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.AudienceInfoBean
import io.agora.secnceui.databinding.FragmentChatroomAudienceListBinding
import io.agora.secnceui.databinding.ItemChatroomAudienceListBinding
import io.agora.secnceui.ui.rank.RoomAudienceListViewHolder
import io.agora.secnceui.ui.rank.RoomContributionAndAudienceConstructor

class RoomAudienceListFragment : BaseUiFragment<FragmentChatroomAudienceListBinding>() {

    companion object {
        fun getInstance(): RoomAudienceListFragment {
            return RoomAudienceListFragment()
        }
    }

    private var audienceAdapter: BaseRecyclerViewAdapter<ItemChatroomAudienceListBinding, AudienceInfoBean, RoomAudienceListViewHolder>? =
        null

    private val audienceList = mutableListOf<AudienceInfoBean>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentChatroomAudienceListBinding {
        return FragmentChatroomAudienceListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audienceList.addAll(RoomContributionAndAudienceConstructor.builderAudienceList(view.context))
        binding?.rvAudienceList?.let {
            initAdapter(it)
        }
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        audienceAdapter =
            BaseRecyclerViewAdapter(
                audienceList,
                null,
                object : OnItemChildClickListener<AudienceInfoBean> {
                    override fun onItemChildClick(
                        data: AudienceInfoBean?,
                        extData: Any?,
                        view: View,
                        position: Int,
                        itemViewType: Long
                    ) {
                        if (extData is Int) {
                            Toast.makeText(recyclerView.context, "${extData}", Toast.LENGTH_SHORT).show()
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
                    dividerColor = ViewTools.getColor(it.resources, R.color.divider_color_F8F5FA)
                }
            )
        }
        recyclerView.adapter = audienceAdapter
    }

}