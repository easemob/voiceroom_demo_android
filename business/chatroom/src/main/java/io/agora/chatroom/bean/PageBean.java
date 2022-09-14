package io.agora.chatroom.bean;

public class PageBean {
   String tab_title;
   String room_name;
   String room_desc;
   int room_type;

   public void setTab_title(String tab_title) {
      this.tab_title = tab_title;
   }

   public String getTab_title() {
      return tab_title;
   }

   public void setRoom_name(String room_name) {
      this.room_name = room_name;
   }

   public String getRoom_name() {
      return room_name;
   }

   public void setRoom_type(int room_type) {
      this.room_type = room_type;
   }

   public int getRoom_type() {
      return room_type;
   }

   public void setRoom_desc(String room_desc) {
      this.room_desc = room_desc;
   }

   public String getRoom_desc() {
      return room_desc;
   }
}
