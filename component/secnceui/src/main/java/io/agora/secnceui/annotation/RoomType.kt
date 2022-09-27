package io.agora.secnceui.annotation;

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(RoomType.CommonRoom, RoomType.SpatialRoom)
annotation class RoomType {
    companion object {
        const val CommonRoom = ConfigConstants.Common_Chatroom

        const val SpatialRoom = ConfigConstants.Spatial_Chatroom
    }
}
