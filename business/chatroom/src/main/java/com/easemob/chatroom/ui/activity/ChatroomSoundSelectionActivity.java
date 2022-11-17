package com.easemob.chatroom.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easemob.chatroom.R;
import com.easemob.chatroom.model.ChatroomViewModel;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;
import com.easemob.baseui.BaseActivity;
import com.easemob.baseui.general.callback.OnResourceParseCallback;
import com.easemob.buddy.tool.FastClickTools;
import com.easemob.buddy.tool.ThreadManager;
import com.easemob.buddy.tool.ToastTools;
import com.easemob.chatroom.ChatroomApplication;
import com.easemob.chatroom.ui.adapter.ChatroomSoundSelectionAdapter;
import com.easemob.chatroom.general.interfaceOrImplement.UserActivityLifecycleCallbacks;
import com.easemob.chatroom.general.repositories.ProfileManager;

import com.easemob.config.ConfigConstants;
import com.easemob.config.RouterParams;
import com.easemob.config.RouterPath;
import com.easemob.secnceui.bean.SoundSelectionBean;
import com.easemob.secnceui.ui.soundselection.RoomSoundSelectionConstructor;
import com.easemob.secnceui.widget.titlebar.ChatroomTitleBar;
import com.hyphenate.EMCallBack;
import com.hyphenate.util.EMLog;

import manager.ChatroomHelper;
import tools.bean.VRUserBean;
import tools.bean.VRoomInfoBean;

public class ChatroomSoundSelectionActivity extends BaseActivity implements ChatroomSoundSelectionAdapter.OnItemClickListener, ChatroomTitleBar.OnBackPressListener, View.OnClickListener {
   private ChatroomTitleBar titleBar;
   private RecyclerView recyclerView;
   private ConstraintLayout goLive;
   private ChatroomSoundSelectionAdapter adapter;
   private ChatroomViewModel chatroomViewModel;
   private boolean isPublic = true;
   private String roomName;
   private String encryption;
   private String soundEffect;
   private int roomType;
   private int sound_effect = ConfigConstants.SoundSelection.Social_Chat;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
      layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS ;
      setNavAndStatusBarTransparent(this);
      super.onCreate(savedInstanceState);
   }

   @Override
   protected int getLayoutId() {
      return R.layout.chatroom_sound_selection_layout;
   }

   @Override
   protected void initSystemFit() {
      setFitSystemForTheme(false, "#00000000");
      setStatusBarTextColor(false);
   }

   @Override
   protected void initIntent(Intent intent) {
      super.initIntent(intent);
      if (null != intent){
         roomName = intent.getStringExtra(RouterParams.KEY_CHATROOM_CREATE_NAME);
         isPublic = intent.getBooleanExtra(RouterParams.KEY_CHATROOM_CREATE_IS_PUBLIC,true);
         encryption = intent.getStringExtra(RouterParams.KEY_CHATROOM_CREATE_ENCRYPTION);
         roomType = intent.getIntExtra(RouterParams.KEY_CHATROOM_CREATE_ROOM_TYPE,0);
      }
   }

   @Override
   protected void initView(Bundle savedInstanceState) {
      super.initView(savedInstanceState);
      titleBar = findViewById(R.id.title_bar);
      recyclerView = findViewById(R.id.list);
      goLive = findViewById(R.id.bottom_layout);
      adapter = new ChatroomSoundSelectionAdapter(this);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      float offsetPx = getResources().getDimension(R.dimen.space_84dp);
      BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
      recyclerView.addItemDecoration(bottomOffsetDecoration);
      recyclerView.setAdapter(adapter);
      setTextStyle(titleBar.getTitle(), Typeface.BOLD);
   }

   @Override
   protected void initListener() {
      super.initListener();
      adapter.SetOnItemClickListener(this);
      titleBar.setOnBackPressListener(this);
      goLive.setOnClickListener(this);
   }

   @Override
   protected void initData() {
      super.initData();
      getSoundSelectionData(this);
      adapter.setSelectedPosition(0);
      soundEffect = ConfigConstants.SoundSelectionText.Social_Chat;
      chatroomViewModel = new ViewModelProvider(this).get(ChatroomViewModel.class);
      chatroomViewModel.getCreateObservable().observe(this,response -> {
         parseResource(response, new OnResourceParseCallback<VRoomInfoBean>() {
            @Override
            public void onSuccess(@Nullable VRoomInfoBean data) {
               if (null != data && null != data.getRoom()){
                  joinRoom(data);
               }
            }
         });
      });
   }

   public void createNormalRoom(boolean allow_free_join_mic,String sound_effect){
      showLoading(false);
      if (isPublic){
         chatroomViewModel.createNormalRoom(this,roomName,false,allow_free_join_mic,sound_effect);
      }else {
         if (!TextUtils.isEmpty(encryption) && encryption.length() == 4){
            chatroomViewModel.createNormalRoom(this,roomName,true,encryption,allow_free_join_mic,sound_effect);
         }else {
            ToastTools.show(this,getString(R.string.chatroom_room_create_tips), Toast.LENGTH_LONG);
         }
      }
   }

   public void createSpatialRoom(){
      showLoading(false);
      if (isPublic){
         chatroomViewModel.createSpatial(this,roomName,false);
      }else {
         if (!TextUtils.isEmpty(encryption) && encryption.length() == 4){
            chatroomViewModel.createSpatial(this,roomName,true,encryption);
         }else {
            ToastTools.show(this,getString(R.string.chatroom_room_create_tips), Toast.LENGTH_LONG);
         }
      }
   }

   public void joinRoom(VRoomInfoBean data){
      ThreadManager.getInstance().runOnMainThread(new Runnable() {
         @Override
         public void run() {
            dismissLoading();
            ARouter.getInstance()
                    .build(RouterPath.ChatroomPath)
                    .withSerializable(RouterParams.KEY_CHATROOM_DETAILS_INFO, data)
                    .navigation();
            finishCreateActivity();
            finish();
         }
      });
   }


   @Override
   public void OnItemClick(int position, SoundSelectionBean bean) {
      adapter.setSelectedPosition(position);
      sound_effect = bean.getSoundSelectionType();
   }

   public void getSoundSelectionData(Context context) {
      List<SoundSelectionBean> soundSelectionList = RoomSoundSelectionConstructor.INSTANCE.builderSoundSelectionList(context, ConfigConstants.SoundSelection.Social_Chat);
      Log.e("ChatroomSoundSelectionActivity"," getData" + soundSelectionList.size());
      adapter.setData(soundSelectionList);
   }


   @Override
   public void onBackPress(View view) {
      onBackPressed();
   }

   @Override
   public void onClick(View view) {
      if (roomType == 0){
         switch (sound_effect){
            case ConfigConstants.SoundSelection.Karaoke:
               soundEffect = ConfigConstants.SoundSelectionText.Karaoke;
               break;
            case ConfigConstants.SoundSelection.Gaming_Buddy:
               soundEffect = ConfigConstants.SoundSelectionText.Gaming_Buddy;
               break;
            case ConfigConstants.SoundSelection.Professional_Broadcaster:
               soundEffect = ConfigConstants.SoundSelectionText.Professional_Broadcaster;
               break;
            default:
               soundEffect = ConfigConstants.SoundSelectionText.Social_Chat;
               break;
         }
         if (!FastClickTools.isFastClick(view,1000))
            createNormalRoom(false,soundEffect);
      }
//      else {
//         createSpatialRoom();
//      }

   }

   /**
    * 结束创建activity
    */
   protected void finishCreateActivity() {
      UserActivityLifecycleCallbacks lifecycleCallbacks = ChatroomApplication.getInstance().getLifecycleCallbacks();
      if(lifecycleCallbacks == null) {
         finish();
         return;
      }
      List<Activity> activities = lifecycleCallbacks.getActivityList();
      if(activities == null || activities.isEmpty()) {
         finish();
         return;
      }
      for(Activity activity : activities) {
         if(activity != lifecycleCallbacks.current() && activity instanceof ChatroomCreateActivity) {
            activity.finish();
         }
      }
   }

   static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
      private int mBottomOffset;

      public BottomOffsetDecoration(int bottomOffset) {
         mBottomOffset = bottomOffset;
      }

      @Override
      public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
         super.getItemOffsets(outRect, view, parent, state);
         int dataSize = state.getItemCount();
         int position = parent.getChildAdapterPosition(view);
         if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, 0, mBottomOffset);
         } else {
            outRect.set(0, 0, 0, 0);
         }

      }
   }
}
