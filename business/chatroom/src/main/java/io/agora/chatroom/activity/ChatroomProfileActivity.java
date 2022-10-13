package io.agora.chatroom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.imageview.ShapeableImageView;
import org.json.JSONException;

import io.agora.baseui.BaseActivity;
import io.agora.baseui.popupwindow.CommonPopupWindow;
import io.agora.buddy.tool.ToastTools;
import io.agora.chat.ChatClient;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ProfileGridAdapter;
import io.agora.chatroom.bean.ProfileBean;
import io.agora.chatroom.databinding.ChatroomProfileAvatarBinding;
import io.agora.chatroom.general.net.HttpManager;
import io.agora.chatroom.general.repositories.ProfileManager;
import io.agora.config.RouterPath;
import io.agora.secnceui.utils.DeviceUtils;
import io.agora.secnceui.widget.titlebar.ChatroomTitleBar;
import manager.ChatroomConfigManager;
import tools.ValueCallBack;
import tools.bean.VRUserBean;

@Route(path = RouterPath.ChatroomProfilePath)
public class ChatroomProfileActivity extends BaseActivity implements View.OnClickListener, ChatroomTitleBar.OnBackPressListener, TextView.OnEditorActionListener {
   private ChatroomTitleBar titleBar;
   private ShapeableImageView avatar;
   private EditText nickName;
   private TextView number;
   private ImageView edit;
   private InputMethodManager inputManager;
   private ConstraintLayout baseLayout;
   private ConstraintLayout disclaimer;
   private LinearLayoutCompat content;
   private String nick;
   private String oldNick;

   @Override
   protected int getLayoutId() {
      return R.layout.chatroom_profile_layout;
   }

   @Override
   protected void initView(Bundle savedInstanceState) {
      super.initView(savedInstanceState);
      titleBar = findViewById(R.id.title_bar);
      avatar = findViewById(R.id.avatar);
      edit = findViewById(R.id.edit);
      nickName = findViewById(R.id.nick_name);
      number = findViewById(R.id.number);
      baseLayout = findViewById(R.id.base_layout);
      disclaimer = findViewById(R.id.disclaimer_layout);
      content = findViewById(R.id.content_layout);
      inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
   }

   @Override
   protected void initListener() {
      super.initListener();
      edit.setOnClickListener(this);
      avatar.setOnClickListener(this);
      titleBar.setOnBackPressListener(this);
      disclaimer.setOnClickListener(this);

      baseLayout.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            baseLayout.setFocusable(true);
            baseLayout.setFocusableInTouchMode(true);
            baseLayout.requestFocus();
            hideKeyboard();
            return false;
         }
      });

      nickName.setOnEditorActionListener(this);
   }

   @Override
   protected void initData() {
      super.initData();
      VRUserBean bean = ProfileManager.getInstance().getProfile();
      if (bean != null){
         String ProfileName =  ProfileManager.getInstance().getProfile().getPortrait();
         String name = ProfileManager.getInstance().getProfile().getName();
         String ID = ProfileManager.getInstance().getProfile().getUid();
         if (!TextUtils.isEmpty(ProfileName)){
            int resId = getResources().getIdentifier(ProfileName, "drawable", getPackageName());
            if (resId != 0){
               avatar.setImageResource(resId);
            }
         }
         nickName.setText(name);
         oldNick = name;
         number.setText("ID: " + ID);
      }

   }

   @Override
   protected void initSystemFit() {
      setFitSystemForTheme(false, "#00000000");
      setStatusBarTextColor(false);
   }

   @Override
   public void onClick(View v) {
      if (v.getId() == R.id.avatar) {
         showDialog(v);
      }else if (v.getId() == R.id.edit){
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
         content.setFocusable(false);
         content.setFocusableInTouchMode(false);
         nickName.setEnabled(true);
         nickName.requestFocus();
         nickName.setSelection(nickName.getText().length());
         showKeyboard(nickName);
      }else if (v.getId() == R.id.disclaimer_layout){
         startActivity(new Intent(ChatroomProfileActivity.this,ChatroomDisclaimerActivity.class));
      }

   }

   @Override
   public void onBackPress(View view) {
      onBackPressed();
   }

   @Override
   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
      int result = actionId & EditorInfo.IME_MASK_ACTION;
      switch(result) {
         case EditorInfo.IME_ACTION_DONE:
            // done stuff
            nick = nickName.getText().toString().trim();
            nickName.setEnabled(false);
            nickName.setFocusable(false);
            nickName.setFocusableInTouchMode(false);
            VRUserBean bean = ProfileManager.getInstance().getProfile();
            bean.setName(nickName.getText().toString());
            updateProfile(bean);
            break;
         case EditorInfo.IME_ACTION_NEXT:
            // next stuff
            break;
      }
      return false;
   }

   private void showDialog(View itemView){
      new CommonPopupWindow.ViewDataBindingBuilder<ChatroomProfileAvatarBinding>()
              .width(ConstraintLayout.LayoutParams.MATCH_PARENT)
              .height(DeviceUtils.dp2px(this,535))
              .outsideTouchable(true)
              .focusable(true)
              .animationStyle(io.agora.secnceui.R.style.BottomDialogFragment_Animation)
              .clippingEnabled(false)
              .alpha(0.618f)
              .layoutId(this,R.layout.chatroom_profile_avatar)
              .intercept(new CommonPopupWindow.ViewEvent<ChatroomProfileAvatarBinding>() {
                 @Override
                 public void getView(CommonPopupWindow popupWindow, ChatroomProfileAvatarBinding view) {
                    ProfileGridAdapter adapter = new ProfileGridAdapter(ChatroomProfileActivity.this);
                    String avatarUrl = ProfileManager.getInstance().getProfile().getPortrait();
                    int index = adapter.getSelectedPosition(avatarUrl);
                    adapter.setSelectedPosition(index);
                    view.gridView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(new ProfileGridAdapter.OnItemClickListener() {
                       @Override
                       public void OnItemClick(int position, ProfileBean bean) {
                          Log.e("SetOnItemClickListener","OnItemClick" + position);
                          boolean checked = bean.isChecked();
                          Log.e("SetOnItemClickListener","OnItemClick" + checked);
                          bean.setChecked(!checked);
                          if(bean.isChecked()) {
                             adapter.setSelectedPosition(position);
                          }else {
                             adapter.setSelectedPosition(-1);
                          }
                          int resId = bean.getAvatarResource();
                          if (resId != 0){
                             avatar.setImageResource(resId);
                             updateProfile(bean);
                          }
                       }
                    });
                 }
              })
              .build(this)
              .showAtLocation(itemView, Gravity.BOTTOM, 0, 0);
   }

   private void updateProfile(ProfileBean bean){
      try {
         HttpManager.getInstance(ChatroomProfileActivity.this).loginWithToken(
                 ChatClient.getInstance().getDeviceInfo().getString("deviceid"), bean.getAvatarName(), new ValueCallBack<VRUserBean>() {
                    @Override
                    public void onSuccess(VRUserBean var1) {
                       ProfileManager.getInstance().setProfile(var1);
                    }

                    @Override
                    public void onError(int var1, String var2) {
                       ToastTools.show(ChatroomProfileActivity.this, getString(R.string.room_profile_update_name) + ": " + var2, Toast.LENGTH_SHORT);
                       onFail();
                    }
                 });
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }

   private void updateProfile(VRUserBean bean){
      try {
         HttpManager.getInstance(ChatroomProfileActivity.this).loginWithToken(
                 ChatClient.getInstance().getDeviceInfo().getString("deviceid"),bean.getPortrait(), new ValueCallBack<VRUserBean>() {
                    @Override
                    public void onSuccess(VRUserBean var1) {
                       ProfileManager.getInstance().setProfile(var1);
                    }

                    @Override
                    public void onError(int var1, String var2) {
                       ToastTools.show(ChatroomProfileActivity.this, getString(R.string.room_profile_update_name)+ ": " + var2, Toast.LENGTH_SHORT);
                       onFail();
                    }
                 });
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }

   private void onFail(){
      VRUserBean bean = ProfileManager.getInstance().getProfile();
      bean.setName(oldNick);
      ProfileManager.getInstance().setProfile(bean);
      nickName.setText(oldNick);
   }
}
