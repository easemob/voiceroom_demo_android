package com.easemob.chatroom.general.repositories;

import android.content.Context;

import com.easemob.chatroom.bean.PageBean;

import java.util.ArrayList;

import com.easemob.chatroom.R;

public class PageRepository extends BaseRepository {
    private static PageRepository mInstance;
    private ArrayList<PageBean> data = new ArrayList<>();

    PageRepository(){}

    public static PageRepository getInstance() {
        if (mInstance == null) {
            synchronized (PageRepository.class) {
                if (mInstance == null) {
                    mInstance = new PageRepository();
                }
            }
        }
        return mInstance;
    }

    public ArrayList<PageBean> getDefaultPageData(Context mContext){
        data.clear();
        PageBean bean = new PageBean();
        bean.setRoom_type(0);
        bean.setTab_title(mContext.getString(R.string.chatroom_tab_layout_chat_room));
        bean.setRoom_name(mContext.getString(R.string.chatroom_create_chat_room_tag));
        bean.setRoom_desc(mContext.getString(R.string.chatroom_create_chat_room_desc));
        data.add(bean);
//        PageBean bean1 = new PageBean();
//        bean1.setRoom_type(1);
//        bean1.setTab_title(mContext.getString(R.string.chatroom_tab_layout_audio_room));
//        bean1.setRoom_name(mContext.getString(R.string.chatroom_create_3d_room));
//        bean1.setRoom_desc(mContext.getString(R.string.chatroom_create_3d_room_desc));
//        data.add(bean1);
        return data;
    }

}
