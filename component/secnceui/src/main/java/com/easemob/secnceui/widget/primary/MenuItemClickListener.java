package com.easemob.secnceui.widget.primary;

import android.view.View;

public interface MenuItemClickListener {
    void onChatExtendMenuItemClick(int itemId, View view);
    void onInputViewFocusChange(boolean focus);
    void onInputLayoutClick();
    void onEmojiClick(boolean isShow);
    void onSendMessage(String content);
}
