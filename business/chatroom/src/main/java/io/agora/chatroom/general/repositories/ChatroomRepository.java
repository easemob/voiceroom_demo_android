package io.agora.chatroom.general.repositories;



import androidx.lifecycle.LiveData;

import java.util.List;

import io.agora.baseui.general.net.Resource;
import tools.bean.VRoomBean;

public class ChatroomRepository extends BaseRepository {
    public ChatroomListRepository listener;
    private static ChatroomRepository mInstance;

    public static ChatroomRepository getInstance() {
        if (mInstance == null) {
            synchronized (ChatroomRepository.class) {
                if (mInstance == null) {
                    mInstance = new ChatroomRepository();
                }
            }
        }
        return mInstance;
    }

    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomList(int type) {
        return  (listener!=null)? listener.getRoomList(type):null;
    }


    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getAllRoomList() {
        return (listener!=null)?listener.getAllRoomList():null;
    }

    public void setListener(ChatroomListRepository chatroomListRepository){
        this.listener = chatroomListRepository;
    }

}
