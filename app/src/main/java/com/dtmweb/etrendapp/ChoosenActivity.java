package com.dtmweb.etrendapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;

public class ChoosenActivity extends AppCompatActivity implements View.OnClickListener{
    private CorrectSizeUtil mCorrectSize = null;
    private Context mContext = null;
    private ImageView btn_left_back = null;
    private TextView header_title = null;
    private ListView listView = null;
    private int mType = 2;
    private ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosen);
        mContext = this;
        findViews();
        initListenersForViews();
        mType = getIntent().getIntExtra(Integer.class.toString(), 2);

        if(mType == Constants.TYPE_CITY){
            header_title.setText("Choose City");
            String[] items = { "Some City" };
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, items);
        }else if(mType == Constants.TYPE_COUNTRY){
            header_title.setText("Choose Country");
            String[] items = { "Saudi Arabia", "Qatar", "Dubai" };
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, items);
        }

        listView.setAdapter(adapter);
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void initListenersForViews() {
        btn_left_back.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String selectedFromList =(listView.getItemAtPosition(pos).toString());
                if(mType == Constants.TYPE_CITY){
                    Intent i = new Intent();
                    i.putExtra("city",
                            selectedFromList);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_bottom,
                            R.anim.anim_slide_out_bottom);
                }else if(mType == Constants.TYPE_COUNTRY){
                    Intent i = new Intent();
                    i.putExtra("country",
                            selectedFromList);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_bottom,
                            R.anim.anim_slide_out_bottom);
                }
            }
        });

    }

    private void findViews() {
        btn_left_back = (ImageView) findViewById(R.id.btn_left_back);
        header_title = (TextView) findViewById(R.id.header_title);
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_left_back:
                afterClickBack();
                break;
        }
    }

    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_bottom,
                R.anim.anim_slide_out_bottom);
    }
}
