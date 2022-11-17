package com.easemob.chatroom.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.facade.annotation.Route
import com.easemob.baseui.BaseUiActivity
import com.easemob.buddy.tool.FastClickTools.isFastClick
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ChatroomListLayoutBinding
import com.easemob.chatroom.general.repositories.ProfileManager
import com.easemob.chatroom.model.PageViewModel
import com.easemob.chatroom.ui.fragment.ChatroomListFragment
import com.easemob.config.RouterPath
import com.easemob.secnceui.widget.titlebar.ChatroomTitleBar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.hyphenate.util.DensityUtil

@Route(path = RouterPath.ChatroomListPath)
class ChatroomListActivity : BaseUiActivity<ChatroomListLayoutBinding>(),
    ChatroomTitleBar.OnBackPressListener, View.OnClickListener {
    private var pageViewModel: PageViewModel? = null
    private var title: TextView? = null
    private var mCount = 0
    private var index = 0
    private var resId = 0
    private val titles = intArrayOf(R.string.app_easemob_chatroom)

    override fun getViewBinding(inflater: LayoutInflater): ChatroomListLayoutBinding? {
        return ChatroomListLayoutBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
        initData()
        initListener()
    }

    private fun initListener() {
        pageViewModel = ViewModelProvider(this)[PageViewModel::class.java]
        binding.titleBar.setOnBackPressListener(this)
        binding.imageAvatar.setOnClickListener(this)

        binding.bottomLayout.setOnClickListener {
            startActivity(
                Intent(
                    this@ChatroomListActivity,
                    ChatroomCreateActivity::class.java
                )
            )
        }

        binding.listTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.customView != null) {
                    index = tab.position
                    title = tab.customView?.findViewById<TextView>(R.id.tab_item_title)
                    title?.setTextColor(resources.getColor(R.color.dark_grey_color_040925))
                    title?.setTypeface(null, Typeface.BOLD)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab.customView != null) {
                    val title = tab.customView!!.findViewById<TextView>(R.id.tab_item_title)
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    title.setText(titles[tab.position])
                    title.setTextColor(resources.getColor(R.color.color_979CBB))
                    setTextStyle(title, Typeface.NORMAL)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                title?.text = getString(titles[index])
            }
        })

        binding.vpFragment.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    pageViewModel!!.setPageSelect(-1)
                } else if (position == 1) {
                    pageViewModel!!.setPageSelect(0)
                } else if (position == 2) {
                    pageViewModel!!.setPageSelect(1)
                }
            }
        })
    }

    private fun initData() {
        setupWithViewPager()
    }

    private fun initView(savedInstanceState: Bundle?) {
        setTextStyle(binding.titleBar.title, Typeface.BOLD)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onBackPress(view: View?) {

    }

    override fun onClick(v: View) {
        if (!isFastClick(v, 1000)) startActivity(
            Intent(
                this@ChatroomListActivity,
                ChatroomProfileActivity::class.java
            )
        )

    }

    private fun setupWithViewPager() {
        binding.vpFragment.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        // set adapter
        binding.vpFragment.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun createFragment(position: Int): Fragment {
                    val fragment = ChatroomListFragment()
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    fragment.arguments = bundle
                    fragment.SetItemCountChangeListener { count ->
                        mCount = count
                        val layoutParams: ViewGroup.LayoutParams? = title?.layoutParams
                        layoutParams?.height = DensityUtil.dip2px(this@ChatroomListActivity, 26f)
                        title?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                        setTextStyle(title, Typeface.BOLD)
                        title?.gravity = Gravity.CENTER
                        val content = getString(titles[index]) + getString(
                            R.string.chatroom_tab_layout_count,
                            mCount.toString()
                        )
                        title?.setTextColor(getColor(R.color.dark_grey_color_040925))
                        title?.text = content
                    }
                    return fragment
                }

                override fun getItemCount(): Int {
                    return titles.size
                }
            }

        // set TabLayoutMediator
        val mediator = TabLayoutMediator(
            binding.listTabLayout, binding.vpFragment
        ) { tab, position ->
            tab.setCustomView(R.layout.tab_item_layout)
            val title = tab.customView!!.findViewById<TextView>(R.id.tab_item_title)
            title.setText(titles[position])
        }
        // setup with viewpager2
        mediator.attach()
    }

    override fun onResume() {
        super.onResume()
        try {
            resId = resources.getIdentifier(
                ProfileManager.getInstance().profile.portrait, "drawable",
                packageName
            )
            binding.imageAvatar.setImageResource(resId)
        } catch (e: Exception) {
            Log.e("getResources()", e.message.toString())
        }
    }
}