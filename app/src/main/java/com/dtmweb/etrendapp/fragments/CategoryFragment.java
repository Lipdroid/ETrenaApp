package com.dtmweb.etrendapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.CategoryAdapter;
import com.dtmweb.etrendapp.adapters.FavProductAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.CategoryObject;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private Context mContext;
    private ArrayList<CategoryObject> mList = null;
    private ListView cate_lv = null;
    private CategoryAdapter adapter = null;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        mContext = getActivity();
        cate_lv = (ListView) root.findViewById(R.id.cate_lv);
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }



    private void requestCategories(){
        final HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_CATEGORY, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("CategoryFragment", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has(Constants.DATA)) {
                            mList = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants.DATA);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonCategory = jsonArray.getJSONObject(i);
                                CategoryObject categoryObject = new CategoryObject();
                                categoryObject.setId(jsonCategory.getString("id"));
                                categoryObject.setName(jsonCategory.getString("name"));
                                categoryObject.setIcon(jsonCategory.getString("icon"));

                                mList.add(categoryObject);
                            }
                            populateList(mList);

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

    private void populateList(ArrayList<CategoryObject> mList) {
        adapter = new CategoryAdapter(mContext, mList);
        cate_lv.setAdapter(adapter);
    }

    public void requestAPIs() {
        requestCategories();
    }
}
