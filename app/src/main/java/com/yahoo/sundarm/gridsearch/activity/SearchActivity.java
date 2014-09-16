package com.yahoo.sundarm.gridsearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.yahoo.sundarm.gridsearch.adapters.EndlessScrollListener;
import com.yahoo.sundarm.gridsearch.adapters.ImageListAdapter;
import com.yahoo.sundarm.gridsearch.models.ImageInfo;
import gridsearch.sundarm.yahoo.com.gridsearch.R;


public class SearchActivity extends Activity {

    EditText editText;
    GridView searchGrid;
    private   ImageListAdapter adapter;
    private ArrayList<ImageInfo> imageList = new ArrayList<ImageInfo>();
    String type=null;
    String color=null;
    String size=null;
    String site=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpSearch();
        adapter = new ImageListAdapter(this, imageList);
        searchGrid.setAdapter(adapter);

        searchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(SearchActivity.this, FullImageActivity.class);
                ImageInfo image = imageList.get(position);
                intent.putExtra("Url", image.url);
                startActivity(intent);
            }
        });

        searchGrid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });



    }

    private void customLoadMoreDataFromApi(int offset) {
        retrieveImages(getUrl(offset));

    }

    private void setUpSearch() {
        editText = (EditText) findViewById(R.id.etText);
        searchGrid = (GridView) findViewById(R.id.gridView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


     public String getUrl (int pageNo)
     {
         String query = editText.getText().toString();
         StringBuilder url = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?q=" + query + "&v=1.0&rsz=6");
         if (site != null){
             url.append("&as_sitesearch=" + site);
         }

         if (color != null){
             url.append("&imgcolor=" + color);
         }
         if (size != null){
             url.append("&imgsz=" + size);
         }
         if (type != null){
             url.append("&=imgtype" + type);
         }
         if (pageNo > 1)
         {
             url.append("&start=" + (pageNo - 1 ) * 5);
         }
         return  url.toString();

     }
    public void searchImages(View view) {
//        Toast toast = Toast.makeText(this, "Entered Query " + editText.getText().toString(),
//                Toast.LENGTH_LONG);
//        toast.show();
        Log.d("DEBUG", "Search Images");

        AsyncHttpClient client = new AsyncHttpClient();

        Log.d("DEBUG", getUrl(1).toString());
       retrieveImages(getUrl(1).toString());
    }

    private void retrieveImages(String url)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)  {
                Log.d("ERROR", response.toString());
                try {
                    imageList.clear();
                    adapter.clear();
                    JSONArray resultArray = response.getJSONObject("responseData").getJSONArray("results");
                    adapter.addAll(ImageInfo.fromJSONArray(resultArray));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        });

    }

    public void addOptions(MenuItem mi)
    {

        Toast.makeText(this, "Options" , Toast.LENGTH_SHORT).show();
        Intent intentSetting = new Intent(SearchActivity.this, SettingActivity.class);
        startActivityForResult(intentSetting, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 1) {
            // Extract name value from result extras
            site = data.getExtras().getString("site");
            color = data.getExtras().getString("color");
            type = data.getExtras().getString("type");
            size = data.getExtras().getString("size");
            // Toast the name to display temporarily on screen

        }
    }


}
