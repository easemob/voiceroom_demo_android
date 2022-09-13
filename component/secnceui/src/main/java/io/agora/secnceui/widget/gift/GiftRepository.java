package io.agora.secnceui.widget.gift;

import android.content.Context;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.List;

import io.agora.secnceui.bean.GiftBean;

/**
 * 用于获取本地礼物信息
 */
public class GiftRepository {
    static int SIZE = 9;

    public static List<GiftBean> getDefaultGifts(Context context) {
        List<GiftBean> gifts = new ArrayList<>();
        GiftBean bean;
        for(int i = 1; i <= SIZE; i++){
            bean = new GiftBean();
            String name = "icon_gift_"+i;
            int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
            int nameId = context.getResources().getIdentifier("gift_default_name_" + i, "string", context.getPackageName());
            bean.setResource(resId);
            bean.setName(context.getString(nameId));
            bean.setId("gift_"+i);
            gifts.add(bean);
        }
        return gifts;
    }

    /**
     * 获取GiftBean
     * @param giftId
     * @return
     */
    public static GiftBean getGiftById(Context context,String giftId) {
        List<GiftBean> gifts = getDefaultGifts(context);
        for (GiftBean bean : gifts) {
            if(TextUtils.equals(bean.getId(), giftId)) {
                return bean;
            }
        }
        return null;
    }
}
