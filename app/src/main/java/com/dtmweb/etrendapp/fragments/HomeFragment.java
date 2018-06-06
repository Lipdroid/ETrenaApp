package com.dtmweb.etrendapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.HomeAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.UserObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ListView banner_lv = null;
    private List<ProductObject> mListBanner = null;
    private HomeAdapter adapter = null;
    private Context mContext = null;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        banner_lv = (ListView) root.findViewById(R.id.banner_lv);
        mContext = getActivity();
        populateList();
        requestBannerApi();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList() {
        adapter = new HomeAdapter(mContext, mListBanner);
        banner_lv.setAdapter(adapter);
    }

    private void requestBannerApi(){
        final HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_BANNER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Banner API", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

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

}
