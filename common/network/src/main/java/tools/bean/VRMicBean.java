package tools.bean;

import java.io.Serializable;
import java.util.List;

public class VRMicBean implements Serializable {

   /**
    * total : 0
    * cursor : string
    * members : [{"uid":"string","chat_uid":"string","name":"string","portrait":"string"}]
    */

   private int total;
   private String cursor;
   private List<MembersBean> members;
   /**
    * mic_index : 0
    */

   private int mic_index;
   /**
    * index : 0
    * status : 0
    * user : {"uid":"string","chat_uid":"string","name":"string","portrait":"string"}
    */

   private int index;
   private int status;
   private MembersBean user;

   public int getTotal() {
      return total;
   }

   public String getCursor() {
      return cursor;
   }

   public List<MembersBean> getMembers() {
      return members;
   }

   public int getMic_index() {
      return mic_index;
   }

   public int getIndex() {
      return index;
   }

   public int getStatus() {
      return status;
   }

   public MembersBean getUser() {
      return user;
   }

   public static class MembersBean implements Serializable {
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
      /**
       * id : string
       */

      private String id;
      /**
       * from : 0
       * to : 0
       */
      private int from;
      private int to;

      public String getUid() {
         return uid;
      }

      public String getChat_uid() {
         return chat_uid;
      }

      public String getName() {
         return name;
      }

      public String getPortrait() {
         return portrait;
      }
   }
}
