package com.yahoo.sundarm.gridsearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sundarm on 9/14/14.
 */
public class ImageInfo {
    public String url;
    public String title;
    public String tbUrl;



    public ImageInfo(String url, String title, String tbUrl) {
        this.url = url;
        this.tbUrl = tbUrl;
        this.title = title;
    }

    public static ArrayList<ImageInfo> fromJSONArray(JSONArray resultArray) {
        ArrayList<ImageInfo> imageInfoArrayList = new ArrayList<ImageInfo>();
        try {


            for (int i = 0 ; i < resultArray.length(); i++) {
                JSONObject imageJson = resultArray.getJSONObject(i);
                String url = imageJson.getString("url");
                String title = imageJson.getString("title");
                String tbUrl = imageJson.getString("tbUrl");
                ImageInfo image = new ImageInfo(url, title, tbUrl);
                imageInfoArrayList.add(image);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imageInfoArrayList;
    }


}
