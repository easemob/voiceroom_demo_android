package com.easemob.chatroom.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.easemob.baseui.general.net.Resource;
import com.easemob.chatroom.general.livedatas.SingleSourceLiveData;
import com.easemob.chatroom.general.repositories.ChatroomHandsRepository;
import tools.bean.VRMicListBean;

public class ChatroomRaisedViewModel extends AndroidViewModel {
   private ChatroomHandsRepository mRepository;
   private SingleSourceLiveData<Resource<VRMicListBean>> raisedObservable;

   public ChatroomRaisedViewModel(@NonNull Application application) {
      super(application);
      mRepository = new ChatroomHandsRepository();
      raisedObservable = new SingleSourceLiveData<>();
   }

   public LiveData<Resource<VRMicListBean>> getRaisedObservable(){
      return raisedObservable;
   }

   public void getRaisedList(Context context,String roomId,int pageSize,String cursor){
      raisedObservable.setSource(mRepository.getRaisedList(context,roomId,pageSize,cursor));
   }


}
