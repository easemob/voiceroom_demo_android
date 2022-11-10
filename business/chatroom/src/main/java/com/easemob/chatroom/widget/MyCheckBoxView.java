package com.easemob.chatroom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.easemob.chatroom.R;

public class MyCheckBoxView extends AppCompatCheckBox {

    private int drawableW;
    private int drawableH;

    public MyCheckBoxView(Context context) {
        this(context, null);
    }

    public MyCheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyCheckBox);
        Drawable drawable = array.getDrawable(R.styleable.MyCheckBox_android_button);
        drawableW = (int) array.getDimension(R.styleable.MyCheckBox_attr_drawableWidth, 20);
        drawableH = (int) array.getDimension(R.styleable.MyCheckBox_attr_drawableHeight, 20);
        setCompoundDrawablePadding(4);
        setButtonDrawable(drawable);

    }

    public MyCheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override

    public void setButtonDrawable(int resId) {
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    //一定要在设置 Drawable 之前设置 否者不生效
    public void setDrawableSize(int drawbleW, int drawbleH) {
        this.drawableW = drawbleW;
        this.drawableH = drawbleH;

    }

    @Override
    public void setButtonDrawable(Drawable buttonDrawable) {
        if (buttonDrawable != null) {
            buttonDrawable.setBounds(0, 0, drawableW, drawableH);
            setCompoundDrawables(buttonDrawable, null, null, null);
        }
    }

}
