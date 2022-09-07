package io.agora.secnceui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.BaseUiFragment
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.buddy.tool.ViewTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.ainoise.ChatroomAINSContentViewHolder
import io.agora.secnceui.ainoise.ChatroomAINSModeViewHolder
import io.agora.secnceui.ainoise.ChatroomAINSSoundsViewHolder
import io.agora.secnceui.ainoise.ChatroomAINSTitleViewHolder
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.bean.ContributionBean
import io.agora.secnceui.databinding.FragmentChatroomContributionRankingBinding
import io.agora.secnceui.databinding.ItemChatroomContributionRankingBinding

class ChatroomContributionRankingFragment : BaseUiFragment<FragmentChatroomContributionRankingBinding>() {

    companion object {
        fun getInstance(): ChatroomContributionRankingFragment {
            return ChatroomContributionRankingFragment()
        }
    }

    private var contributionAdapter: BaseRecyclerViewAdapter<ItemChatroomContributionRankingBinding, ContributionBean, ChatroomContributionRankingViewHolder>? =
        null


    private val contributionList = mutableListOf<ContributionBean>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatroomContributionRankingBinding {
        return FragmentChatroomContributionRankingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contributionList.addAll(ChatroomContributionAndAudienceConstructor.builderContributionRanking(view.context))
        binding?.rvContributionRanking?.let {
            initAdapter(it)
        }
        binding?.ivContributionEmpty?.isVisible = false
        binding?.mtContributionEmpty?.isVisible = false
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        contributionAdapter =
            BaseRecyclerViewAdapter(contributionList, null, ChatroomContributionRankingViewHolder::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)
        context?.let {
            recyclerView.addItemDecoration(
                MaterialDividerItemDecoration(it, MaterialDividerItemDecoration.HORIZONTAL).apply {
                    dividerThickness = 15.dp.toInt()
                    dividerColor = ViewTools.getColor(it.resources, R.color.divider_color_F8F5FA)
                }
            )
        }
        recyclerView.adapter = contributionAdapter
    }
}