package io.agora.chatroom.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.general.livedatas.SingleSourceLiveData;
import io.agora.chatroom.general.repositories.ChatroomRepository;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;


public class ChatroomViewModel extends AndroidViewModel {
    private ChatroomRepository mRepository;
    private SingleSourceLiveData<Resource<List<VRoomBean.RoomsBean>>> roomObservable;
    private SingleSourceLiveData<Resource<Boolean>> joinObservable;
    private SingleSourceLiveData<Resource<VRoomInfoBean>> roomDetailsObservable;
    private SingleSourceLiveData<Resource<VRoomInfoBean>> createObservable;

    public ChatroomViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ChatroomRepository();
        roomObservable = new SingleSourceLiveData<>();
        joinObservable = new SingleSourceLiveData<>();
        roomDetailsObservable = new SingleSourceLiveData<>();
        createObservable = new SingleSourceLiveData<>();
    }

    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomObservable() {
        return roomObservable;
    }

    public LiveData<Resource<VRoomInfoBean>> getCreateObservable() {
        return createObservable;
    }

    public LiveData<Resource<Boolean>> getJoinObservable() {
        return joinObservable;
    }

    public LiveData<Resource<VRoomInfoBean>> getRoomDetailObservable() {
        return roomDetailsObservable;
    }

    public void getDataList(Context context,int pageSize,int type){
        roomObservable.setSource(mRepository.getRoomList(context,pageSize,type));
    }

    public void getDetails(Context context, String roomId) {
        roomDetailsObservable.setSource(mRepository.getRoomInfo(context, roomId));
    }

    public void joinRoom(Context context,String roomId){
        joinObservable.setSource(mRepository.joinRoom(context,roomId,""));
    }

    public void joinRoom(Context context,String roomId,String password){
        joinObservable.setSource(mRepository.joinRoom(context,roomId,password));
    }

    public void createRoom(Context context,String name,boolean is_privacy,String password,
                           int type,boolean allow_free_join_mic,String sound_effect){
        createObservable.setSource(mRepository.createRoom(context,name,is_privacy,password,type,
                allow_free_join_mic,sound_effect));
    }

    public void createRoom(Context context,String name,boolean is_privacy,
                           int type,boolean allow_free_join_mic,String sound_effect){
        createObservable.setSource(mRepository.createRoom(context,name,is_privacy,"",type,
                allow_free_join_mic,sound_effect));
    }

    /**
     * 清理注册信息
     */
    public void clearRegisterInfo() {
        roomObservable.call();
        joinObservable.call();
        createObservable.call();
    }
}
