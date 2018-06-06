package com.dtmweb.etrendapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dtmweb.etrendapp.adapters.CartAdapter;
import com.dtmweb.etrendapp.adapters.ChooseAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.UserObject;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChoosenActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private Context mContext = null;
    private ImageView btn_left_back = null;
    private TextView header_title = null;
    private ListView listView = null;
    private int mType = 2;
    private static final String TAG = "ChoosenActivity";
    private List<PlaceObject> mListPlace = null;
    private ChooseAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosen);
        mContext = this;
        findViews();
        initListenersForViews();
        mType = getIntent().getIntExtra(Integer.class.toString(), 2);

        if (mType == Constants.TYPE_CITY) {
            header_title.setText("Choose City");
            String countryId = getIntent().getStringExtra("countryId");
            requestForCities(countryId);
        } else if (mType == Constants.TYPE_COUNTRY) {
            header_title.setText("Choose Country");
            requestForCountries();
        }

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void initListenersForViews() {
        btn_left_back.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                PlaceObject selectedFromList = mListPlace.get(pos);
                Intent i = new Intent();
                i.putExtra(PlaceObject.class.toString(),
                        selectedFromList);
                setResult(Activity.RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_bottom,
                        R.anim.anim_slide_out_bottom);
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
        switch (view.getId()) {
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

    private void requestForCountries() {
        final HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_COUNTRY, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e(TAG, result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has(Constants.DATA)) {
                            mListPlace = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants.DATA);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonCountry = jsonArray.getJSONObject(i);
                                PlaceObject placeObject = new PlaceObject();
                                placeObject.setId(jsonCountry.getString("id"));
                                placeObject.setName(jsonCountry.getString("name"));
                                mListPlace.add(placeObject);
                            }
                            populateList(mListPlace);

                        } else {
                            //parse errors
                            GlobalUtils.parseErrors(mContext, params, jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }


            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();

            }

            @Override
            public void onException(Exception e) {

                GlobalUtils.dismissLoadingProgress();

            }
        });

        mRequestAsync.execute();

    }

    private void requestForCities(String countryId) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_COUNTRY, countryId);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_CITY, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e(TAG, result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has(Constants.DATA)) {
                            mListPlace = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants.DATA);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonCity = jsonArray.getJSONObject(i);
                                PlaceObject placeObject = new PlaceObject();
                                placeObject.setId(jsonCity.getString("id"));
                                placeObject.setName(jsonCity.getString("name"));
                                mListPlace.add(placeObject);
                            }
                            populateList(mListPlace);

                        } else {
                            //parse errors
                            GlobalUtils.parseErrors(mContext, params, jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }


            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();

            }

            @Override
            public void onException(Exception e) {

                GlobalUtils.dismissLoadingProgress();

            }
        });

        mRequestAsync.execute();

    }


    private void populateList(List<PlaceObject> mListPlace) {
        adapter = new ChooseAdapter(mContext, mListPlace);
        listView.setAdapter(adapter);
    }
}
