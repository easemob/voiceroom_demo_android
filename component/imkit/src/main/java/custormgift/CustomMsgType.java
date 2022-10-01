package custormgift;

import android.text.TextUtils;

public enum CustomMsgType {
    /**
     * 礼物消息
     */
    CHATROOM_GIFT("chatroom_gift"),

    /**
     * 点赞
     */
    CHATROOM_PRAISE("chatroom_praise"),

    /**
     * 普通消息
     */
    CHATROOM_NORMAL("chatroom_normal"),

    /**
     * 申请消息
     */
    CHATROOM_APPLY_SITE("chatroom_applySiteNotify"),

    /**
     * 拒绝申请消息
     */
    CHATROOM_DECLINE_APPLY("chatroom_applyRefusedNotify"),

    /**
     * 邀请消息
     */
    CHATROOM_INVITE_SITE("chatroom_inviteSiteNotify"),

    /**
     * 系统消息
     */
    CHATROOM_SYSTEM("chatroom_system"),
    ;

    private String name;
    private CustomMsgType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CustomMsgType fromName(String name) {
        for (CustomMsgType type : CustomMsgType.values()) {
            if(TextUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return null;
    }
}
