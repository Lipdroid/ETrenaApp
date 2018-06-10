package com.dtmweb.etrendapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.ProductGridAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.models.UserObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private GridView gridview = null;
    private List<ProductObject> mListProduct = null;
    private ProductGridAdapter adapter = null;
    private Context mContext = null;
    private UserObject mUserObj = null;
    private StoreObject mStoreObj = null;
    private TextView tv_name = null;
    private TextView tv_phone = null;
    private TextView tv_address = null;
    private ImageView pro_image = null;
    private ImageView cover_image = null;
    private Button btn_add = null;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = null;
        switch (GlobalUtils.user_type) {
            case Constants.CATEGORY_BUYER:
                root = inflater.inflate(R.layout.fragment_profile_buyer, container, false);
                tv_name = (TextView) root.findViewById(R.id.tv_name);
                tv_phone = (TextView) root.findViewById(R.id.tv_phone);
                tv_address = (TextView) root.findViewById(R.id.tv_address);
                mUserObj = GlobalUtils.getCurrentUser();
                if (mUserObj != null) {
                    tv_name.setText(mUserObj.getUsername());
                    tv_phone.setText("+" + mUserObj.getContact_no());
                    tv_address.setText(mUserObj.getCity() + "," + mUserObj.getCountry());
                }
                break;
            case Constants.CATEGORY_SELLER:
                root = inflater.inflate(R.layout.fragment_profile_seller, container, false);
                tv_name = (TextView) root.findViewById(R.id.tv_name);
                tv_phone = (TextView) root.findViewById(R.id.tv_phone);
                tv_address = (TextView) root.findViewById(R.id.tv_address);
                gridview = (GridView) root.findViewById(R.id.gridview);
                pro_image = (ImageView) root.findViewById(R.id.pro_image);
                cover_image = (ImageView)root.findViewById(R.id.cover_image);
                btn_add = (Button) root.findViewById(R.id.btn_add);
                mContext = getActivity();
                mUserObj = GlobalUtils.getCurrentUser();
                if (mUserObj != null)
                    mStoreObj = mUserObj.getStoreObject();
                if (mStoreObj != null) {
                    setUpStoreView();
                } else {
                    requestToGetStoreInfo();
                }
                requestToGetProductList();
                tv_phone.setText("+" + mUserObj.getContact_no());
                tv_address.setText(mUserObj.getCity() + "," + mUserObj.getCountry());
                Picasso.get()
                        .load(mUserObj.getPro_img())
                        .placeholder(R.drawable.default_profile_image_shop)
                        .error(R.color.common_gray)
                        .into(pro_image);
                break;
            case Constants.CATEGORY_NON_LOGGED:
                //no need to implement anything here
                root = inflater.inflate(R.layout.fragment_profile_buyer, container, false);
                break;
        }
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }


    private void requestToGetStoreInfo() {
        final HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_STORE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Store API", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("id")) {
                            mStoreObj = new StoreObject();
                            if (jsonObject.has("name")) {
                                mStoreObj.setStore_name(jsonObject.getString("name"));
                            }
                            if (jsonObject.has("cover_photo")) {
                                mStoreObj.setCover_photo(jsonObject.getString("cover_photo"));
                            }
                            if (jsonObject.has("is_active")) {
                                mStoreObj.setIs_Active(jsonObject.getBoolean("is_active"));
                            }
                            if (jsonObject.has("is_subscribed")) {
                                mStoreObj.setIs_subscribed(jsonObject.getBoolean("is_subscribed"));
                            }
                            if (jsonObject.has("bank_name")) {
                                mStoreObj.setBank_name(jsonObject.getString("bank_name"));
                            }
                            if (jsonObject.has("account_name")) {
                                mStoreObj.setBank_acc_name(jsonObject.getString("account_name"));
                            }
                            if (jsonObject.has("account_number")) {
                                mStoreObj.setBank_acc_number(jsonObject.getString("account_number"));
                            }

                            mUserObj.setStoreObject(mStoreObj);

                            //save the current user
                            GlobalUtils.saveCurrentUser(mUserObj);
                            //save the store
                            GlobalUtils.saveCurrentStore(mStoreObj);

                            setUpStoreView();
                        } else {
                            //parse errors
                            parseErrors(params, jsonObject);
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


    private void requestToGetProductList() {
        final HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_STORE_PRODUCT_LIST, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Product List API", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has(Constants.DATA)) {
                            mListProduct = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants.DATA);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonProduct = jsonArray.getJSONObject(i);
                                ProductObject productObject = new ProductObject();
                                productObject.setId(jsonProduct.getString("id"));
                                productObject.setTitle(jsonProduct.getString("title"));
                                productObject.setShort_description(jsonProduct.getString("short_description"));
                                productObject.setIs_favourite(jsonProduct.getString("is_favourite"));
                                productObject.setLowest_price(jsonProduct.getString("lowest_price"));
                                productObject.setImage_url(jsonProduct.getString("image_url"));

                                mListProduct.add(productObject);
                            }
                            populateList(mListProduct);
                        } else {

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

    private void parseErrors(HashMap<String, Object> requestBody, JSONObject jObjError) {
        try {
            for (String key : requestBody.keySet()) {
                if (jObjError.has(key)) {
                    String error = null;
                    error = jObjError.getJSONArray(key).get(0).toString();
                    Log.e("Error ", this.getClass().getSimpleName() + error);
                    GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                    return;
                }
            }
            if (jObjError.has("non_field_errors")) {
                String error = jObjError.getJSONArray("non_field_errors").get(0).toString();
                if (error != null) {
                    GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                    return;
                }
            }

            if (jObjError.has("detail")) {
                String error = jObjError.getString("detail");
                GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setUpStoreView() {
        //set the values
        tv_name.setText(mStoreObj.getStore_name());
        if(mStoreObj.getCover_photo() != null && !mStoreObj.getCover_photo().equals("") && !mStoreObj.getCover_photo().equals("null")){
            Picasso.get()
                    .load(mStoreObj.getCover_photo())
                    .placeholder(R.color.common_gray)
                    .error(R.color.common_gray)
                    .into(cover_image);
        }else{
            cover_image.setImageResource(R.drawable.icon_no_cover);
        }

    }

    private void populateList(List<ProductObject> mListProduct) {
        adapter = new ProductGridAdapter(mContext, mListProduct);
        gridview.setAdapter(adapter);
        if(mListProduct.size() == 0){
            btn_add.setVisibility(View.VISIBLE);
        }else {
            btn_add.setVisibility(View.GONE);
        }
    }


}
