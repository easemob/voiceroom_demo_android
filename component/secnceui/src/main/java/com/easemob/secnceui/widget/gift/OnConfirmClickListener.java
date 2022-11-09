package com.easemob.secnceui.widget.gift;

import android.view.View;

import com.easemob.secnceui.bean.GiftBean;

public interface OnConfirmClickListener {
    void onConfirmClick(View view, Object bean);
    void onFirstItem(GiftBean firstBean);
}
