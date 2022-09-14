package io.agora.chatroom.general.repositories;



import androidx.lifecycle.LiveData;

import java.util.List;

import io.agora.baseui.general.net.Resource;
import tools.bean.VRoomBean;

public class RoomRepository extends BaseRepository {
    public RoomListRepository listener;
    private static RoomRepository mInstance;

    public static RoomRepository getInstance() {
        if (mInstance == null) {
            synchronized (RoomRepository.class) {
                if (mInstance == null) {
                    mInstance = new RoomRepository();
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

    public void setListener(RoomListRepository roomListRepository){
        this.listener = roomListRepository;
    }

}
