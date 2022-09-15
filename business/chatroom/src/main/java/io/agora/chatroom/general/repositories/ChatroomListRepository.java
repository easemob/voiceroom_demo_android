package io.agora.chatroom.general.repositories;


import androidx.lifecycle.LiveData;

import java.util.List;

import io.agora.baseui.general.net.Resource;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;

public interface ChatroomListRepository {
    LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomList(int type);
    LiveData<Resource<List<VRoomBean.RoomsBean>>> getAllRoomList();
    LiveData<Resource<VRoomInfoBean>> getRoomInfo(String roomId);
}
