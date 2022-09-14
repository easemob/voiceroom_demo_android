package io.agora.secnceui.widget.expression;


import io.agora.secnceui.R;

public class DefaultExpressionData {
    
    private static String[] emojis = new String[]{
        SmileUtils.ee_1,
        SmileUtils.ee_2,
        SmileUtils.ee_3,
        SmileUtils.ee_4,
        SmileUtils.ee_5,
        SmileUtils.ee_6,
        SmileUtils.ee_7,
        SmileUtils.ee_8,
        SmileUtils.ee_9,
        SmileUtils.ee_10,
        SmileUtils.ee_11,
        SmileUtils.ee_12,
        SmileUtils.ee_13,
        SmileUtils.ee_14,
        SmileUtils.ee_15,
        SmileUtils.ee_16,
        SmileUtils.ee_17,
        SmileUtils.ee_18,
        SmileUtils.ee_19,
        SmileUtils.ee_20,
        SmileUtils.ee_21,
        SmileUtils.ee_22,
        SmileUtils.ee_23,
        SmileUtils.ee_24,
        SmileUtils.ee_25,
        SmileUtils.ee_26,
        SmileUtils.ee_27,
        SmileUtils.ee_28,
        SmileUtils.ee_29,
        SmileUtils.ee_30,
        SmileUtils.ee_31,
        SmileUtils.ee_32,
        SmileUtils.ee_33,
        SmileUtils.ee_34,
        SmileUtils.ee_35,
        SmileUtils.ee_36,
        SmileUtils.ee_37,
        SmileUtils.ee_38,
        SmileUtils.ee_39,
        SmileUtils.ee_40,
        SmileUtils.ee_41,
        SmileUtils.ee_42,
        SmileUtils.ee_43,
        SmileUtils.ee_44,
        SmileUtils.ee_45,
        SmileUtils.ee_46,
        SmileUtils.ee_47,
        SmileUtils.ee_48,
        SmileUtils.ee_49,
        SmileUtils.ee_50,
        SmileUtils.ee_51,
        SmileUtils.ee_52,
    };
    
    private static int[] icons = new int[]{
        R.drawable.ee_1,
        R.drawable.ee_2,  
        R.drawable.ee_3,  
        R.drawable.ee_4,  
        R.drawable.ee_5,  
        R.drawable.ee_6,  
        R.drawable.ee_7,  
        R.drawable.ee_8,  
        R.drawable.ee_9,  
        R.drawable.ee_10,  
        R.drawable.ee_11,  
        R.drawable.ee_12,  
        R.drawable.ee_13,  
        R.drawable.ee_14,  
        R.drawable.ee_15,  
        R.drawable.ee_16,  
        R.drawable.ee_17,  
        R.drawable.ee_18,  
        R.drawable.ee_19,  
        R.drawable.ee_20,  
        R.drawable.ee_21,  
        R.drawable.ee_22,  
        R.drawable.ee_23,  
        R.drawable.ee_24,  
        R.drawable.ee_25,  
        R.drawable.ee_26,  
        R.drawable.ee_27,  
        R.drawable.ee_28,  
        R.drawable.ee_29,  
        R.drawable.ee_30,  
        R.drawable.ee_31,  
        R.drawable.ee_32,  
        R.drawable.ee_33,  
        R.drawable.ee_34,  
        R.drawable.ee_35,
        R.drawable.ee_36,
        R.drawable.ee_37,
        R.drawable.ee_38,
        R.drawable.ee_39,
        R.drawable.ee_40,
        R.drawable.ee_41,
        R.drawable.ee_42,
        R.drawable.ee_43,
        R.drawable.ee_44,
        R.drawable.ee_45,
        R.drawable.ee_46,
        R.drawable.ee_47,
        R.drawable.ee_48,
        R.drawable.ee_49,
        R.drawable.ee_50,
        R.drawable.ee_51,
        R.drawable.ee_52,

    };
    
    
    private static final ExpressionIcon[] DATA = createData();
    
    private static ExpressionIcon[] createData(){
        ExpressionIcon[] datas = new ExpressionIcon[icons.length];
        for(int i = 0; i < icons.length; i++){
            datas[i] = new ExpressionIcon(icons[i], emojis[i], ExpressionIcon.Type.NORMAL);
        }
        return datas;
    }
    
    public static ExpressionIcon[] getData(){
        return DATA;
    }
}
