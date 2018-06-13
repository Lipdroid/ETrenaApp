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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.ProductGridAdapter;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageProductFragment extends Fragment {


    private GridView gridview = null;
    private List<ProductObject> mListProduct = null;
    private ProductGridAdapter adapter = null;
    private Context mContext = null;
    private Button btn_add = null;

    public ManageProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_manage_product, container, false);
        gridview = (GridView) root.findViewById(R.id.gridview);
        btn_add = (Button) root.findViewById(R.id.btn_add);
        mContext = getActivity();
        requestToGetProductList();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).addFrag(Constants.FRAG_ADD_PRODUCT, null);
            }
        });
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList(List<ProductObject> mListProduct) {
        adapter = new ProductGridAdapter(mContext, mListProduct);
        gridview.setAdapter(adapter);
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
