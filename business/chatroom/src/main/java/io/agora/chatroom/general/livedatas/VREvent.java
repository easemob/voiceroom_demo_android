package io.agora.chatroom.general.livedatas;


import java.io.Serializable;


/**
 *
 */
public class VREvent implements Serializable {
    public boolean refresh;
    public String event;
    public TYPE type;
    public String message;

    public VREvent() {}

    public VREvent(String event, TYPE type, boolean refresh) {
        this.refresh = refresh;
        this.event = event;
        this.type = type;
    }

    public VREvent(String event, TYPE type) {
        this.refresh = true;
        this.event = event;
        this.type = type;
    }

    public static VREvent create(String event, TYPE type) {
        return new VREvent(event, type);
    }

    public static VREvent create(String event, TYPE type, String message) {
        VREvent easeEvent = new VREvent(event, type);
        easeEvent.message = message;
        return easeEvent;
    }

    public static VREvent create(String event, TYPE type, boolean refresh) {
        return new VREvent(event, type, refresh);
    }

    public boolean isMessageChange() {
        return type == TYPE.MESSAGE;
    }

    public boolean isChatRoomLeave() {
        return type == TYPE.CHAT_ROOM_LEAVE;
    }

    public boolean isNotifyChange() {
        return type == TYPE.NOTIFY;
    }

    public boolean isVRDataChange() {
        return type == TYPE.VR_DATA_CHANGE;
    }

    public enum TYPE {
       MESSAGE, NOTIFY, CHAT_ROOM, CHAT_ROOM_LEAVE,VR_DATA_CHANGE
    }
}
