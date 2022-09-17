package io.agora.chatroom.general.net;

import static http.VRHttpClientManager.Method_DELETE;
import static http.VRHttpClientManager.Method_GET;
import static http.VRHttpClientManager.Method_POST;
import static http.VRHttpClientManager.Method_PUT;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.text.StringKt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import http.VRHttpCallback;
import http.VRHttpClientManager;
import http.VRHttpServer;
import http.VRRequestApi;
import io.agora.buddy.tool.GsonTools;
import io.agora.buddy.tool.LogToolsKt;
import io.agora.chatroom.general.repositories.ProfileManager;
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

    @NonNull
    private static String buildAuth() {
        VRUserBean userBean = ProfileManager.getInstance().getProfile();
        String authorization = null;
        if (userBean != null) {
            authorization = userBean.getAuthorization();
        }
        if (authorization == null) {
            authorization = "";
        }
        return "Bearer " + authorization;
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
                     LogToolsKt.logE("loginWithToken success: " + result, TAG);
                     VRUserBean bean = GsonTools.toBean(result,VRUserBean.class);
                     callBack.onSuccess(bean);
                 }

                 @Override
                 public void onError(int code, String msg) {
                     LogToolsKt.logE("loginWithToken onError: " + code + " msg: " + msg, TAG);
                    callBack.onError(code,msg);
                 }
              });

//       Map<String, Object> body = new HashMap<>();
//       body.put("deviceId", device);
//       body.put("name", "apex");
//       body.put("portrait", "");
//          body.putOpt("phone", "手机号后期上");
//          body.putOpt("verify_code", "验证码后期上");
//       VRHttpServer.get().enqueuePost(VRRequestApi.get().login(), headers, body, VRUserBean.class, new VRHttpServer.IHttpCallback<VRUserBean>() {
//
//           @Override
//           public void onSuccess(String bodyString, VRUserBean data) {
//               Log.e("loginWithToken success: "+ bodyString,TAG);
//               callBack.onSuccess(data);
//           }
//
//           @Override
//           public void onFail(int code, String message) {
//               LogToolsKt.logE("loginWithToken onError: "+code + " msg: " + message,TAG);
//               callBack.onError(code,message);
//           }
//       });
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
    public void createRoom(String name,boolean is_privacy,String password,int type,boolean allow_free_join_mic,String sound_effect,ValueCallBack<VRoomInfoBean> callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("name", name);
            requestBody.putOpt("is_privacy", is_privacy);
            requestBody.putOpt("password", password);
            requestBody.putOpt("type", type);
            requestBody.putOpt("allow_free_join_mic", allow_free_join_mic);
            requestBody.putOpt("sound_effect", sound_effect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().createRoom())
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("createRoom success: ",result);
                        VRoomInfoBean bean = GsonTools.toBean(result,VRoomInfoBean.class);
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Log.e("createRoom onError: ",code + " msg: " + msg);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 获取指定语聊房信息
     * @param roomId
     */
    public void getRoomDetails(String roomId,ValueCallBack< VRoomInfoBean > callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", buildAuth());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().fetchRoomInfo(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("fetchRoomInfo onSuccess: " + result, TAG);
                        callBack.onSuccess(GsonTools.toBean(result, VRoomInfoBean.class));
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("fetchRoomInfo onError：" + code + "msg:" + msg, TAG);
                        callBack.onError(code, msg);
                    }
                });
    }

    /**
     * 根据id删除房间
     * @param roomId
     */
    public void deleteRoom(String roomId,ValueCallBack<Boolean> callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().deleteRoom(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("deleteRoom success " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("deleteRoom onError " + msg, TAG);
                        callBack.onError(code, msg);
                    }
                });
    }

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
    public void updateRoomInfo(String roomId,String name,String announcement,boolean is_private,
                               String password,boolean use_robot,boolean allowed_free_join_mic,
                               ValueCallBack<Boolean> callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("name", name);
            requestBody.putOpt("announcement", announcement);
            requestBody.putOpt("is_private", is_private);
            requestBody.putOpt("password", password);
            requestBody.putOpt("use_rebot", use_robot);
            requestBody.putOpt("allowed_free_join_mic", allowed_free_join_mic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().modifyRoomInfo(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_PUT)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("updateRoomInfo success " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("updateRoomInfo onError " + msg, TAG);
                        callBack.onError(code, msg);
                    }
                });
    }

    /**
     * 获取房间列表
     * @param limit
     * @param type
     */
    public void getRoomFromServer(int limit,int type,ValueCallBack<List<VRoomBean.RoomsBean>> callBack){
        ArrayList<String> cursor = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
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
