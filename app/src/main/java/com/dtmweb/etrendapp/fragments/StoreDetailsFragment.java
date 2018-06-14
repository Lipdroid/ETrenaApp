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
import android.widget.TextView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.ProductGridAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.CategoryObject;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.StoreObject;
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
public class StoreDetailsFragment extends Fragment {


    private String store_id = null;
    private Context mContext = null;
    private String city = null;
    private String country = null;
    private String address = null;
    private String image_url = null;
    private String phone = null;

    private TextView tv_name = null;
    private TextView tv_phone = null;
    private TextView tv_address = null;
    private ImageView pro_image = null;
    private ImageView cover_image = null;

    private GridView gridview = null;
    private List<ProductObject> mListProduct = null;
    private ProductGridAdapter adapter = null;


    public StoreDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_details, container, false);
        mContext = getActivity();
        if (getArguments().getString(String.class.toString()) != null) {
            store_id = getArguments().getString(String.class.toString());
            requestToGetStore(store_id);
            requestToGetProductList(store_id);
        }
        tv_name = (TextView) root.findViewById(R.id.tv_name);
        tv_phone = (TextView) root.findViewById(R.id.tv_phone);
        tv_address = (TextView) root.findViewById(R.id.tv_address);
        gridview = (GridView) root.findViewById(R.id.gridview);
        pro_image = (ImageView) root.findViewById(R.id.pro_image);
        cover_image = (ImageView) root.findViewById(R.id.cover_image);
        ImageView btn_change_cover = (ImageView) root.findViewById(R.id.btn_change_cover);
        mContext = getActivity();

        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void requestToGetStore(String store_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_STORE_ID, store_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_STORE_DETAILS, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("GET Store", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("id")) {
                            StoreObject mStoreObj = new StoreObject();
                            if (jsonObject.has("name")) {
                                mStoreObj.setStore_name(jsonObject.getString("name"));
                            }
                            if (jsonObject.has("cover_photo")) {
                                mStoreObj.setCover_photo(jsonObject.getString("cover_photo"));
                            }
                            if (jsonObject.has("address")) {
                                address = jsonObject.getString("address");
                            }
                            if (jsonObject.has("image")) {
                                image_url = jsonObject.getString("image");
                            }

                            if (jsonObject.has("phone")) {
                                phone = jsonObject.getString("phone");
                            }

                            if (jsonObject.has("city") && !jsonObject.isNull("city")) {
                                JSONObject jsonCity = jsonObject.getJSONObject("city");
                                if (jsonCity.has("name")) {
                                    city = jsonCity.getString("name");
                                }
                                if (jsonCity.has("country")) {
                                    JSONObject jsonCountry = jsonCity.getJSONObject("country");
                                    if (jsonCountry.has("name")) {
                                        country = jsonCountry.getString("name");
                                    }

                                }
                            }


                            //setUp the view
                            setUpStore(mStoreObj);


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

    private void setUpStore(StoreObject mStoreObj) {
        tv_name.setText(mStoreObj.getStore_name());
        tv_phone.setText("+" + phone);
        tv_address.setText(city + "," + country);
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.default_profile_image_shop)
                .error(R.color.common_gray)
                .into(pro_image);
        Picasso.get()
                .load(mStoreObj.getCover_photo())
                .placeholder(R.drawable.default_profile_image_shop)
                .error(R.color.common_gray)
                .into(cover_image);
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

    private void requestToGetProductList(String store_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_STORE_ID, store_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_STORE_PRODUCT_LIST_BY_STORE, params, new AsyncCallback() {
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
                                if (jsonProduct.has("id")) {
                                    productObject.setId(jsonProduct.getString("id"));
                                }
                                if (jsonProduct.has("title")) {
                                    productObject.setTitle(jsonProduct.getString("title"));
                                }
                                if (jsonProduct.has("details")) {
                                    productObject.setDetails(jsonProduct.getString("details"));
                                }
                                if (jsonProduct.has("is_fav")) {
                                    productObject.setIs_favourite(jsonProduct.getString("is_fav"));
                                }
                                if (jsonProduct.has("discounted_price")) {
                                    productObject.setDiscounted_price(jsonProduct.getString("discounted_price"));
                                }
                                if (jsonProduct.has("images")) {
                                    JSONArray imagesArray = jsonProduct.getJSONArray("images");
                                    List<ImageObject> images = new ArrayList<>();
                                    for (int j = 0; j < imagesArray.length(); j++) {
                                        JSONObject jsonObjectImage = imagesArray.getJSONObject(j);
                                        ImageObject image = new ImageObject();
                                        image.setId(jsonObjectImage.getString("id"));
                                        image.setUrl(jsonObjectImage.getString("image"));
                                        images.add(image);
                                    }
                                    productObject.setImages(images);

                                }


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

    private void populateList(List<ProductObject> mListProduct) {
        adapter = new ProductGridAdapter(mContext, mListProduct);
        gridview.setAdapter(adapter);
    }


}
