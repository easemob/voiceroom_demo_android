package tools.bean;

import java.io.Serializable;
import java.util.List;

public class VRMicListBean implements Serializable {

    /**
     * total : 0
     * cursor : null
     * apply_list : [{"index":null,"member":{"uid":"string","name":"string","portrait":"string"},"created_at":0}]
     */

    private int total;
    private String cursor;
    private List<ApplyListBean> apply_list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public List<ApplyListBean> getApply_list() {
        return apply_list;
    }

    public void setApply_list(List<ApplyListBean> apply_list) {
        this.apply_list = apply_list;
    }

    public static class ApplyListBean implements Serializable {
        /**
         * index : null
         * member : {"uid":"string","name":"string","portrait":"string"}
         * created_at : 0
         */

        private Object index;
        private MemberBean member;
        private int created_at;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public static class MemberBean implements Serializable {
            /**
             * uid : string
             * name : string
             * portrait : string
             */

            private String uid;
            private String name;
            private String portrait;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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
}
