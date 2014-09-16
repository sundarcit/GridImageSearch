package com.yahoo.sundarm.gridsearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.sundarm.gridsearch.models.ImageInfo;

import java.util.List;

import gridsearch.sundarm.yahoo.com.gridsearch.R;

/**
 * Created by sundarm on 9/14/14.
 */
public class ImageListAdapter extends ArrayAdapter<ImageInfo>
 {

     public ImageListAdapter(Context context,  List<ImageInfo> objects) {
         super(context, android.R.layout.simple_list_item_1, objects);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        ImageInfo image = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }

         ImageView imageview = (ImageView) convertView.findViewById(R.id.ivImage);
         TextView  titleView = (TextView)convertView.findViewById(R.id.tvTitle);
         imageview.setImageResource(0);
         titleView.setText(Html.fromHtml(image.title));
         Picasso.with(getContext()).load(image.tbUrl).into(imageview);
         return convertView;
     }
 }
