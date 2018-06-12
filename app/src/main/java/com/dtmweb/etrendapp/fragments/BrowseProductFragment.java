package com.dtmweb.etrendapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.ProductGridAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.ImageObject;
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
public class BrowseProductFragment extends Fragment {
    private GridView gridview = null;
    private List<ProductObject> mListProduct = null;
    private ProductGridAdapter adapter = null;
    private Context mContext = null;
    private String category;

    public BrowseProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_browse_product, container, false);
        if (getArguments().getString(String.class.toString()) != null) {
            category = getArguments().getString(String.class.toString());
            requestProductsApi(category);
        }
        gridview = (GridView) root.findViewById(R.id.gridview);
        mContext = getActivity();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList(List<ProductObject> mListProduct) {
        adapter = new ProductGridAdapter(mContext, mListProduct);
        gridview.setAdapter(adapter);
    }

    private void requestProductsApi(String category) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_CATEGORY, category);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_PRODUCTS, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Products API", result);
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

}
