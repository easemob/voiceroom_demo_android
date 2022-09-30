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
        private VMemberBean member;
        private int created_at;

        public VMemberBean getMember() {
            return member;
        }

        public void setMember(VMemberBean member) {
            this.member = member;
        }
    }
}
