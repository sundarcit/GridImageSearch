package com.yahoo.sundarm.gridsearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import gridsearch.sundarm.yahoo.com.gridsearch.R;


public class SettingActivity extends Activity {

    Spinner sizeSpinner ;
    Spinner colorSpinner;
    Spinner typeSpinner;
    EditText etSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
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


    public void saveSettings(View view)
    {
        Toast.makeText(this, "SaveSettings", Toast.LENGTH_SHORT).show();
        sizeSpinner = (Spinner)findViewById(R.id.spSize);
        colorSpinner = (Spinner)findViewById(R.id.spColor);
        typeSpinner = (Spinner)findViewById(R.id.spType);
        etSite = (EditText)findViewById(R.id.etSite);
        String size = sizeSpinner.getSelectedItem().toString();
        String color = colorSpinner.getSelectedItem().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String site = etSite.getText().toString();
        Intent data = new Intent();

        data.putExtra("size", size);
        data.putExtra("color", color);
        data.putExtra("site", site);
        data.putExtra("type", type);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }
}
