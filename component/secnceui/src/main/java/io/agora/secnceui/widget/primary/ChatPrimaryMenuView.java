package io.agora.secnceui.widget.primary;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.agora.secnceui.R;

public class ChatPrimaryMenuView extends RelativeLayout {

    protected Activity activity;
    protected InputMethodManager inputManager;
    private LinearLayoutCompat inputLayout;
    private LinearLayoutCompat menuLayout;
    private ArrayList<MenuItemModel> itemModels = new ArrayList<MenuItemModel>();
    private Map<Integer, MenuItemModel> itemMap = new HashMap();
    private MenuItemClickListener clickListener;
    private ConstraintLayout inputView;
    private EditText edContent;
    private ImageView icon;
    private TextView mSend;
    private boolean isShowEmoji;

    public ChatPrimaryMenuView(Context context) {
        this(context, null);
    }

    public ChatPrimaryMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatPrimaryMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_primary_menu_layout, this);
        activity = (Activity) context;
        inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        initViews();
    }

    private void initViews() {
        menuLayout = findViewById(R.id.menu_layout);
        inputLayout = findViewById(R.id.input_layout);
        inputView = findViewById(R.id.input_view);
        edContent = findViewById(R.id.input_edit_view);
        icon = findViewById(R.id.icon_emoji);
        mSend = findViewById(R.id.input_send);
        edContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("focus", "focused");
                    if (null != clickListener)
                        clickListener.onInputViewFocusChange(true);
                } else {
                    Log.d("focus", "focus lost");
                    if (null != clickListener)
                        clickListener.onInputViewFocusChange(false);
                    inputView.setVisibility(View.GONE);
                }
            }
        });
        inputLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView.setVisibility(View.VISIBLE);
                edContent.requestFocus();
            }
        });
        icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowEmoji = !isShowEmoji;
                if (isShowEmoji){
                    icon.setImageResource(R.drawable.icon_key);
                }else {
                    icon.setImageResource(R.drawable.icon_face);
                }
                // TODO: 2022/9/2  show emoji
                if (null != clickListener)
                    clickListener.onEmojiClick();
            }
        });
        mSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener)
                    clickListener.onSendMessage(edContent.getText().toString().trim());
            }
        });
        ViewGroup.LayoutParams  params =  menuLayout.getLayoutParams();
        params.width = itemModels.size();
        initMenu();
    }

    public void addMenu( int drawableRes, int itemId){
        registerMenuItem(drawableRes,itemId);
        if(!itemMap.containsKey(itemId)) {
            ImageView imageView = new ImageView(activity);
            imageView.setLayoutParams(new LayoutParams(dp2px(activity,38), dp2px(activity,38)));
            imageView.setPadding(dp2px(activity,7),dp2px(activity,7)
                    ,dp2px(activity,7),dp2px(activity,7));
            imageView.setImageResource(drawableRes);
            imageView.setBackgroundResource(R.drawable.bg_primary_menu_item_icon);
            imageView.setId(itemId);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != clickListener)
                    clickListener.onChatExtendMenuItemClick(v.getId(),v);
                }
            });
            menuLayout.addView(imageView);
        }
    }

    private void initMenu() {
        registerMenuItem(R.drawable.icon_mic,R.id.extend_item_mic);
        registerMenuItem(R.drawable.icon_handuphard,R.id.extend_item_hand_up);
        registerMenuItem(R.drawable.icon_eq,R.id.extend_item_eq);
        registerMenuItem(R.drawable.icon_gift,R.id.extend_item_gift);
        addView();
    }

    private void addView(){
        for (MenuItemModel itemModel : itemModels) {
            ImageView imageView = new ImageView(activity);
            LinearLayoutCompat.LayoutParams marginLayoutParams = new LinearLayoutCompat.LayoutParams(dp2px(activity,38), dp2px(activity,38));
            marginLayoutParams.leftMargin = dp2px(activity,5);
            marginLayoutParams.setMarginStart(dp2px(activity,5));
            imageView.setLayoutParams(marginLayoutParams);
            imageView.setPadding(dp2px(activity,7),dp2px(activity,7)
                    ,dp2px(activity,7),dp2px(activity,7));
            imageView.setImageResource(itemModel.image);
            imageView.setBackgroundResource(R.drawable.bg_primary_menu_item_icon);
            imageView.setId(itemModel.id);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != clickListener)
                    clickListener.onChatExtendMenuItemClick(v.getId(),v);
                }
            });
            menuLayout.addView(imageView);
        }
    }


    /**
         * register menu item
         *
         * @param drawableRes
         *            background of item
         * @param itemId
         *             id
         */
    public void registerMenuItem( int drawableRes, int itemId) {
        if(!itemMap.containsKey(itemId)) {
            MenuItemModel item = new MenuItemModel();
            item.image = drawableRes;
            item.id = itemId;
            itemModels.add(item);
        }
    }


    public static class MenuItemModel{
        public int image;
        public int id;
    }

    public void setMenuItemOnClickListener(MenuItemClickListener listener){
        this.clickListener = listener;
    }


    public EditText getEdContent(){
        return edContent;
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @SuppressWarnings("unused")
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
