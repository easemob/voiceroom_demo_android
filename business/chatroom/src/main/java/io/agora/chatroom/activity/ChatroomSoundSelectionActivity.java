package io.agora.chatroom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;
import java.util.Objects;

import io.agora.CallBack;
import io.agora.ValueCallBack;
import io.agora.baseui.BaseActivity;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.buddy.tool.ToastTools;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatRoom;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomSoundSelectionAdapter;
import io.agora.chatroom.general.repositories.ProfileManager;
import io.agora.chatroom.model.ChatroomViewModel;
import io.agora.config.ConfigConstants;
import io.agora.config.RouterParams;
import io.agora.config.RouterPath;
import io.agora.secnceui.bean.SoundSelectionBean;
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionConstructor;
import io.agora.secnceui.widget.titlebar.ChatroomTitleBar;
import io.agora.util.EMLog;
import manager.ChatroomConfigManager;
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
   private int roomType;
   private String sound_effect;

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
      recyclerView.setAdapter(adapter);
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
      chatroomViewModel = new ViewModelProvider(this).get(ChatroomViewModel.class);
      chatroomViewModel.getCreateObservable().observe(this,response -> {
         parseResource(response, new OnResourceParseCallback<VRoomInfoBean>() {
            @Override
            public void onSuccess(@Nullable VRoomInfoBean data) {
               if (null != data && null != data.getRoom()){
                  if (ChatClient.getInstance().isLoggedIn()){
                     joinRoom(data);
                  }else {
                     VRUserBean userinfo = ProfileManager.getInstance().getProfile();
                     Log.d("ChatroomCreateActivity","chat_uid: " + userinfo.getChat_uid());
                     Log.d("ChatroomCreateActivity","im_token: " + userinfo.getIm_token());
                     ChatroomConfigManager.getInstance().login(userinfo.getChat_uid(), userinfo.getIm_token(), new CallBack() {
                        @Override
                        public void onSuccess() {
                           joinRoom(data);
                        }

                        @Override
                        public void onError(int code, String desc) {
                           EMLog.e("ChatroomSoundSelectionActivity", "Login Fail code: "+code + " desc: " + desc);
                        }
                     });
                  }

               }
            }
         });
      });
   }

   public void createNormalRoom(boolean allow_free_join_mic,String sound_effect){
      if (isPublic){
         chatroomViewModel.createNormalRoom(this,roomName,false,allow_free_join_mic,sound_effect);
      }else {
         if (!TextUtils.isEmpty(encryption) && encryption.length() == 4){
            chatroomViewModel.createNormalRoom(this,roomName,true,encryption,allow_free_join_mic,sound_effect);
         }else {
            ToastTools.show(this,"4 Digit Password Required", Toast.LENGTH_LONG);
         }
      }
   }

   public void createSpatialRoom(){
      if (isPublic){
         chatroomViewModel.createSpatial(this,roomName,false);
      }else {
         if (!TextUtils.isEmpty(encryption) && encryption.length() == 4){
            chatroomViewModel.createSpatial(this,roomName,true,encryption);
         }else {
            ToastTools.show(this,"4 Digit Password Required", Toast.LENGTH_LONG);
         }
      }
   }

   public void joinRoom(VRoomInfoBean data){
      ChatroomConfigManager.getInstance().joinRoom(
              Objects.requireNonNull(data.getRoom()).getChatroom_id(), new ValueCallBack<ChatRoom>() {
         @Override
         public void onSuccess(ChatRoom chatRoom) {
            ARouter.getInstance()
                    .build(RouterPath.ChatroomPath)
                    .withSerializable(RouterParams.KEY_CHATROOM_DETAILS_INFO, data)
                    .navigation();
            finish();
         }

         @Override
         public void onError(int code, String desc) {
            EMLog.e("ChatroomSoundSelectionActivity", "joinRoom Fail code: "+code + " desc: " + desc);

         }
      });
   }


   @Override
   public void OnItemClick(int position, SoundSelectionBean bean) {
      adapter.setSelectedPosition(position);
      sound_effect = bean.getSoundName();
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
         if (TextUtils.isEmpty(sound_effect)){
            ToastTools.show(this,"Please select a sound effect",Toast.LENGTH_LONG);
            return;
         }
         createNormalRoom(false,sound_effect);
      }else {
         createSpatialRoom();
      }
   }
}
