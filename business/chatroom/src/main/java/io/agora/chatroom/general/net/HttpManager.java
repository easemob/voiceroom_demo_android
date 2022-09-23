package io.agora.chatroom.general.net;

import static http.VRHttpClientManager.Method_DELETE;
import static http.VRHttpClientManager.Method_GET;
import static http.VRHttpClientManager.Method_POST;
import static http.VRHttpClientManager.Method_PUT;

import android.content.Context;
import android.service.autofill.FieldClassification;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import http.VRHttpCallback;
import http.VRHttpClientManager;
import http.VRRequestApi;
import io.agora.baseui.general.net.ErrorCode;
import io.agora.buddy.tool.GsonTools;
import io.agora.buddy.tool.LogToolsKt;
import io.agora.chatroom.general.repositories.ProfileManager;
import tools.ValueCallBack;
import tools.bean.VRGiftBean;
import tools.bean.VRMicBean;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;
import tools.bean.VRoomUserBean;

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
      String portrait = ProfileManager.getInstance().getProfile().getPortrait();
      String randomPortrait = "avatar"+ Math.round((Math.random()*18)+1);
      try {
          requestBody.putOpt("deviceId", device);
          requestBody.putOpt("name", "apex");
          requestBody.putOpt("portrait",!TextUtils.isEmpty(portrait)? portrait:randomPortrait);
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
            requestBody.putOpt("is_private", is_privacy);
            if (!TextUtils.isEmpty(password)){
                requestBody.putOpt("password", password);
            }
            requestBody.putOpt("type", type);
            if (type == 0){
                requestBody.putOpt("allow_free_join_mic", allow_free_join_mic);
                requestBody.putOpt("sound_effect", sound_effect);
            }

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
                        LogToolsKt.logE("createRoom success: " + result, TAG);
                        VRoomInfoBean bean = GsonTools.toBean(result,VRoomInfoBean.class);
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("createRoom onError: " + code + " msg: " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 获取指定语聊房信息
     * @param roomId
     */
    public void getRoomDetails(String roomId,ValueCallBack<VRoomInfoBean> callBack){
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
                        LogToolsKt.logE("getRoomDetails onSuccess: " + result, TAG);
                        callBack.onSuccess(GsonTools.toBean(result, VRoomInfoBean.class));
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("getRoomDetails onError：" + code + "msg:" + msg, TAG);
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
    public void updateRoomInfo(String roomId, String name, String announcement, Boolean is_private,
                               String password, Boolean use_robot, Boolean allowed_free_join_mic,
                               Integer robotVolume,ValueCallBack<Boolean> callBack) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            if (name != null) requestBody.putOpt("name", name);
            if (announcement != null) requestBody.putOpt("announcement", announcement);
            if (is_private != null) requestBody.putOpt("is_private", is_private);
            if (password != null) requestBody.putOpt("password", password);
            if (use_robot != null) requestBody.putOpt("use_robot", use_robot);
            if (allowed_free_join_mic != null) requestBody.putOpt("allowed_free_join_mic", allowed_free_join_mic);
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
    public void getRoomFromServer(int limit,int type,String cursor,ValueCallBack<VRoomBean> callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().getRoomList(!TextUtils.isEmpty(cursor) ? cursor:"" , limit,type))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("getRoomFromServer success: " + result, TAG);
                        VRoomBean bean = GsonTools.toBean(result,VRoomBean.class);
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("getRoomFromServer onError " + msg, TAG);
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
     * @param limit
     */
    public void getRoomMembers(String roomId, int limit, ValueCallBack<VRoomUserBean> callBack){
        ArrayList<String> cursor = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().fetchRoomMembers(roomId,cursor.size() == 0 ? "": cursor.get(0), limit))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("getRoomMembers success: " + result, TAG);
                        VRoomUserBean bean = GsonTools.toBean(result,VRoomUserBean.class);
                        if (!TextUtils.isEmpty(bean.getCursor())){
                            cursor.add(0,bean.getCursor());
                        }
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("getRoomMembers onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 加入房间
     * @param roomId
     * @param password
     */
    public void joinRoom(String roomId,String password,ValueCallBack<Boolean> callBack){
        Log.e("joinRoom","roomId:"+roomId);
        Log.e("joinRoom","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().joinRoom(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("joinRoom success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("joinRoom onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 离开房间
     * @param roomId
     */
    public void leaveRoom(String roomId,ValueCallBack<Boolean> callBack){
        Log.e("leaveRoom","roomId:"+roomId);
        Log.e("leaveRoom","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().leaveRoom(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("joinRoom success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("joinRoom onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 踢出房间
     * @param roomId
     * @param uid
     */
    public void kickRoomMember(String roomId,String uid,ValueCallBack<String> callBack){
        Log.e("kickRoomMember","roomId:"+roomId);
        Log.e("kickRoomMember","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().kickUser(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("kickRoomMember success: " + result, TAG);
                        callBack.onSuccess(result);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("kickRoomMember onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     *************************************Mic 麦位操作请求***************************
     */

    /**
     * 获取上麦申请列表
     * @param roomId
     * @param limit
     */
    public void getApplyMicList(String roomId,int limit,ValueCallBack<VRMicBean> callBack){
        ArrayList<String> cursor = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().fetchApplyMembers(roomId,cursor.size() == 0 ? "": cursor.get(0), limit))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("getApplyMicList success: " + result, TAG);
                        VRMicBean bean = GsonTools.toBean(result, VRMicBean.class);
                        if (!TextUtils.isEmpty(bean.getCursor())){
                            cursor.add(0,bean.getCursor());
                        }
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("getApplyMicList onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 提交上麦申请
     * @param roomId
     * @param mic_index
     */
    public void submitMic(String roomId,int mic_index,ValueCallBack<Boolean> callBack){
        Log.e("submitMic","roomId:"+roomId);
        Log.e("submitMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("mic_index", mic_index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().submitApply(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("submitMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("submitMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 撤销上麦申请
     * @param roomId
     */
    public void cancelSubmitMic(String roomId,ValueCallBack<Boolean> callBack){
        Log.e("cancelSubmitMic","roomId:"+roomId);
        Log.e("cancelSubmitMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().cancelApply(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("cancelSubmitMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("cancelSubmitMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 获取麦位信息
     * @param roomId
     */
    public void getMicInfo(String roomId,ValueCallBack<VRoomUserBean> callBack){
        Log.e("getMicInfo","roomId:"+roomId);
        Log.e("getMicInfo","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().fetchMicsInfo(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("getMicInfo success: " + result, TAG);
                        VRoomUserBean bean = GsonTools.toBean(result,VRoomUserBean.class);
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("getMicInfo onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 关麦
     * @param roomId
     * @param mic_index
     */
    public void closeMic(String roomId,int mic_index,ValueCallBack<Boolean> callBack){
        Log.e("closeMic","roomId:"+roomId);
        Log.e("closeMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("mic_index", mic_index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().closeMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("closeMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("closeMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 取消关麦
     * @param roomId
     * @param mic_index
     */
    public void cancelCloseMic(String roomId,int mic_index,ValueCallBack<Boolean> callBack){
        Log.e("cancelCloseMic","roomId:"+roomId);
        Log.e("cancelCloseMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().cancelCloseMic(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("cancelCloseMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("cancelCloseMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 下麦
     * @param roomId
     */
    public void leaveMic(String roomId,ValueCallBack<Boolean> callBack){
        Log.e("leaveMic","roomId:"+roomId);
        Log.e("leaveMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().leaveMic(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("leaveMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("leaveMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 禁止指定麦位
     * @param roomId
     * @param mic_index
     */
    public void muteMic(String roomId,int mic_index,ValueCallBack<String> callBack){
        Log.e("muteMic","roomId:"+roomId);
        Log.e("muteMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("mic_index", mic_index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().muteMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("muteMic success: " + result, TAG);
                        callBack.onSuccess(result);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("muteMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 取消禁止指定麦位
     * @param roomId
     * @param mic_index
     */
    public void cancelMuteMic(String roomId,int mic_index,ValueCallBack<String> callBack){
        Log.e("cancelMuteMic","roomId:"+roomId);
        Log.e("cancelMuteMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("mic_index", mic_index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().unMuteMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("cancelMuteMic success: " + result, TAG);
                        callBack.onSuccess(result);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("cancelMuteMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 交换麦位
     * @param roomId
     * @param form
     * @param to
     */
    public void exChangeMic(String roomId,int form,int to,ValueCallBack<Boolean> callBack){
        Log.e("exChangeMic","roomId:"+roomId);
        Log.e("exChangeMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("from", form);
            requestBody.putOpt("to", to);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().exchangeMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("exChangeMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("exChangeMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 踢用户下麦
     * @param roomId
     * @param uid
     * @param mic_index
     */
    public void kickMic(String roomId,String uid,int mic_index,ValueCallBack<Boolean> callBack){
        Log.e("kickMic","uid: "+uid);
        Log.e("kickMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("uid", uid);
            requestBody.putOpt("mic_index", mic_index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().kickMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("kickMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("kickMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 用户拒绝上麦邀请
     * @param roomId
     */
    public void rejectMicInvitation(String roomId,ValueCallBack<VRoomBean> callBack){
        Log.e("rejectMicInvitation","roomId:"+roomId);
        Log.e("rejectMicInvitation","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().rejectMicInvitation(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("rejectMicInvitation success: " + result, TAG);
                        VRoomBean bean = GsonTools.toBean(result,VRoomBean.class);
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("rejectMicInvitation onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 锁麦
     * @param roomId
     * @param mic_index
     */
    public void lockMic(String roomId,int mic_index,ValueCallBack<Boolean> callBack){
        Log.e("lockMic","roomId:"+roomId);
        Log.e("lockMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("mic_index", mic_index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().lockMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("lockMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("lockMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 取消锁麦
     * @param roomId
     */
    public void cancelLockMic(String roomId,ValueCallBack<Boolean> callBack){
        Log.e("cancelLockMic","roomId:"+roomId);
        Log.e("cancelLockMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().unlockMic(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_DELETE)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("cancelLockMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("cancelLockMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 邀请上麦
     * @param roomId
     * @param uid
     */
    public void invitationMic(String roomId,String uid,ValueCallBack<Boolean> callBack){
        Log.e("invitationMic","roomId:"+roomId);
        Log.e("invitationMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().inviteUserToMic(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("invitationMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("closeMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 拒绝上麦申请
     * @param roomId
     * @param uid
     */
    public void rejectSubmitMic(String roomId,String uid,ValueCallBack<Boolean> callBack){
        Log.e("rejectSubmitMic","roomId:"+roomId);
        Log.e("rejectSubmitMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().rejectApplyInvitation(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("rejectSubmitMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("rejectSubmitMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 同意上麦申请
     * @param roomId
     * @param uid
     * @param mic_index
     */
    public void applySubmitMic(String roomId,String uid,int mic_index,ValueCallBack<Boolean> callBack){
        Log.e("applySubmitMic","roomId:"+roomId);
        Log.e("applySubmitMic","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("mic_index", mic_index);
            requestBody.putOpt("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().ApplyAgreeInvitation(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("applySubmitMic success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("applySubmitMic onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     ************************************Gift 礼物操作请求***************************
     */

    /**
     * 获取赠送礼物榜单
     * @param roomId
     */
    public void getGiftList(String roomId,ValueCallBack<VRGiftBean> callBack){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().fetchGiftContribute(roomId))
                .setHeaders(headers)
                .setRequestMethod(Method_GET)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("getGiftList success: " + result, TAG);
                        VRGiftBean bean = GsonTools.toBean(result, VRGiftBean.class);
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("getGiftList onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }

    /**
     * 赠送礼物
     * @param roomId
     * @param gift_id
     * @param num
     * @param to_uid
     */
    public void sendGift(String roomId,String gift_id,int num,int to_uid,ValueCallBack<Boolean> callBack){
        Log.e("sendGift","roomId:"+roomId);
        Log.e("sendGift","Authorization:"+ ProfileManager.getInstance().getProfile().getAuthorization());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ProfileManager.getInstance().getProfile().getAuthorization());
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.putOpt("gift_id", gift_id);
            requestBody.putOpt("num", num);
            requestBody.putOpt("to_uid", to_uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VRHttpClientManager.Builder(mContext)
                .setUrl(VRRequestApi.get().giftTo(roomId))
                .setHeaders(headers)
                .setParams(requestBody.toString())
                .setRequestMethod(Method_POST)
                .asyncExecute(new VRHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogToolsKt.logE("sendGift success: " + result, TAG);
                        callBack.onSuccess(true);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogToolsKt.logE("sendGift onError " + msg, TAG);
                        callBack.onError(code,msg);
                    }
                });
    }


}
