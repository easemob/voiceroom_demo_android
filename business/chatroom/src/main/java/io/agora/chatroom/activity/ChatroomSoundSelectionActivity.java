package io.agora.chatroom.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.agora.baseui.BaseActivity;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomSoundSelectionAdapter;
import io.agora.config.ConfigConstants;
import io.agora.secnceui.bean.SoundSelectionBean;
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionConstructor;
import io.agora.secnceui.widget.titlebar.ChatroomTitleBar;

public class ChatroomSoundSelectionActivity extends BaseActivity implements ChatroomSoundSelectionAdapter.OnItemClickListener {

   private ChatroomTitleBar titleBar;
   private RecyclerView recyclerView;
   private ConstraintLayout goLive;
   private ChatroomSoundSelectionAdapter adapter;
   private List<SoundSelectionBean> soundSelectionList = new ArrayList<>();

   @Override
   protected int getLayoutId() {
      return R.layout.chatroom_sound_selection_layout;
   }

   @Override
   protected void initSystemFit() {
      setFitSystemForTheme(false, "#00000000");
      setStatusBarTextColor(false);
   }

   @Override
   protected void initView(Bundle savedInstanceState) {
      super.initView(savedInstanceState);
      titleBar = findViewById(R.id.title_bar);
      recyclerView = findViewById(R.id.list);
      goLive = findViewById(R.id.bottom_layout);
      adapter = new ChatroomSoundSelectionAdapter(this);
      recyclerView.setAdapter(adapter);
   }

   @Override
   protected void initListener() {
      super.initListener();
      adapter.SetOnItemClickListener(this);
   }

   @Override
   protected void initData() {
      super.initData();
      getSoundSelectionData(this);
   }


   @Override
   public void OnItemClick(int position, SoundSelectionBean bean) {

   }

   public void getSoundSelectionData(Context context) {
      SoundSelectionBean b = RoomSoundSelectionConstructor.INSTANCE.builderCurSoundSelection(context, ConfigConstants.SoundSelection.Social_Chat);
      soundSelectionList.addAll(Collections.singleton(b));
      Log.e("ChatroomSoundSelectionActivity"," getData" + soundSelectionList.size());
      adapter.setData(soundSelectionList);
   }


}
