package com.easemob.buddy.tool;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.easemob.buddy.R;

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    private Context mContext;

    public CountDownTimerUtils(Context context,TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.mContext = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(String.format(mContext.getString(R.string.chatroom_code_timer),millisUntilFinished / 1000 + " s"));  //设置倒计时时间
        // mTextView.setBackgroundResource(R.drawable.bg_identify_code_press); //设置按钮为灰色，这时是不能点击的
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(mContext.getColor(R.color.color_979CBB));
        spannableString.setSpan(span, 0, spannableString.length()-1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为灰色
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText(mContext.getString(R.string.chatroom_login_get_code));
        mTextView.setTextColor(Color.parseColor("#009FFF"));
        mTextView.setClickable(true);//重新获得点击
        // mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);  //还原背景色
    }
}