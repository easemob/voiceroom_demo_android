package io.agora.chatroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import io.agora.baseui.BaseActivity;
import io.agora.chatroom.R;
import io.agora.chatroom.fragment.ChatroomListFragment;
import io.agora.chatroom.model.PageViewModel;
import io.agora.config.RouterPath;
import io.agora.secnceui.widget.titlebar.ChatroomTitleBar;

@Route(path = RouterPath.ChatroomListPath)
public class ChatroomListActivity extends BaseActivity implements ChatroomTitleBar.OnBackPressListener, View.OnClickListener {

    private ChatroomTitleBar mVRTitleBar;
    private ShapeableImageView mVRAvatar;
    private ConstraintLayout mAvatarLayout;
    private ConstraintLayout mButtonLayout;
    private TabLayout mTableLayout;
    private ViewPager2 mViewPager;
    private PageViewModel pageViewModel;
    private TextView title;
    private int mCount;
    private int index;
    private int[] titles = {R.string.tab_layout_all,R.string.tab_layout_chat_room,R.string.tab_layout_audio_room};

    @Override
    protected int getLayoutId() {
        return R.layout.agora_room_list_layout;
    }

    @Override
    protected void initSystemFit() {
        setFitSystemForTheme(false, "#00000000");
        setStatusBarTextColor(false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mVRTitleBar =  findViewById(R.id.title_bar);
        mVRAvatar = findViewById(R.id.image_avatar);
        mAvatarLayout = findViewById(R.id.avatar_layout);
        mTableLayout = findViewById(R.id.agora_tab_layout);
        mViewPager = findViewById(R.id.vp_fragment);
        mButtonLayout = findViewById(R.id.bottom_layout);
    }

    @Override
    protected void initData() {
        setupWithViewPager();
    }

    @Override
    protected void initListener() {
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        mVRTitleBar.setOnBackPressListener(this);
        mButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatroomListActivity.this, ChatroomCreateActivity.class));
            }
        });
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getCustomView() != null) {
                    index = tab.getPosition();
                    title = tab.getCustomView().findViewById(R.id.tab_item_title);
                    ViewGroup.LayoutParams layoutParams = title.getLayoutParams();
                    layoutParams.height = (int)dip2px(mContext, 26);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    title.setGravity(Gravity.CENTER);
                    String content = getString(titles[index]) + getString(R.string.room_tab_layout_count,String.valueOf(mCount));
                    title.setText(content);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getCustomView() != null) {
                    TextView title = tab.getCustomView().findViewById(R.id.tab_item_title);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    title.setText(titles[tab.getPosition()]);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    pageViewModel.setPageSelect(-1);
                }else if (position == 1){
                    pageViewModel.setPageSelect(0);
                }else if (position == 2){
                    pageViewModel.setPageSelect(1);
                }
            }
        });

        mVRAvatar.setOnClickListener(this);

    }

    private void setupWithViewPager() {
        mViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        // set adapter
        mViewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                ChatroomListFragment fragment = new ChatroomListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                fragment.setArguments(bundle);
                fragment.SetItemCountChangeListener(new ChatroomListFragment.itemCountListener() {
                    @Override
                    public void getItemCount(int count) {
                        mCount = count;
                        String content = getString(titles[index]) + getString(R.string.room_tab_layout_count,String.valueOf(count));
                        title.setText(content);
                    }
                });
                return fragment;
            }

            @Override
            public int getItemCount() {
                return titles.length;
            }

        });

        // set TabLayoutMediator
        TabLayoutMediator mediator = new TabLayoutMediator(mTableLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(R.layout.tab_item_layout);
                TextView title = tab.getCustomView().findViewById(R.id.tab_item_title);
                title.setText(titles[position]);
            }
        });
        // setup with viewpager2
        mediator.attach();
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(ChatroomListActivity.this, ChatroomProfileActivity.class));
    }

}
