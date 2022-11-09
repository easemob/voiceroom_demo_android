package com.easemob.chatroom.ui.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.easemob.baseui.dialog.BaseSheetDialog;
import com.easemob.chatroom.R;
import com.easemob.chatroom.databinding.ChatroomHandLayoutBinding;
import com.easemob.chatroom.ui.fragment.ChatroomInviteHandsFragment;
import com.easemob.chatroom.ui.fragment.ChatroomRaisedHandsFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.Map;

import com.easemob.baseui.BaseInitFragment;
import com.easemob.secnceui.utils.DeviceUtils;

public class ChatroomHandsDialog extends BaseSheetDialog<ChatroomHandLayoutBinding> {
    private int[] titles ={R.string.chatroom_raised_hands_title, R.string.chatroom_invite_hands_title};
    private ArrayList<BaseInitFragment> fragments=new ArrayList();
    private MaterialTextView title;
    private int index;
    private int mCount;
    private String roomId;
    private Bundle bundle = new Bundle();
    private ChatroomRaisedHandsFragment raisedHandsFragment;
    private ChatroomInviteHandsFragment inviteHandsFragment;
    private ChatroomHandLayoutBinding binding;

    @Nullable
    @Override
    protected ChatroomHandLayoutBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return ChatroomHandLayoutBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getBinding() != null)
            binding = getBinding();
        setOnApplyWindowInsets(binding.getRoot());
        initArgument();
        initView(savedInstanceState);
        initListener();
    }

    public static ChatroomHandsDialog getNewInstance() {
        return new ChatroomHandsDialog();
    }

    public void initArgument() {
        if (getArguments() != null && getArguments().containsKey("roomId"))
        roomId = getArguments().getString("roomId");
    }

    public void initView(Bundle savedInstanceState) {
        fragments.add(new ChatroomRaisedHandsFragment());
        fragments.add(new ChatroomInviteHandsFragment());
        setupWithViewPager();
    }

    public void initListener() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getCustomView() != null) {
                    Log.e("ChatroomHandsDialog","onTabSelected：" + mCount);
                    index = tab.getPosition();
                    title = tab.getCustomView().findViewById(R.id.mtTabText);
                    ShapeableImageView tag_line = tab.getCustomView().findViewById(R.id.tab_bg);
                    ViewGroup.LayoutParams layoutParams = title.getLayoutParams();
                    layoutParams.height = (int) DeviceUtils.dp2px(getActivity(), 26);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null,Typeface.BOLD);
                    String content = getString(titles[index]) + getString(R.string.room_tab_layout_count,String.valueOf(mCount));
                    title.setText(content);
                    title.setTextColor(getResources().getColor(R.color.dark_grey_color_040925));
                    tag_line.setBackgroundColor(getResources().getColor(R.color.color_156EF3));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getCustomView() != null) {
                    Log.e("ChatroomHandsDialog","onTabUnselected：" + mCount);
                    MaterialTextView title = tab.getCustomView().findViewById(R.id.mtTabText);
                    ShapeableImageView tag_line = tab.getCustomView().findViewById(R.id.tab_bg);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    title.setText(titles[tab.getPosition()]);
                    title.setTypeface(null,Typeface.NORMAL);
                    title.setTextColor(getResources().getColor(R.color.color_979CBB));
                    tag_line.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("ChatroomHandsDialog","onTabReselected");
                title = tab.getCustomView().findViewById(R.id.mtTabText);
                title.setText(titles[tab.getPosition()]);
                title.setTextColor(getResources().getColor(R.color.dark_grey_color_040925));
                title.setTypeface(null,Typeface.BOLD);
            }
        });
        binding.vpFragment.setCurrentItem(0);
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0));
    }

    private void setupWithViewPager() {
        binding.vpFragment.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        // set adapter
        binding.vpFragment.setAdapter(new FragmentStateAdapter(getActivity().getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (fragments.get(position) instanceof ChatroomRaisedHandsFragment){
                    raisedHandsFragment = ((ChatroomRaisedHandsFragment)fragments.get(position));
                    raisedHandsFragment.setItemCountChangeListener(new ChatroomRaisedHandsFragment.itemCountListener() {
                        @Override
                        public void getItemCount(int count) {
                            mCount = count;
                            if (getActivity() != null){
                                String content = requireActivity().getString(titles[index]) + getString(R.string.room_tab_layout_count,String.valueOf(mCount));
                                Log.e("getItemCount","content1: " + content);
                                title.setText(content);
                            }
                        }
                    });
                }else if (fragments.get(position) instanceof ChatroomInviteHandsFragment){
                    inviteHandsFragment = ((ChatroomInviteHandsFragment)fragments.get(position));
                    inviteHandsFragment.setItemCountChangeListener(new ChatroomInviteHandsFragment.itemCountListener() {
                        @Override
                        public void getItemCount(int count) {
                            mCount = count;
                            if (getActivity() != null){
                                String content = requireActivity().getResources().getString(titles[index]) + getString(R.string.room_tab_layout_count,String.valueOf(mCount));
                                Log.e("getItemCount","content2: " + content);
                                title.setText(content);
                            }
                        }
                    });
                }
                bundle.putString("roomId",roomId);
                fragments.get(position).setArguments(bundle);
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return titles.length;
            }

        });

        // set TabLayoutMediator
        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayout, binding.vpFragment, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(R.layout.chatroom_hands_tab_item);
                title = tab.getCustomView().findViewById(R.id.mtTabText);
                title.setText(titles[position]);
            }
        });
        // setup with viewpager2
        mediator.attach();
    }

    public void update(int index){
        switch (index){
            case 0:
                if (raisedHandsFragment != null)
                    raisedHandsFragment.reset();
                break;
            case 1:
                if (inviteHandsFragment != null)
                    inviteHandsFragment.reset();
                break;
        }
    }

    public void check(Map<String,String> map){
        if (inviteHandsFragment != null)
            inviteHandsFragment.MicChanged(map);
    }

}
