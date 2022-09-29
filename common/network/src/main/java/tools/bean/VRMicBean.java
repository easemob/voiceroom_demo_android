package tools.bean;

import java.io.Serializable;

public class VRMicBean implements Serializable {

   /**
    * index : 0
    * status : 0
    * member : {"uid":"string","chat_uid":"string","name":"string","portrait":"string","rtc_uid":0,"mic_index":0}
    */

   private int index;
   private int status;
   private MemberBean member;

   public int getIndex() {
      return index;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public MemberBean getMember() {
      return member;
   }

   public void setMember(MemberBean member) {
      this.member = member;
   }

   public static class MemberBean implements Serializable {
      /**
       * uid : string
       * chat_uid : string
       * member : string
       * portrait : string
       * rtc_uid : 0
       * mic_index : 0
       */

      private String uid;
      private String chat_uid;
      private String member;
      private String portrait;
      private int rtc_uid;
      private int mic_index;

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

      public String getMember() {
         return member;
      }

      public void setMember(String member) {
         this.member = member;
      }

      public String getPortrait() {
         return portrait;
      }

      public void setPortrait(String portrait) {
         this.portrait = portrait;
      }

      public int getRtc_uid() {
         return rtc_uid;
      }

      public void setRtc_uid(int rtc_uid) {
         this.rtc_uid = rtc_uid;
      }

      public int getMic_index() {
         return mic_index;
      }

      public void setMic_index(int mic_index) {
         this.mic_index = mic_index;
      }
   }
}
