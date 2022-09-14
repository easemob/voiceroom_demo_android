package io.agora.chatroom.bean;

public class ProfileBean {
   private boolean isChecked;
   private int avatarResource;

   public boolean isChecked() {
      return isChecked;
   }

   public void setChecked(boolean check) {
      isChecked = check;
   }

   public int getAvatarResource() {
      return avatarResource;
   }

   public void setAvatarResource(int avatarResource) {
      this.avatarResource = avatarResource;
   }
}
