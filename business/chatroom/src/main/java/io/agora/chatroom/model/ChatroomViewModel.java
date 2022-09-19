package io.agora.chatroom.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.agora.ValueCallBack;
import io.agora.baseui.general.net.Resource;
import io.agora.buddy.tool.LogToolsKt;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatRoom;
import io.agora.chatroom.controller.RtcRoomController;
import io.agora.chatroom.general.constructor.RoomInfoConstructor;
import io.agora.chatroom.general.livedatas.SingleSourceLiveData;
import io.agora.chatroom.general.repositories.ChatroomRepository;
import io.agora.chatroom.general.repositories.ProfileManager;
import tools.DefaultValueCallBack;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;

public class ChatroomViewModel extends AndroidViewModel {

    private static final String TAG = "ChatroomViewModel";

    private ChatroomRepository mRepository;
    private SingleSourceLiveData<Resource<List<VRoomBean.RoomsBean>>> roomObservable;
    private SingleSourceLiveData<Resource<Boolean>> joinObservable;
    private SingleSourceLiveData<Resource<VRoomInfoBean>> roomDetailsObservable;
    private SingleSourceLiveData<Resource<Boolean>> leaveObservable;

    private final AtomicBoolean joinRtcChannel = new AtomicBoolean(false);
    private final AtomicBoolean joinImRoom = new AtomicBoolean(false);

    public ChatroomViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ChatroomRepository();
        roomObservable = new SingleSourceLiveData<>();
        joinObservable = new SingleSourceLiveData<>();
        roomDetailsObservable = new SingleSourceLiveData<>();
        leaveObservable = new SingleSourceLiveData<>();
    }

    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomObservable() {
        return roomObservable;
    }

    public LiveData<Resource<Boolean>> getJoinObservable() {
        return joinObservable;
    }

    public LiveData<Resource<VRoomInfoBean>> getRoomDetailObservable() {
        return roomDetailsObservable;
    }

    public LiveData<Resource<Boolean>> getLeaveObservable() {
        return leaveObservable;
    }

    public void getDataList(Context context, int pageSize, int type) {
        roomObservable.setSource(mRepository.getRoomList(context, pageSize, type));
    }

    public void getDetails(Context context, String roomId) {
        roomDetailsObservable.setSource(mRepository.getRoomInfo(context, roomId));
    }

    public void joinRoom(Context context, String roomId) {
        if (joinRtcChannel.get() && joinImRoom.get()) {
            joinObservable.setSource(mRepository.joinRoom(context, roomId, ""));
        }
    }

    public void joinRoom(Context context, String roomId, String password) {
        joinObservable.setSource(mRepository.joinRoom(context, roomId, password));
    }

    public void leaveRoom(Context context, String roomId) {
        joinObservable.setSource(mRepository.leaveRoom(context, roomId));
    }

    public void initSdkJoin(VRoomBean.RoomsBean roomBean) {
        joinRtcChannel.set(false);
        joinImRoom.set(false);
        RtcRoomController.get().initMain(getApplication());
        RtcRoomController.get().joinChannel(roomBean.getChannel_id(),
                ProfileManager.getInstance().getProfile().getRtc_uid(),
                RoomInfoConstructor.isOwner(roomBean),
                new DefaultValueCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean value) {
                        LogToolsKt.logE("rtc  joinChannel onSuccess ", TAG);
                        joinRtcChannel.set(true);
                        joinRoom(getApplication(), roomBean.getRoom_id());
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        LogToolsKt.logE("rtc  joinChannel onError " + error + "  " + errorMsg, TAG);
                    }
                }
        );
        ChatClient.getInstance().chatroomManager().joinChatRoom(roomBean.getChatroom_id(), new ValueCallBack<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom value) {
                LogToolsKt.logE("im  joinChatRoom onSuccess ", TAG);
                joinImRoom.set(true);
                joinRoom(getApplication(), roomBean.getRoom_id());
            }

            @Override
            public void onError(int error, String errorMsg) {
                LogToolsKt.logE("im  joinChatRoom onError " + error + "  " + errorMsg, TAG);
            }
        });
    }

    /**
     * 清理注册信息
     */
    public void clearRegisterInfo() {
        roomObservable.call();
        joinObservable.call();
    }
}
