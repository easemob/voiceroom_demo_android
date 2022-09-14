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
   /**
    * room : {"room_id":"string","channel_id":"string","chat_room_id":0,"name":"string","type":0,"is_private":true,"allowed_free_join_mic":true,"owner":{"uid":"string","chat_uid":"string","name":"string","portrait":"string"},"members_total":0,"click_count":0,"announcement":"string","ranking_list":[{"name":"string","portrait":"string","amount":0}],"member_list":[{"uid":"string","chat_uid":"string","name":"string","portrait":"string"}]}
    */

   private RoomBean room;

   public String getTotal() {
      return total;
   }

   public String getCursor() {
      return cursor;
   }

   public List<UsersBean> getUsers() {
      return users;
   }

   public RoomBean getRoom() {
      return room;
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

   public static class RoomBean implements Serializable {
      /**
       * room_id : string
       * channel_id : string
       * chat_room_id : 0
       * name : string
       * type : 0
       * is_private : true
       * allowed_free_join_mic : true
       * owner : {"uid":"string","chat_uid":"string","name":"string","portrait":"string"}
       * members_total : 0
       * click_count : 0
       * announcement : string
       * ranking_list : [{"name":"string","portrait":"string","amount":0}]
       * member_list : [{"uid":"string","chat_uid":"string","name":"string","portrait":"string"}]
       */

      private String room_id;
      private String channel_id;
      private int chat_room_id;
      private String name;
      private int type;
      private boolean is_private;
      private boolean allowed_free_join_mic;
      private UsersBean owner;
      private int members_total;
      private int click_count;
      private String announcement;
      private List<RankingListBean> ranking_list;
      private List<UsersBean> member_list;

      public String getRoom_id() {
         return room_id;
      }

      public String getChannel_id() {
         return channel_id;
      }

      public int getChat_room_id() {
         return chat_room_id;
      }

      public String getName() {
         return name;
      }

      public int getType() {
         return type;
      }

      public boolean isIs_private() {
         return is_private;
      }

      public boolean isAllowed_free_join_mic() {
         return allowed_free_join_mic;
      }

      public UsersBean getOwner() {
         return owner;
      }

      public int getMembers_total() {
         return members_total;
      }

      public int getClick_count() {
         return click_count;
      }

      public String getAnnouncement() {
         return announcement;
      }

      public List<RankingListBean> getRanking_list() {
         return ranking_list;
      }

      public List<UsersBean> getMember_list() {
         return member_list;
      }

      public static class RankingListBean implements Serializable {
         /**
          * name : string
          * portrait : string
          * amount : 0
          */

         private String name;
         private String portrait;
         private int amount;

         public String getName() {
            return name;
         }

         public String getPortrait() {
            return portrait;
         }

         public int getAmount() {
            return amount;
         }
      }
   }
}
