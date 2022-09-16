package http;

import android.text.TextUtils;
import android.util.Log;

public class VRRequestApi {
    VRRequestApi(){}
    public static VRRequestApi mInstance;
    private final String BASE_URL = "https://a1-test-voiceroom.easemob.com";
    private final String BASE_ROOM = "/%1$d";
    private final String LOGIN = "/user/login/device";
    private final String ROOM_LIST = "/voice/room/list?limit=%1$s";
    private final String BASE_MEMBERS = "/voice/room/%1$s/members";
    private final String BASE_MIC = "/%1$s/mic";
    private final String CREATE_ROOM = "/create";
    private final String ROOM_DETAILS = "/voice/room/%1$s";
    private final String FETCH_ROOM_MEMBERS = "%1$s/members/list?limit=%2$s";
    private final String JOIN_ROOM = "/join";
    private final String LEAVE_ROOM = "/leave";
    private final String KICK_USER = "/kick";
    private final String FETCH_GIFT_CONTRIBUTE = "/%1$s/gift/list?limit=%2$s";
    private final String GIFT_TO = "/%1$s/gift/add";
    private final String FETCH_APPLY_MEMBERS = "/%1$s/mic/apply?limit=%2$s";
    private final String MIC_APPLY = "/apply";
    private final String MIC_CLOSE = "/close";
    private final String MIC_LEAVE = "/leave";
    private final String MIC_MUTE = "/mute";
    private final String MIC_EXCHANGE = "/exchange";
    private final String MIC_KICK = "/kick";
    private final String MIC_LOCK = "/lock";
    private final String MIC_INVITE = "/invite";

    public static VRRequestApi get() {
        if(mInstance == null) {
            synchronized (VRRequestApi.class) {
                if(mInstance == null) {
                    mInstance = new VRRequestApi();
                }
            }
        }
        return mInstance;
    }

    public String createRoom(){
        return CREATE_ROOM;
    }

    public String fetchRoomInfo(String roomId) {
        return String.format(ROOM_DETAILS, roomId);
    }

    public String deleteRoom(String roomId){
        return CREATE_ROOM;
    }

    public String modifyRoomInfo(String roomId){
        return CREATE_ROOM;
    }

    public String fetchRoomMembers(String roomId,String cursor,int limit){
        String api = String.format(FETCH_ROOM_MEMBERS,roomId,limit);
        if (!TextUtils.isEmpty(cursor)){
            api = api + "&cursor=" + cursor;
        }
        return api;
    }

    public String joinRoom(String roomId){
        Log.e("joinRoom","url: "+BASE_URL + String.format(BASE_MEMBERS,roomId) + JOIN_ROOM);
        return BASE_URL + String.format(BASE_MEMBERS,roomId) + JOIN_ROOM;
    }

    public String leaveRoom(String roomId){
        return String.format(BASE_MEMBERS,roomId) + LEAVE_ROOM;
    }

    public String kickUser(String roomId){
        return String.format(BASE_MEMBERS,roomId) + KICK_USER;
    }

    public String fetchGiftContribute(String roomId,String cursor,int limit){
        String api = String.format(FETCH_GIFT_CONTRIBUTE,roomId,limit);
        if (!TextUtils.isEmpty(cursor)){
            api = api + "&cursor=" + cursor;
        }
        return api;
    }

    public String giftTo(String roomId){
        return String.format(GIFT_TO,roomId);
    }

    public String fetchApplyMembers(String roomId,String cursor,int limit){
        String api = String.format(FETCH_APPLY_MEMBERS,roomId,limit);
        if (!TextUtils.isEmpty(cursor)){
            api = api + "&cursor=" + cursor;
        }
        return api;
    }

    public String submitApply(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_APPLY;
    }

    public String cancelApply(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_APPLY;
    }

    public String fetchMicsInfo(String roomId){
        return String.format(BASE_MIC,roomId);
    }

    public String closeMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_CLOSE;
    }

    public String cancelCloseMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_CLOSE;
    }

    public String leaveMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_LEAVE;
    }

    public String muteMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_MUTE;
    }

    public String unMuteMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_MUTE;
    }

    public String exchangeMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_EXCHANGE;
    }

    public String kickMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_KICK;
    }

    public String lockMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_LOCK;
    }

    public String unlockMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_LOCK;
    }

    public String inviteUserToMic(String roomId){
        return String.format(BASE_MIC,roomId) + MIC_INVITE;
    }

    public String login(){
        return BASE_URL+LOGIN;
    }

    public String getRoomList(String cursor,int limit,int type){
        String api = BASE_URL + String.format(ROOM_LIST,limit);
        if (type != -1){
            api = api + "&type=" + type;
        }
        if (!TextUtils.isEmpty(cursor)){
            api = api + "&cursor=" + cursor;
        }

        return api;
    }
}
