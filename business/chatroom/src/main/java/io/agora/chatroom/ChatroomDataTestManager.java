package io.agora.chatroom;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import io.agora.chatroom.bean.PageBean;
import io.agora.chatroom.general.repositories.BaseRepository;
import io.agora.chatroom.model.ChatroomViewModel;
import tools.bean.VRoomBean;

public class ChatroomDataTestManager extends BaseRepository {
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

}
