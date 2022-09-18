package io.agora.secnceui.ui.micmanger

import io.agora.secnceui.bean.MicInfoBean

/**
 * @author create by zhangwei03
 */
class RoomMicMangerImpl :IRoomMicManager{

    override fun micMap(): Map<Int, MicInfoBean> {
        return mutableMapOf()
    }

    override fun changeMic(oldIndex: Int, newIndex: Int) {

    }

    override fun muteMic(index: Int, muted: Boolean) {

    }

    override fun openMic(index: Int) {

    }

    override fun closeMic(index: Int) {

    }
}