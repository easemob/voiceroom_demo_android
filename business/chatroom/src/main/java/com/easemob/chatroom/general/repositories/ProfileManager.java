package com.easemob.chatroom.general.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONException;

import com.easemob.buddy.tool.GsonTools;
import com.hyphenate.chat.EMClient;

import manager.ChatroomConfigManager;
import tools.bean.VRUserBean;

public class ProfileManager {
   private static ProfileManager instance;
   private SharedPreferences.Editor editor;
   private SharedPreferences mSharedPreferences;
   private static String KEY_AT_PROFILE = "AT_PROFILE";
   private VRUserBean vrUserBean;

   public synchronized static ProfileManager getInstance(){
      if(instance == null){
         instance = new ProfileManager();
      }
      return instance;
   }

   private ProfileManager(){
      mSharedPreferences = ChatroomConfigManager.getInstance().getContext().getSharedPreferences("SP_AT_PROFILE", Context.MODE_PRIVATE);
      editor = mSharedPreferences.edit();
   }

   public void setProfile(VRUserBean value) {
      try {
         vrUserBean = value;
         String device = EMClient.getInstance().getDeviceInfo().getString("deviceid");
         if (TextUtils.isEmpty(device)){
            editor.putString(KEY_AT_PROFILE, GsonTools.beanToString(value));
         }else {
            editor.putString(device, GsonTools.beanToString(value));
         }
         editor.apply();
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }

   public VRUserBean getProfile() {
      try {
         if (vrUserBean != null) return vrUserBean;
         String device = EMClient.getInstance().getDeviceInfo().getString("deviceid");
         String profile = mSharedPreferences.getString(device,"");
         if (!TextUtils.isEmpty(profile)){
           return GsonTools.toBean(profile,VRUserBean.class);
         }
      } catch (JSONException e) {
         e.printStackTrace();
      }
      return null;
   }

    public boolean isMyself(String uid) {
        VRUserBean currentUser = getProfile();
        if (currentUser != null) {

            return TextUtils.equals(currentUser.getUid(), uid);
        }
        return false;
    }

    public String myUid() {
        VRUserBean currentUser = getProfile();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }

    public int rtcUid() {
        VRUserBean currentUser = getProfile();
        if (currentUser != null) {
            return currentUser.getRtc_uid();
        }
        return -1;
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}