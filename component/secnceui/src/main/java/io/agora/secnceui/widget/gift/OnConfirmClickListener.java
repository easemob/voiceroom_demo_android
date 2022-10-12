package io.agora.secnceui.widget.gift;

import android.view.View;

import io.agora.secnceui.bean.GiftBean;

public interface OnConfirmClickListener {
    void onConfirmClick(View view, Object bean);
    void onFirstItem(GiftBean firstBean);
}
