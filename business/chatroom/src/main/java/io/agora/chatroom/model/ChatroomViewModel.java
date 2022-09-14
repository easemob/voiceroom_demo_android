package io.agora.chatroom.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.general.livedatas.SingleSourceLiveData;
import io.agora.chatroom.general.repositories.ChatroomRepository;
import tools.bean.VRoomBean;


public class ChatroomViewModel extends AndroidViewModel {
    private ChatroomRepository mRepository;
    private SingleSourceLiveData<Resource<List<VRoomBean.RoomsBean>>> roomObservable;

    public ChatroomViewModel(@NonNull Application application) {
        super(application);
        mRepository = ChatroomRepository.getInstance();
        roomObservable = new SingleSourceLiveData<>();
    }

    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomObservable() {
        return roomObservable;
    }


    public void getDataList(int type){
        roomObservable.setSource(mRepository.getRoomList(type));
    }

    public void getAllDataList(){
        roomObservable.setSource(mRepository.getAllRoomList());
    }

    public ChatroomRepository getRoomRepository(){
        return mRepository;
    }

    /**
     * 清理注册信息
     */
    public void clearRegisterInfo() {
        roomObservable.setValue(null);
    }
}
