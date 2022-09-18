package io.agora.secnceui.ui.rank.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.BaseUiFragment
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ViewTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.ContributionBean
import io.agora.secnceui.databinding.FragmentChatroomContributionRankingBinding
import io.agora.secnceui.databinding.ItemChatroomContributionRankingBinding
import io.agora.secnceui.ui.rank.RoomContributionAndAudienceConstructor
import io.agora.secnceui.ui.rank.RoomContributionRankingViewHolder

class RoomContributionRankingFragment : BaseUiFragment<FragmentChatroomContributionRankingBinding>() {

    companion object {
        fun getInstance(): RoomContributionRankingFragment {
            return RoomContributionRankingFragment()
        }
    }

    private var contributionAdapter: BaseRecyclerViewAdapter<ItemChatroomContributionRankingBinding, ContributionBean, RoomContributionRankingViewHolder>? =
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
        contributionList.addAll(RoomContributionAndAudienceConstructor.builderContributionRanking(view.context))
        binding?.rvContributionRanking?.let {
            initAdapter(it)
        }
        binding?.ivContributionEmpty?.isVisible = false
        binding?.mtContributionEmpty?.isVisible = false
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        contributionAdapter =
            BaseRecyclerViewAdapter(contributionList, null, RoomContributionRankingViewHolder::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)
        context?.let {
            recyclerView.addItemDecoration(
                MaterialDividerItemDecoration(it, MaterialDividerItemDecoration.VERTICAL).apply {
                    dividerThickness = 1.dp.toInt()
                    dividerInsetStart = 15.dp.toInt()
                    dividerInsetEnd =  15.dp.toInt()
                    dividerColor = ViewTools.getColor(it.resources, R.color.divider_color_F8F5FA)
                }
            )
        }
        recyclerView.adapter = contributionAdapter
    }
}