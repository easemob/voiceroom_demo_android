package com.easemob.chatroom.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.easemob.baseui.general.net.Resource;
import com.easemob.chatroom.general.livedatas.SingleSourceLiveData;
import com.easemob.chatroom.general.repositories.ChatroomHandsRepository;
import tools.bean.VRoomUserBean;

public class ChatroomInviteViewModel extends AndroidViewModel {
   private ChatroomHandsRepository mRepository;
   private SingleSourceLiveData<Resource<VRoomUserBean>> inviteObservable;

   public ChatroomInviteViewModel(@NonNull Application application) {
      super(application);
      mRepository = new ChatroomHandsRepository();
      inviteObservable = new SingleSourceLiveData<>();
   }

   public LiveData<Resource<VRoomUserBean>> getInviteObservable(){
      return inviteObservable;
   }


   public void getInviteList(Context context,String roomId,int pageSize,String cursor){
      inviteObservable.setSource(mRepository.getInvitedList(context,roomId,pageSize,cursor));
   }

}
