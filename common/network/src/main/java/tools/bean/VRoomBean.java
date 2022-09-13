package tools.bean;

import java.io.Serializable;
import java.util.List;

public class VRoomBean implements Serializable {


   /**
    * total : 0
    * rooms : [{"room_id":"string","channel_id":"string","chat_room_id":0,"name":"string","owner":{"uid":"string","chat_uid":"string","name":"string","portrait":"string"},"is_private":true,"type":0,"created_at":0}]
    * cursor : string
    */

   private int total;
   private String cursor;
   private List<RoomsBean> roomList;

   public List<RoomsBean> getRooms() {
      return roomList;
   }

   public String getCursor() {
      return cursor;
   }

   public int getTotal() {
      return total;
   }

   public RoomDetailsBean getRoomDetails() {
      return roomDetails;
   }

   public List<MicInfoBean> getMic_info() {
      return mic_info;
   }

   public static class RoomsBean implements Serializable {
      /**
       * room_id : string
       * channel_id : string
       * chat_room_id : 0
       * name : string
       * owner : {"uid":"string","chat_uid":"string","name":"string","portrait":"string"}
       * is_private : true
       * type : 0
       * created_at : 0
       */

      private String room_id;
      private String channel_id;
      private int chat_room_id;
      private String name;
      private OwnerBean owner;
      private boolean is_private;
      private int type;
      private int created_at;

      public void setType(int type) {
         this.type = type;
      }
      public int getType() {
         return type;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getName() {
         return name;
      }

      public String getRoom_id() {
         return room_id;
      }

      public void setIs_private(boolean is_private) {
         this.is_private = is_private;
      }

      public boolean isIs_private() {
         return is_private;
      }

      public void setOwner(OwnerBean owner) {
         this.owner = owner;
      }

      public OwnerBean getOwner() {
         return owner;
      }

      public String getChannel_id() {
         return channel_id;
      }

      public static class OwnerBean implements Serializable {
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

         public String getName() {
            return name;
         }

         public void setName(String name) {
            this.name = name;
         }

         public String getUid() {
            return uid;
         }

         public String getChat_uid() {
            return chat_uid;
         }

         public String getPortrait() {
            return portrait;
         }
      }
   }

   /////////////////////////房间详情//////////////////

   /**
    * room : {"room_id":"string","channel_id":"string","chat_room_id":0,"name":"string","type":0,"is_private":true,"allowed_free_join_mic":true,"owner":{"uid":"string","chat_uid":"string","name":"string","portrait":"string"},"members_total":0,"click_count":0,"announcement":"string","ranking_list":[{"name":"string","portrait":"string","amount":0}],"member_list":[{"uid":"string","chat_uid":"string","name":"string","portrait":"string"}]}
    * mic_info : [{"index":0,"status":0,"user":{"uid":"string","chat_uid":"string","name":"string","portrait":"string"}}]
    */

   private RoomDetailsBean roomDetails;
   private List<MicInfoBean> mic_info;

   public static class RoomDetailsBean implements Serializable {
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
      private RoomsBean.OwnerBean owner;
      private int members_total;
      private int click_count;
      private String announcement;
      private List<RankingListBean> ranking_list;
      private List<RoomsBean.OwnerBean> member_list;

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

      public RoomsBean.OwnerBean getOwner() {
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

      public List<RoomsBean.OwnerBean> getMember_list() {
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

   public static class MicInfoBean implements Serializable {
      /**
       * index : 0
       * status : 0
       * user : {"uid":"string","chat_uid":"string","name":"string","portrait":"string"}
       */

      private int index;
      private int status;
      private RoomsBean.OwnerBean user;

      public int getIndex() {
         return index;
      }

      public int getStatus() {
         return status;
      }

      public RoomsBean.OwnerBean getUser() {
         return user;
      }
   }

}
