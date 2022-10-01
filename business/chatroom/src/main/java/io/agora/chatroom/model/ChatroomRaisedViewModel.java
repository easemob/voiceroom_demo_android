package io.agora.chatroom.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.general.livedatas.SingleSourceLiveData;
import io.agora.chatroom.general.repositories.ChatroomHandsRepository;
import tools.bean.VRMicListBean;
import tools.bean.VRoomUserBean;

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
