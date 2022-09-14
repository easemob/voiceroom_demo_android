package io.agora.chatroom.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import io.agora.ValueCallBack;
import io.agora.baseui.BaseActivity;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatRoom;
import io.agora.chatroom.R;
import io.agora.chatroom.bean.PageBean;
import io.agora.config.RouterPath;
import io.agora.secnceui.widget.encryption.ChatroomEncryptionInputView;
import io.agora.secnceui.widget.titlebar.ChatroomTitleBar;

@Route(path = RouterPath.ChatroomCreatePath)
public class ChatroomCreateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ChatroomTitleBar.OnBackPressListener, View.OnClickListener {

   private RadioGroup mRadioGroup;
   private ChatroomTitleBar mTitleBar;
   private TabLayout mTableLayout;
   private ViewPager2 mViewPager;
   private TextView mRandom;
   private TextView mNext;
   private EditText mEdRoomName;
   private boolean isPublic = true;
   private TextView mTip;
   private ArrayList<PageBean> data = new ArrayList<>();
   private ChatroomEncryptionInputView mEditText;
   private ConstraintLayout baseLayout;
   private int roomType;

   @Override
   protected int getLayoutId() {
      return R.layout.agora_create_room_layout;
   }

   @Override
   protected void initView(Bundle savedInstanceState) {
      super.initView(savedInstanceState);
      mRadioGroup = findViewById(R.id.radioGroup_gender);
      mTitleBar = findViewById(R.id.title_bar);
      mTableLayout = findViewById(R.id.agora_tab_layout);
      mViewPager = findViewById(R.id.vp_fragment);
      mRandom = findViewById(R.id.random);
      mEdRoomName = findViewById(R.id.ed_room_name);
      mEditText = findViewById(R.id.ed_pwd);
      mTip = findViewById(R.id.input_tip);
      mNext = findViewById(R.id.bottom_next);
      baseLayout = findViewById(R.id.base_layout);
      chickPrivate();
      data.clear();
      PageBean bean = new PageBean();
      bean.setRoom_type(0);
      bean.setTab_title(getString(R.string.tab_layout_chat_room));
      bean.setRoom_name(getString(R.string.room_create_chat_room));
      bean.setRoom_desc(getString(R.string.room_create_chat_room_desc));
      data.add(bean);
      PageBean bean1 = new PageBean();
      bean1.setRoom_type(1);
      bean1.setTab_title(getString(R.string.tab_layout_audio_room));
      bean1.setRoom_name(getString(R.string.room_create_3d_room));
      bean1.setRoom_desc(getString(R.string.room_create_3d_room_desc));
      data.add(bean1);
      PageBean bean2 = new PageBean();
      bean2.setRoom_type(2);
      bean2.setTab_title(getString(R.string.tab_layout_karaoke_room));
      bean2.setRoom_name(getString(R.string.room_create_ktv_room));
      bean2.setRoom_desc(getString(R.string.room_create_ktv_room_desc));
      data.add(bean2);
   }

   @Override
   protected void initListener() {
      super.initListener();
      mEditText.setOnClickListener(this);
      mEdRoomName.setOnClickListener(this);
      mRadioGroup.setOnCheckedChangeListener(this);
      mTitleBar.setOnBackPressListener(this);
      mNext.setOnClickListener(this);
      mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
         @Override
         public void onTabSelected(TabLayout.Tab tab) {
            if(tab.getCustomView() != null) {
               TextView title = tab.getCustomView().findViewById(R.id.tab_item_title);
               ViewGroup.LayoutParams layoutParams = title.getLayoutParams();
               layoutParams.height = (int)dip2px(mContext, 26);
               title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
               title.setGravity(Gravity.CENTER);
            }
         }

         @Override
         public void onTabUnselected(TabLayout.Tab tab) {
            if(tab.getCustomView() != null) {
               TextView title = tab.getCustomView().findViewById(R.id.tab_item_title);
               title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
         }

         @Override
         public void onTabReselected(TabLayout.Tab tab) {

         }
      });

      mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
         @Override
         public void onPageSelected(int position) {
            roomType = data.get(position).getRoom_type();
         }
      });

      baseLayout.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            baseLayout.setFocusable(true);
            baseLayout.setFocusableInTouchMode(true);
            baseLayout.requestFocus();
            hideKeyboard();
            return false;
         }
      });
   }

   @Override
   protected void initData() {
      super.initData();
      setupWithViewPager();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
   }

   @Override
   protected void initSystemFit() {
      setFitSystemForTheme(false, "#00000000");
      setStatusBarTextColor(false);
   }

   @Override
   public void onCheckedChanged(RadioGroup group, int checkedId) {
      if (checkedId == R.id.radioButton_private) {
         isPublic = false;
      } else if (checkedId == R.id.radioButton_public) {
         isPublic = true;
      }
      chickPrivate();
   }

   private void setupWithViewPager() {
      mViewPager.setOffscreenPageLimit(1);
      View recyclerView = mViewPager.getChildAt(0);
      if(recyclerView instanceof RecyclerView){
         recyclerView.setPadding(85, 0, 85, 0);
         ((RecyclerView) recyclerView).setClipToPadding(false);
      }
      CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
      compositePageTransformer.addTransformer(new MarginPageTransformer(16));
      mViewPager.setPageTransformer(compositePageTransformer);
      // set adapter
      mViewPager.setAdapter(new RecyclerView.Adapter<ViewHolder>() {

         @NonNull
         @Override
         public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ChatroomCreateActivity.this).inflate(R.layout.chatroom_create_page_item_layout,parent, false);
            return new ViewHolder(view);
         }

         @Override
         public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
               if (data.get(position).getRoom_type() == 0){
                  holder.mLayout.setBackgroundResource(R.mipmap.icon_create_chat_room);
                  holder.mTitle.setText(getString(R.string.room_create_chat_room));
                  holder.mContent.setText(getString(R.string.room_create_chat_room_desc));
               }else if (data.get(position).getRoom_type() == 1){
                  holder.mLayout.setBackgroundResource(R.mipmap.icon_create_3d_room);
                  holder.mTitle.setText(getString(R.string.room_create_3d_room));
                  holder.mContent.setText(getString(R.string.room_create_3d_room_desc));
               }else if (data.get(position).getRoom_type() == 2){
                  holder.mLayout.setBackgroundResource(R.mipmap.icon_create_ktv_room);
                  holder.mTitle.setText(getString(R.string.room_create_ktv_room));
                  holder.mContent.setText(getString(R.string.room_create_ktv_room_desc));
               }
         }

         @Override
         public int getItemCount() {
            return data.size();
         }

      });

      // set TabLayoutMediator
      TabLayoutMediator mediator = new TabLayoutMediator(mTableLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
         @Override
         public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            tab.setCustomView(R.layout.chatroom_create_tab_item_layout);
            TextView title = tab.getCustomView().findViewById(R.id.tab_item_title);
            title.setText(data.get(position).getTab_title());
         }
      });
      // setup with viewpager2
      mediator.attach();
   }

   private void chickPrivate(){
       if (isPublic){
          mEditText.setVisibility(View.GONE);
          mTip.setVisibility(View.GONE);
       }else {
          mEditText.setVisibility(View.VISIBLE);
          mTip.setVisibility(View.VISIBLE);
       }
   }

   @Override
   public void onBackPress(View view) {
      onBackPressed();
   }

   @Override
   public void onClick(View v) {
      if (v.getId() == R.id.bottom_next){
         ChatClient.getInstance().chatroomManager().joinChatRoom("191488197722116", new ValueCallBack<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom value) {
               Log.e("DemoApplication","joinChatRoom onSuccess");
//               startActivity(new Intent(AgoraCreateRoomActivity.this,AgoraChatRoomDetailsActivity.class));
            }

            @Override
            public void onError(int error, String errorMsg) {
               Log.e("DemoApplication","joinChatRoom onError" + error +"  "+ errorMsg);
            }
         });
            String en =  mEditText.getText().toString().trim();
            String roomName = mEdRoomName.getText().toString().trim();
         if (isPublic){
            // TODO: 2022/8/30  next
         }else {
            if (!TextUtils.isEmpty(en) && en.length() == 4){
               // TODO: 2022/8/30  next
            }else {
               // TODO: 2022/8/30  show Toast  4 Digit Password Required
            }
         }
      }else if (v.getId() == R.id.ed_room_name){

         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
         mEdRoomName.setFocusable(true);
         mEdRoomName.setFocusableInTouchMode(true);
         mEdRoomName.requestFocus();

      }else if (v.getId() == R.id.ed_pwd){

         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         mEditText.setFocusable(true);
         mEditText.setFocusableInTouchMode(true);
         mEditText.requestFocus();

      }
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {

      private ConstraintLayout mLayout;
      private TextView mTitle;
      private TextView mContent;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         mLayout =  itemView.findViewById(R.id.item_layout);
         mTitle =  itemView.findViewById(R.id.item_title);
         mContent = itemView.findViewById(R.id.item_text);
      }
   }
}
