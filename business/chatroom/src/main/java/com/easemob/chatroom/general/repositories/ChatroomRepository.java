package com.easemob.chatroom.general.repositories;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.easemob.baseui.general.callback.ResultCallBack;
import com.easemob.baseui.general.net.Resource;
import com.easemob.chatroom.general.net.ChatroomHttpManager;
import kotlin.Pair;
import tools.ValueCallBack;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;

public class ChatroomRepository extends BaseRepository {

    public LiveData<Resource<VRoomBean>> getRoomList(Context context, int pageSize, int type, String cursor) {
        return new NetworkOnlyResource<VRoomBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRoomBean>> callBack) {
                ChatroomHttpManager.getInstance().getRoomFromServer(pageSize, type, cursor, new ValueCallBack<VRoomBean>() {
                    @Override
                    public void onSuccess(VRoomBean bean) {
                        callBack.onSuccess(createLiveData(bean));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code, desc);
                    }
                });
            }
        }.asLiveData();
    }


    public LiveData<Resource<VRoomInfoBean>> getRoomInfo(Context context, String roomId) {
        return new NetworkOnlyResource<VRoomInfoBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRoomInfoBean>> callBack) {
                ChatroomHttpManager.getInstance().getRoomDetails(roomId, new ValueCallBack<VRoomInfoBean>() {
                    @Override
                    public void onSuccess(VRoomInfoBean data) {
                        callBack.onSuccess(createLiveData(data));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code, desc);
                    }
                });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> joinRoom(Context context, String roomId, String password) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                ChatroomHttpManager.getInstance().joinRoom(roomId, password, new ValueCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        callBack.onSuccess(createLiveData(data));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code, desc);
                    }
                });
            }

        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> leaveRoom(Context context, String roomId) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                ChatroomHttpManager.getInstance().leaveRoom(roomId, new ValueCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        callBack.onSuccess(createLiveData(data));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code, desc);
                    }
                });
            }
        }.asLiveData();
    }

    public LiveData<Resource<VRoomInfoBean>> createRoom(Context context, String name, boolean is_privacy, String password,
                                                        int type, boolean allow_free_join_mic, String sound_effect) {
        return new NetworkOnlyResource<VRoomInfoBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRoomInfoBean>> callBack) {
                ChatroomHttpManager.getInstance().createRoom(name, is_privacy, password, type,
                        allow_free_join_mic, sound_effect, new ValueCallBack<VRoomInfoBean>() {
                            @Override
                            public void onSuccess(VRoomInfoBean var1) {
                                callBack.onSuccess(createLiveData(var1));
                            }

                            @Override
                            public void onError(int code, String desc) {
                                callBack.onError(code, desc);
                            }
                        });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> updateRoomInfo(Context context, String roomId, String name, String announcement, Boolean isPrivate,
                                                      String password, Boolean useRobot, Boolean allowedFreeJoinMic, Integer robotVolume) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                ChatroomHttpManager.getInstance().updateRoomInfo(roomId, name, announcement, isPrivate,
                        password, useRobot, allowedFreeJoinMic, robotVolume, new ValueCallBack<Boolean>() {
                            @Override
                            public void onSuccess(Boolean var1) {
                                callBack.onSuccess(createLiveData(var1));
                            }

                            @Override
                            public void onError(int code, String desc) {
                                callBack.onError(code, desc);
                            }
                        });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> activeBot(Context context, String roomId, Boolean useRobot) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                ChatroomHttpManager.getInstance().updateRoomInfo(roomId, null, null, null,
                        null, useRobot, null, null, new ValueCallBack<Boolean>() {
                            @Override
                            public void onSuccess(Boolean var1) {
                                callBack.onSuccess(createLiveData(var1));
                            }

                            @Override
                            public void onError(int code, String desc) {
                                callBack.onError(code, desc);
                            }
                        });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Pair<Integer, Boolean>>> changeRobotVolume(Context context, String roomId, Integer robotVolume) {
        return new NetworkOnlyResource<Pair<Integer, Boolean>>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Pair<Integer, Boolean>>> callBack) {
                ChatroomHttpManager.getInstance().updateRoomInfo(roomId, null, null, null,
                        null, null, null, robotVolume, new ValueCallBack<Boolean>() {
                            @Override
                            public void onSuccess(Boolean var1) {
                                callBack.onSuccess(createLiveData(new Pair(robotVolume, var1)));
                            }

                            @Override
                            public void onError(int code, String desc) {
                                callBack.onError(code, desc);
                            }
                        });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> updateRoomNotice(Context context, String roomId, String notice) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                ChatroomHttpManager.getInstance().updateRoomInfo(roomId, null, notice, null,
                        null, null, null, null, new ValueCallBack<Boolean>() {
                            @Override
                            public void onSuccess(Boolean var1) {
                                callBack.onSuccess(createLiveData(var1));
                            }

                            @Override
                            public void onError(int code, String desc) {
                                callBack.onError(code, desc);
                            }
                        });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> checkPassword(Context context, String roomId, String password) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                ChatroomHttpManager.getInstance().checkPassword(roomId, password, new ValueCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean var1) {
                        callBack.onSuccess(createLiveData(var1));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code,desc);
                    }
                });
            }

        }.asLiveData();
    }
}
