package io.agora.chatroom.general.net;

import static http.VRHttpClientManager.Method_GET;
import static http.VRHttpClientManager.Method_POST;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.text.StringKt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import http.VRHttpCallback;
import http.VRHttpClientManager;
import http.VRRequestApi;
import io.agora.buddy.tool.GsonTools;
import io.agora.buddy.tool.LogToolsKt;
import io.agora.chatroom.general.repositories.ProfileManager;
import tools.ParseResponseTool;
import tools.ValueCallBack;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;
import tools.bean.VRoomDetail;
import tools.bean.VRoomInfoBean;
import tools.bean.VRoomMicInfo;

public class HttpManager {

    private static HttpManager mInstance;
    private static Context mContext;

    private static final String TAG = "HttpManager";

    public static HttpManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        mContext = context;
        return mInstance;
    }
   //登录
   public void loginWithToken(String device, ValueCallBack<VRUserBean> callBack){
      Map<String, String> headers = new HashMap<>();
      headers.put("Content-Type", "application/json");
      JSONObject requestBody = new JSONObject();
      try {
         requestBody.putOpt("deviceId", device);
          requestBody.putOpt("name", "apex");
          requestBody.putOpt("portrait", "");
//          requestBody.putOpt("phone", "手机号后期上");
//          requestBody.putOpt("verify_code", "验证码后期上");
      } catch (JSONException e) {
         e.printStackTrace();
      }
      new VRHttpClientManager.Builder(mContext)
              .setUrl(VRRequestApi.get().login())
              .setHeaders(headers)
              .setParams(requestBody.toString())
              .setRequestMethod(Method_POST)
              .asyncExecute(new VRHttpCallback() {
                 @Override
                 public void onSuccess(String result) {
                    Log.e("loginWithToken success: ",result);
                     VRUserBean bean = ParseResponseTool.getInstance().parseVRUserBean(result);
                     callBack.onSuccess(bean);
                 }

                 @Override
                 public void onError(int code, String msg) {
                    Log.e("loginWithToken onError: ",code + " msg: " + msg);
                    callBack.onError(code,msg);
                 }
              });
   }

   /**
    **********************************Room 操作请求***************************
    */

    /**
     * 创建语聊房
     * @param name
     * @param is_privacy
     * @param password
     * @param type
     * @param allow_free_join_mic
     * @param sound_effect
     */
    public void createRoom(String name,boolean is_privacy,String password,int type,boolean allow_free_join_mic,String sound_effect){}

    /**
     * 获取指定语聊房信息
     * @param roomId
     */
    public void getRoomDetails(String roomId,ValueCallBack< VRoomInfoBean > callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
//        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("room", roomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().fetchRoomInfo(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("HttpClient success1 " + result, TAG);
                        callBack.onSuccess(GsonTools.toBean(result, VRoomInfoBean.class));
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("HttpClient onError " + msg, TAG);
                        callBack.onError(code, msg);
                    }
                });
    }

    /**
     * 根据id删除房间
     * @param roomId
     */
    public void deleteRoom(String roomId){}

    /**
     * 修改语聊房信息
     * @param roomId
     * @param name
     * @param announcement
     * @param is_private
     * @param password
     * @param use_robot
     * @param allowed_free_join_mic
     */
    public void updateRoomInfo(String roomId,String name,String announcement,boolean is_private,String password,boolean use_robot,boolean allowed_free_join_mic){}

    /**
     * 获取房间列表
     * @param limit
     * @param type
     */
    public void getRoomFromServer(int limit,int type,ValueCallBack<List<VRoomBean.RoomsBean>> callBack){
        VRUserBean userBean = ProfileManager.getInstance().getProfile();
        ArrayList<String> cursor = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + userBean.getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().getRoomList(cursor.size() == 0 ? "": cursor.get(0), limit,type))
                .setHeaders(headers)
                .setParams("")
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        VRoomBean bean = GsonTools.toBean(result,VRoomBean.class);
                        if (!TextUtils.isEmpty(bean.getCursor())){
                            cursor.add(0,bean.getCursor());
                        }
                        callBack.onSuccess(bean.getRooms());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Log.e("getRoomFromServer onError: ",code + " msg: " + msg);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     ***********************************Room user 操作请求***************************
     */

    /**
     * 获取房间内成员
     * @param roomId
     * @param cursor
     * @param limit
     */
    public void getRoomMembers(String roomId,String cursor,int limit){}

    /**
     * 加入房间
     * @param roomId
     * @param password
     */
    public void joinRoom(String roomId,String password,ValueCallBack<Boolean> callBack){
        VRUserBean userBean = ProfileManager.getInstance().getProfile();
        Log.e("joinRoom","roomId:"+roomId);
        Log.e("joinRoom","Authorization:"+ userBean.getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + userBean.getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            if (!TextUtils.isEmpty(password)){
                requestBody.putOpt("password", password);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().joinRoom(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("joinRoom success: ",result);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Log.e("joinRoom onError: ",code + " msg: " + msg);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 离开房间
     * @param roomId
     */
    public void leaveRoom(String roomId){}

    /**
     * 踢出房间
     * @param roomId
     * @param uid
     */
    public void kickRoomMember(String roomId,String uid){}

    /**
     *************************************Mic 麦位操作请求***************************
     */

    /**
     * 获取上麦申请列表
     * @param roomId
     * @param cursor
     * @param limit
     */
    public void getApplyMicList(String roomId,String cursor,int limit){}

    /**
     * 提交上麦申请
     * @param roomId
     * @param mic_index
     */
    public void submitMic(String roomId,int mic_index){}

    /**
     * 撤销上麦申请
     * @param roomId
     */
    public void cancelSubmitMic(String roomId){}

    /**
     * 获取麦位信息
     * @param roomId
     */
    public void getMicInfo(String roomId){}

    /**
     * 关麦
     * @param roomId
     * @param mic_index
     */
    public void closeMic(String roomId,int mic_index){}

    /**
     * 取消关麦
     * @param roomId
     * @param mic_index
     */
    public void cancelCloseMic(String roomId,int mic_index){}

    /**
     * 下麦
     * @param roomId
     */
    public void leaveMic(String roomId){}

    /**
     * 禁止指定麦位
     * @param roomId
     * @param mic_index
     */
    public void muteMic(String roomId,int mic_index){}

    /**
     * 取消禁止指定麦位
     * @param roomId
     * @param mic_index
     */
    public void cancelMuteMic(String roomId,int mic_index){}

    /**
     * 交换麦位
     * @param roomId
     * @param form
     * @param to
     */
    public void exChangeMic(String roomId,int form,int to){}

    /**
     * 踢用户下麦
     * @param uid
     * @param mic_index
     */
    public void kickMic(String uid,int mic_index){}

    /**
     * 用户拒绝上麦邀请
     * @param roomId
     */
    public void rejectMicInvitation(String roomId){}

    /**
     * 锁麦
     * @param roomId
     * @param mic_index
     */
    public void lockMic(String roomId,int mic_index){}

    /**
     * 取消锁麦
     * @param roomId
     */
    public void cancelLockMic(String roomId){}

    /**
     * 邀请上麦
     * @param roomId
     * @param uid
     */
    public void invitationMic(String roomId,String uid){}

    /**
     * 拒绝上麦申请
     * @param roomId
     * @param uid
     */
    public void rejectSubmitMic(String roomId,String uid){}

    /**
     * 同意上麦申请
     * @param roomId
     * @param uid
     * @param mic_index
     */
    public void applySubmitMic(String roomId,String uid,int mic_index){}

    /**
     ************************************Gift 礼物操作请求***************************
     */

    /**
     * 获取赠送礼物榜单
     * @param roomId
     */
    public void getGiftList(String roomId){}

    /**
     * 赠送礼物
     * @param roomId
     * @param gift_id
     * @param num
     * @param to_uid
     */
    public void sendGift(String roomId,String gift_id,int num,int to_uid){}


}
