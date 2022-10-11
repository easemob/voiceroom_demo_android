package io.agora.secnceui.widget.expression;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import io.agora.secnceui.R;


public class ExpressionGridAdapter extends ArrayAdapter<ExpressionIcon>{

    public ExpressionGridAdapter(Context context, int textViewResourceId, List<ExpressionIcon> objects) {
        super(context, textViewResourceId, objects);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
           convertView = View.inflate(getContext(), R.layout.widget_row_expression, null);
        }
        ShapeableImageView imageView = (ShapeableImageView) convertView.findViewById(R.id.iv_expression);
        ExpressionIcon emojicon = getItem(position);
        if(emojicon.getIcon() != 0){
            imageView.setImageResource(emojicon.getIcon());
        }else if(emojicon.getIconPath() != null){
            Glide.with(getContext()).load(emojicon.getIconPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.icon_default_expression))
                    .into(imageView);
        }
        return convertView;
    }
    
}
