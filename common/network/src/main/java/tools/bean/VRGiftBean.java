package tools.bean;

import java.io.Serializable;

public class VRGiftBean implements Serializable {

   /**
    * ranking_list : {"name":"string","portrait":"string","amount":0}
    * total : 0
    * cursor : string
    */

   private RankingListBean ranking_list;
   private int total;
   private String cursor;
   /**
    * gift_id : string
    * num : 0
    * to_uid : string
    */

   private String gift_id;
   private int num;
   private String to_uid;

   public RankingListBean getRanking_list() {
      return ranking_list;
   }

   public int getTotal() {
      return total;
   }

   public String getCursor() {
      return cursor;
   }

   public String getGift_id() {
      return gift_id;
   }

   public int getNum() {
      return num;
   }

   public String getTo_uid() {
      return to_uid;
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
