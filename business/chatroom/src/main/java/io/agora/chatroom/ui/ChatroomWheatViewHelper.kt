package io.agora.chatroom.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.secnceui.R
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.ui.wheat.ChatroomSeatManagerSheetDialog
import io.agora.secnceui.ui.common.CommonSheetAlertDialog

/**
 * @author create by zhangwei03
 */
object ChatroomWheatViewHelper {

    /**
     * 普通座位
     */
    fun createSeatClickListener(activity: FragmentActivity): OnItemClickListener<SeatInfoBean> {
        return object : OnItemClickListener<SeatInfoBean> {
            override fun onItemClick(data: SeatInfoBean, view: View, position: Int, viewType: Long) {
                super.onItemClick(data, view, position, viewType)
                // 普通用户,空闲位置
                if (data.wheatSeatType == WheatSeatType.Idle) {
                    CommonSheetAlertDialog()
                        .contentText(activity.getString(R.string.chatroom_request_speak))
                        .rightText(activity.getString(R.string.chatroom_confirm))
                        .leftText(activity.getString(R.string.chatroom_cancel))
                        .setOnClickListener(object : CommonSheetAlertDialog.OnClickBottomListener {
                            override fun onConfirmClick() {

                            }

                            override fun onCancelClick() {
                            }

                        })
                        .show(activity.supportFragmentManager, "SeatManagerSheetDialog11")
                } else {
                    ChatroomSeatManagerSheetDialog().apply {
                        arguments = Bundle().apply {
                            putSerializable(ChatroomSeatManagerSheetDialog.KEY_SEAT_INFO, data)
                        }
                        theme
                    }.show(activity.supportFragmentManager, "SeatManagerSheetDialog")
                }
            }
        }
    }

    /**
     * 机器人
     */
    fun createBotClickListener(activity: FragmentActivity): OnItemClickListener<SeatInfoBean> {
        return object : OnItemClickListener<SeatInfoBean> {
            override fun onItemClick(data: SeatInfoBean, view: View, position: Int, viewType: Long) {
                super.onItemClick(data, view, position, viewType)
                Toast.makeText(activity, "${data.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}