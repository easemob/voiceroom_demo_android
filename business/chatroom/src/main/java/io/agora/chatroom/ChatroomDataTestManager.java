package io.agora.chatroom;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import io.agora.ValueCallBack;
import io.agora.baseui.general.callback.ResultCallBack;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.bean.PageBean;
import io.agora.chatroom.general.repositories.BaseRepository;
import io.agora.chatroom.general.repositories.ChatroomListRepository;
import io.agora.chatroom.general.repositories.NetworkOnlyResource;
import io.agora.chatroom.model.ChatroomViewModel;
import tools.bean.VRoomBean;
import tools.bean.VRoomDetail;
import tools.bean.VRoomInfoBean;
import tools.bean.VRoomMicInfo;

public class ChatroomDataTestManager extends BaseRepository implements ChatroomListRepository {
    private static ChatroomDataTestManager mInstance;
    private Context mContext;
    private List<VRoomBean.RoomsBean> list = new ArrayList<>();
    private ArrayList<PageBean> data = new ArrayList<>();

    ChatroomDataTestManager(){}

    public static ChatroomDataTestManager getInstance() {
        if (mInstance == null) {
            synchronized (ChatroomDataTestManager.class) {
                if (mInstance == null) {
                    mInstance = new ChatroomDataTestManager();
                }
            }
        }
        return mInstance;
    }

    public void setRoomListData(Context context, ChatroomViewModel model){
        this.mContext = context;
        model.getRoomRepository().setListener(this);
    }

    public ArrayList<PageBean> getDefaultPageData(){
        data.clear();
        PageBean bean = new PageBean();
        bean.setRoom_type(0);
        bean.setTab_title(mContext.getString(R.string.tab_layout_chat_room));
        bean.setRoom_name(mContext.getString(R.string.room_create_chat_room));
        bean.setRoom_desc(mContext.getString(R.string.room_create_chat_room_desc));
        data.add(bean);
        PageBean bean1 = new PageBean();
        bean1.setRoom_type(1);
        bean1.setTab_title(mContext.getString(R.string.tab_layout_audio_room));
        bean1.setRoom_name(mContext.getString(R.string.room_create_3d_room));
        bean1.setRoom_desc(mContext.getString(R.string.room_create_3d_room_desc));
        data.add(bean1);
        PageBean bean2 = new PageBean();
        bean2.setRoom_type(2);
        bean2.setTab_title(mContext.getString(R.string.tab_layout_karaoke_room));
        bean2.setRoom_name(mContext.getString(R.string.room_create_ktv_room));
        bean2.setRoom_desc(mContext.getString(R.string.room_create_ktv_room_desc));
        data.add(bean2);
        return data;
    }

    @Override
    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomList(int type) {
        return new NetworkOnlyResource<List<VRoomBean.RoomsBean>>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<List<VRoomBean.RoomsBean>>> callBack) {
                getDataList(type, new ValueCallBack<List<VRoomBean.RoomsBean>>() {
                    @Override
                    public void onSuccess(List<VRoomBean.RoomsBean> value) {
                        callBack.onSuccess(createLiveData(value));
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getAllRoomList() {
        return new NetworkOnlyResource<List<VRoomBean.RoomsBean>>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<List<VRoomBean.RoomsBean>>> callBack) {
                getDataList(new ValueCallBack<List<VRoomBean.RoomsBean>>() {
                    @Override
                    public void onSuccess(List<VRoomBean.RoomsBean> value) {
                        callBack.onSuccess(createLiveData(value));
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<VRoomInfoBean>> getRoomInfo(String roomId) {
        return new NetworkOnlyResource<VRoomInfoBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRoomInfoBean>> callBack) {
                getRoomInfo(roomId, new ValueCallBack<VRoomInfoBean>() {
                    @Override
                    public void onSuccess(VRoomInfoBean value) {
                        callBack.onSuccess(createLiveData(value));
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        }.asLiveData();
    }

    private void getDataList( ValueCallBack<List<VRoomBean.RoomsBean>> callBack) {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.putOpt("grant_type", "client_credentials");
//            requestBody.putOpt("client_id", "YXA6t0NJ4KAjEeiqc3HubFOvIQ");
//            requestBody.putOpt("client_secret", "YXA6k55FCaCNhwpC6-Dqdt8n5mbEb_w");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        new VRHttpClientManager.Builder(mContext)
//                .setUrl("https://a1.easemob.com/1107180814253417/myeasuichatdemo/token")
//                .setHeaders(headers)
//                .setParams(requestBody.toString())
//                .setRequestMethod(Method_POST)
//                .asyncExecute(new VRHttpCallback() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Log.e("HttpClient success1: ",result);
//
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        Log.e("HttpClient onError: ",code + " msg: " + msg);
//                    }
//                });
        List<VRoomBean.RoomsBean> list = new ArrayList<>();
        VRoomBean.RoomsBean bean = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean.setName("omg2");
        bean.setName("apex-chat-room");
        bean.setIs_private(true);
        bean.setType(1);
        bean.setOwner(ownerBean);
        list.add(bean);

        VRoomBean.RoomsBean bean1 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean1 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean1.setName("omg3");
        bean1.setName("apex-chat-room1");
        bean1.setIs_private(false);
        bean1.setType(1);
        bean1.setOwner(ownerBean1);
        list.add(bean1);

        VRoomBean.RoomsBean bean2 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean2 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean2.setName("omg4");
        bean2.setName("apex-chat-room2 SA-08729-001 2304934-3434");
        bean2.setIs_private(true);
        bean2.setType(2);
        bean2.setOwner(ownerBean2);
        list.add(bean2);

        VRoomBean.RoomsBean bean3 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean3 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean3.setName("omg5");
        bean3.setName("apex-chat-room3");
        bean3.setIs_private(false);
        bean3.setType(2);
        bean3.setOwner(ownerBean3);
        list.add(bean3);

        VRoomBean.RoomsBean bean4 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean4 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean4.setName("omg6");
        bean4.setName("apex-chat-room4");
        bean4.setIs_private(true);
        bean4.setType(3);
        bean4.setOwner(ownerBean4);
        list.add(bean4);

        VRoomBean.RoomsBean bean5 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean5 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean5.setName("omg7");
        bean5.setName("apex-chat-room5");
        bean5.setIs_private(false);
        bean5.setType(3);
        bean5.setOwner(ownerBean5);
        list.add(bean5);
        Log.e("getdata","data_all_size: "+list.size());
        callBack.onSuccess(list);
    }

    private void getDataList(int type, ValueCallBack<List<VRoomBean.RoomsBean>> callBack) {
        list.clear();
        List<VRoomBean.RoomsBean> typeList = new ArrayList<>();
        VRoomBean.RoomsBean bean = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean.setName("omg2");
        bean.setName("apex-chat-room");
        bean.setIs_private(true);
        bean.setType(1);
        bean.setOwner(ownerBean);
        list.add(bean);

        VRoomBean.RoomsBean bean1 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean1 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean1.setName("omg3");
        bean1.setName("apex-chat-room1");
        bean1.setIs_private(false);
        bean1.setType(1);
        bean1.setOwner(ownerBean1);
        list.add(bean1);

        VRoomBean.RoomsBean bean2 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean2 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean2.setName("omg4");
        bean2.setName("apex-chat-room2 SA-08729-001 2304934-3434");
        bean2.setIs_private(true);
        bean2.setType(2);
        bean2.setOwner(ownerBean2);
        list.add(bean2);

        VRoomBean.RoomsBean bean3 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean3 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean3.setName("omg5");
        bean3.setName("apex-chat-room3");
        bean3.setIs_private(false);
        bean3.setType(2);
        bean3.setOwner(ownerBean3);
        list.add(bean3);

        VRoomBean.RoomsBean bean4 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean4 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean4.setName("omg6");
        bean4.setName("apex-chat-room4");
        bean4.setIs_private(true);
        bean4.setType(3);
        bean4.setOwner(ownerBean4);
        list.add(bean4);

        VRoomBean.RoomsBean bean5 = new VRoomBean.RoomsBean();
        VRoomBean.RoomsBean.OwnerBean ownerBean5 =  new VRoomBean.RoomsBean.OwnerBean();
        ownerBean5.setName("omg7");
        bean5.setName("apex-chat-room5");
        bean5.setIs_private(false);
        bean5.setType(3);
        bean5.setOwner(ownerBean5);
        list.add(bean5);

        for (VRoomBean.RoomsBean roomsBean : list) {
            if (roomsBean.getType() == type){
                typeList.add(roomsBean);
            }
        }
        Log.e("getdata:","data_type_size: "+typeList.size());
        callBack.onSuccess(typeList);
    }

    private void getRoomInfo(String roomId, ValueCallBack<VRoomInfoBean> callBack) {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.putOpt("room", roomId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        new VRHttpClientManager.Builder(mContext)
//                .setUrl("https://a1.easemob.com/voice/room/" + roomId)
//                .setHeaders(headers)
//                .setParams(requestBody.toString())
//                .setRequestMethod(Method_GET)
//                .asyncExecute(new VRHttpCallback() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Log.e("HttpClient success1: ", result);
//                        callBack.onSuccess(GsonTools.toBean(result, VRoomInfoBean.class));
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        Log.e("HttpClient onError: ", code + " msg: " + msg);
//                        callBack.onError(code, msg);
//                    }
//                });

        List<VRoomMicInfo> micInfoList = new ArrayList<>();
        VRoomDetail room = new VRoomDetail();
        VRoomInfoBean vRoomInfoBean = new VRoomInfoBean(micInfoList, room);
        callBack.onSuccess(vRoomInfoBean);
    }
}
