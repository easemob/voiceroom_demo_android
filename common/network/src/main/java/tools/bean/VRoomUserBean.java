package tools.bean;

import java.io.Serializable;
import java.util.List;

public class VRoomUserBean implements Serializable {

   /**
    * total : string
    * cursor : string
    * users : [{"uid":"string","chat_uid":"string","name":"string","portrait":"string"}]
    */

   private String total;
   private String cursor;
   private List<UsersBean> users;

   public String getTotal() {
      return total;
   }

   public void setTotal(String total) {
      this.total = total;
   }

   public String getCursor() {
      return cursor;
   }

   public void setCursor(String cursor) {
      this.cursor = cursor;
   }

   public List<UsersBean> getUsers() {
      return users;
   }

   public void setUsers(List<UsersBean> users) {
      this.users = users;
   }

   public static class UsersBean implements Serializable {
      /**
       * uid : string
       * chat_uid : string
       * name : string
       * portrait : string
       */

      private String uid;
      private String chat_uid;
      private String name;
      private String portrait;

      public String getUid() {
         return uid;
      }

      public void setUid(String uid) {
         this.uid = uid;
      }

      public String getChat_uid() {
         return chat_uid;
      }

      public void setChat_uid(String chat_uid) {
         this.chat_uid = chat_uid;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getPortrait() {
         return portrait;
      }

      public void setPortrait(String portrait) {
         this.portrait = portrait;
      }
   }
}
