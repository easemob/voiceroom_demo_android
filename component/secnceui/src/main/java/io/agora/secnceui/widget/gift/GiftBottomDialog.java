package io.agora.secnceui.widget.gift;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;
import java.util.List;
import io.agora.baseui.popupwindow.CommonPopupWindow;
import io.agora.secnceui.R;
import io.agora.secnceui.bean.GiftBean;
import io.agora.secnceui.databinding.ChatroomGiftPopupwindowLayoutBinding;
import io.agora.secnceui.utils.DeviceUtils;


public class GiftBottomDialog extends BottomDialogFragment implements View.OnClickListener {
    private int currentIndex = 0;//当前页面,默认首页
    private LinearLayoutCompat linearLayout;
    private ViewPager2 mViewPager;
    private GiftFragmentAdapter adapter;
    private AppCompatTextView send;
    private AppCompatTextView count;
    private OnSendClickListener listener;
    private List<GiftBean> list;
    private ConstraintLayout countLayout;
    private AppCompatTextView total_count;
    private int GiftNum = 1;
    private GiftBean giftBean;
    private ImageView icon;


    public static GiftBottomDialog getNewInstance() {
        return new GiftBottomDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.chatroom_gift_dialog_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        linearLayout = findViewById(R.id.pager_dots);
        mViewPager = findViewById(R.id.view_pager);
        countLayout = findViewById(R.id.gift_count_layout);
        count = findViewById(R.id.count);
        total_count = findViewById(R.id.total_count);
        icon = findViewById(R.id.icon);

        send = findViewById(R.id.send);
        adapter = new GiftFragmentAdapter(mContext);
        mViewPager.setAdapter(adapter);
        //设置缓冲页数
        mViewPager.setOffscreenPageLimit(1);

    }

    @Override
    public void initData() {
        super.initData();
        list = GiftRepository.getDefaultGifts(getContext());
        initPoints();
    }

    @Override
    public void initListener() {
        super.initListener();
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //当前页卡被选择时,position为当前页数
                linearLayout.getChildAt(position).setEnabled(false);//不可点击
                linearLayout.getChildAt(currentIndex).setEnabled(true);//恢复之前页面状态
                currentIndex = position;
                if (currentIndex == 0){
                    linearLayout.getChildAt(currentIndex).setEnabled(false);
                }else if (currentIndex == 1){

                }

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftBean != null && listener != null)
                listener.SendGift(v,giftBean);
            }
        });
        countLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v);
            }
        });
        adapter.setOnVpFragmentItemListener(new GiftFragmentAdapter.OnVpFragmentItemListener() {
            @Override
            public void onVpFragmentItem(int position, Object bean) {
                giftBean = (GiftBean) bean;
                check(giftBean.getPrice());
                reset();
            }

            @Override
            public void onFirstData(GiftBean bean) {
                giftBean = bean;
                check(bean.getPrice());
                if (getActivity() != null && isAdded())
                total_count.setText(getActivity().getString(R.string.dialog_gift_total_count,bean.getPrice()));
                count.setText("1");
            }
        });
    }

    private void isShowPop(boolean isShow){
        if (isShow){
            icon.setImageResource(R.drawable.icon_arrow_down);
        }else {
            icon.setImageResource(R.drawable.icon_arrow_up);
        }
    }

    @Override
    public void onClick(View v) {
        mViewPager.setCurrentItem((int) v.getTag());
    }

    private void initPoints() {
        addViewPagerDots(linearLayout,Math.round((list.size()/4)+0.5f));
    }

    /**
     * 向一个线性布局里添加小圆点
     * @param llGuideGroup
     * @param count 要添加多少个小圆点
     */
    public void addViewPagerDots(LinearLayoutCompat llGuideGroup, int count){
        Log.e("addViewPagerDots","count: " + count);
        if(llGuideGroup == null || count<1 || getContext() == null) return;
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(
                DeviceUtils.dp2px(getContext(),5), DeviceUtils.dp2px(getContext(),5));
        lp.leftMargin = DeviceUtils.dp2px(getContext(),5);
        lp.rightMargin = DeviceUtils.dp2px(getContext(),5);
        for(int i=0;i<count;i++){
            ImageView imageView = new ImageView(llGuideGroup.getContext());
            imageView.setLayoutParams(lp);
            imageView.setEnabled(true);//设置当前状态为允许点击（可点，灰色）
            imageView.setOnClickListener(this);//设置点击监听
            //额外设置一个标识符，以便点击小圆点时跳转对应页面
            imageView.setTag(i);//标识符与圆点顺序一致
            imageView.setBackgroundResource(R.drawable.bg_gift_vp_point);
            llGuideGroup.addView(imageView);
        }
    }


    public void setOnConfirmClickListener(OnSendClickListener listener) {
        this.listener = listener;
    }

   private void showPop(View itemView){
       //Gets the coordinates attached to the view
       int[] location = new int[2];
       itemView.getLocationInWindow(location);
       if (getContext() != null){
           new CommonPopupWindow.ViewDataBindingBuilder<ChatroomGiftPopupwindowLayoutBinding>()
                   .width(DeviceUtils.dp2px(getContext(),120))
                   .height(DeviceUtils.dp2px(getContext(),186))
                   .outsideTouchable(true)
                   .focusable(true)
                   .clippingEnabled(false)
                   .alpha(0.618f)
                   .layoutId(getContext(),R.layout.chatroom_gift_popupwindow_layout)
                   .intercept(new CommonPopupWindow.ViewEvent<ChatroomGiftPopupwindowLayoutBinding>() {
                       @Override
                       public void getView(CommonPopupWindow popupWindow, ChatroomGiftPopupwindowLayoutBinding view) {
                           isShowPop(true);
                           String[] data={"999","599","199","99", "9", "1"};
                           GiftAdapter adapter = new GiftAdapter(getContext(),1,data);
                           view.listView.setAdapter(adapter);
                           adapter.setOnItemClickListener(new GiftAdapter.OnItemClickListener() {
                               @Override
                               public void OnItemClick(int position, String num) {
                                   GiftNum = Integer.parseInt(num);
                                   int total = GiftNum * Integer.parseInt(giftBean.getPrice());
                                   total_count.setText(getString(R.string.dialog_gift_total_count,String.valueOf(total)));
                                   if (giftBean != null && GiftNum >= 1){
                                       giftBean.setNum(GiftNum);
                                       count.setText(num);
                                       //礼物金额大于100的 数量只能选1
                                       if (Integer.parseInt(giftBean.getPrice()) >= 100){
                                           reset();
                                       }
                                   }
                                   popupWindow.dismiss();
                               }
                           });
                       }
                   })
                   .onDismissListener(new PopupWindow.OnDismissListener() {
                       @Override
                       public void onDismiss() {
                           /*每次dismiss都会回调*/
                           isShowPop(false);
                       }
                   })
                   .build(getContext())
                   .showAtLocation(itemView, Gravity.NO_GRAVITY,
                           location[0]-DeviceUtils.dp2px(getContext(),60)/3,
                           location[1]-DeviceUtils.dp2px(getContext(),186));
       }
   }

   public void reset(){
       count.setText("1");
        if (null != giftBean){
            giftBean.setNum(1);
            total_count.setText(getString(R.string.dialog_gift_total_count,giftBean.getPrice()));
        }
   }

   public void check(String price){
       if (Integer.parseInt(price) < 100){
           icon.setAlpha(1.0f);
           count.setAlpha(1.0f);
           countLayout.setEnabled(true);
       }else {
           icon.setAlpha(0.2f);
           count.setAlpha(0.2f);
           countLayout.setEnabled(false);
       }
   }

   public void setSendEnable(boolean enable){
       send.setEnabled(enable);
   }
}
