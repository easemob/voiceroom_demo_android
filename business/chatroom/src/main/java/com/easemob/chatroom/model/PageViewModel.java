package com.easemob.chatroom.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.easemob.chatroom.general.livedatas.SingleSourceLiveData;


public class PageViewModel extends AndroidViewModel {

   private SingleSourceLiveData<Integer> pageObservable;

   public PageViewModel(@NonNull Application application) {
      super(application);
      pageObservable = new SingleSourceLiveData<>();
   }

   /**
    * 设置跳转的页面
    * @param page
    */
   public void setPageSelect(int page) {
      pageObservable.setValue(page);
   }

   /**
    * 获取页面跳转的observable
    * @return
    */
   public LiveData<Integer> getPageSelect() {
      return pageObservable;
   }

   /**
    * 清理注册信息
    */
   public void clearRegisterInfo() {
      pageObservable.setValue(null);
   }
}
