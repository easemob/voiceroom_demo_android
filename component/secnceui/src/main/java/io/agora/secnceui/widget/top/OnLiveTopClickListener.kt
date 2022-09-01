package io.agora.secnceui.widget.top

import android.view.View

interface OnLiveTopClickListener {

    fun onClickView(view: View, @ChatroomLiveTopView.ClickAction action: Int)
}