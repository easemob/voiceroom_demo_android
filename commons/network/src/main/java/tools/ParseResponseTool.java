package tools;

import com.google.gson.Gson;

import tools.bean.VRGiftBean;
import tools.bean.VRMicBean;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;
import tools.bean.VRoomUserBean;

public class ParseResponseTool {

    private static ParseResponseTool mInstance;
    private Gson gson = new Gson();
    public static ParseResponseTool getInstance() {
        if (mInstance == null) {
            synchronized (ParseResponseTool.class) {
                if (mInstance == null) {
                    mInstance = new ParseResponseTool();
                }
            }
        }
        return mInstance;
    }

    public VRoomBean parseRoomBean(String response){
       return gson.fromJson(response, VRoomBean.class);
    }

    public VRoomUserBean parseRoomUserBean(String response){
        return gson.fromJson(response, VRoomUserBean.class);
    }

    public VRMicBean parseVRMicBean(String response){
        return gson.fromJson(response, VRMicBean.class);
    }

    public VRGiftBean parseVRGiftBean(String response){
        return gson.fromJson(response, VRGiftBean.class);
    }

    public VRUserBean parseVRUserBean(String response){
        return gson.fromJson(response, VRUserBean.class);
    }

}
