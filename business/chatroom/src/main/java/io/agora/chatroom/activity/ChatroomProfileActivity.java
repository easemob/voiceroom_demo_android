package io.agora.chatroom.activity;

import android.content.Context;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.imageview.ShapeableImageView;

import io.agora.baseui.BaseActivity;
import io.agora.baseui.popupwindow.CommonPopupWindow;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ProfileGridAdapter;
import io.agora.chatroom.bean.ProfileBean;
import io.agora.chatroom.databinding.ChatroomProfileAvatarBinding;
import io.agora.config.RouterPath;
import io.agora.secnceui.utils.DeviceUtils;
import io.agora.secnceui.widget.titlebar.ChatroomTitleBar;

@Route(path = RouterPath.ChatroomProfilePath)
public class ChatroomProfileActivity extends BaseActivity implements View.OnClickListener, ChatroomTitleBar.OnBackPressListener, TextView.OnEditorActionListener {
   private ChatroomTitleBar titleBar;
   private ShapeableImageView avatar;
   private EditText nickName;
   private TextView number;
   private ImageView edit;
   private InputMethodManager inputManager;
   private ConstraintLayout baseLayout;
   private String nick;

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
      inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
   }

   @Override
   protected void initListener() {
      super.initListener();
      edit.setOnClickListener(this);
      avatar.setOnClickListener(this);
      titleBar.setOnBackPressListener(this);

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
         nickName.setEnabled(true);
         nickName.setFocusable(true);
         nickName.setFocusableInTouchMode(true);
         nickName.requestFocus();
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
              .clippingEnabled(false)
              .alpha(0.618f)
              .layoutId(this,R.layout.chatroom_profile_avatar)
              .intercept(new CommonPopupWindow.ViewEvent<ChatroomProfileAvatarBinding>() {
                 @Override
                 public void getView(CommonPopupWindow popupWindow, ChatroomProfileAvatarBinding view) {
                    ProfileGridAdapter adapter = new ProfileGridAdapter(ChatroomProfileActivity.this);
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
                       }
                    });
                 }
              })
              .build(this)
              .showAtLocation(itemView, Gravity.BOTTOM, 0, 0);
   }

}
