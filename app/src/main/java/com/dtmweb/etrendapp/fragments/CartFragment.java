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
import android.widget.ImageView;
import android.widget.ListView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.CartAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.CartObject;
import com.dtmweb.etrendapp.models.ImageObject;
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
public class CartFragment extends Fragment {
    private ListView cart_lv = null;
    private List<CartObject> mListCartProduct = null;
    private CartAdapter adapter = null;
    private Context mContext = null;
    private ImageView image_no_data = null;
    private Button btn_check_out = null;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        cart_lv = (ListView) root.findViewById(R.id.cart_lv);
        image_no_data = (ImageView) root.findViewById(R.id.image_no_data);
        btn_check_out = (Button) root.findViewById(R.id.btn_check_out);
        mContext = getActivity();
        btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open address fragment
                ((MainActivity) mContext).addFrag(Constants.FRAG_BILLING_ADDRESS, null);

            }
        });
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList(List<CartObject> mListCartProduct) {
        adapter = new CartAdapter(mContext, mListCartProduct);
        cart_lv.setAdapter(adapter);
        if(mListCartProduct != null && mListCartProduct.size() == 0){
            image_no_data.setVisibility(View.VISIBLE);
        }
    }

    public void requestAPIs() {
        //make the API call here
        requestGetCart();
    }

    private void requestGetCart() {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_IS_ORDERED, "false");

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_CART, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Get Cart", result);
                if (result != null) {
                    try {
                        JSONObject responeJson = new JSONObject(result);
                        if (responeJson.has(Constants.DATA)) {
                            mListCartProduct = new ArrayList<>();
                            JSONArray jsonArray = responeJson.getJSONArray(Constants.DATA);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject.has("id")) {
                                    CartObject cartObject = new CartObject();
                                    cartObject.setCart_id(jsonObject.getString("id"));
                                    if (jsonObject.has("product")) {
                                        JSONObject jsonProduct = jsonObject.getJSONObject("product");
                                        if (jsonProduct.has("id")) {
                                            cartObject.setId(jsonProduct.getString("id"));
                                        }
                                        if (jsonProduct.has("title")) {
                                            cartObject.setTitle(jsonProduct.getString("title"));
                                        }
                                        if (jsonProduct.has("details")) {
                                            cartObject.setDetails(jsonProduct.getString("details"));
                                        }
                                    }

                                    if (jsonObject.has("product_image")) {
                                        JSONObject jsonProductImage = jsonObject.getJSONObject("product_image");
                                        ImageObject imageObject = new ImageObject();
                                        imageObject.setId(jsonProductImage.getString("id"));
                                        imageObject.setUrl(jsonProductImage.getString("image"));
                                        cartObject.setImage(imageObject);
                                    }

                                    if (jsonObject.has("discounted_price")) {
                                        cartObject.setDiscounted_price(jsonObject.getString("discounted_price"));
                                    }
                                    if (jsonObject.has("discount")) {
                                        cartObject.setDiscount(jsonObject.getString("discount"));
                                    }
                                    if (jsonObject.has("quantity")) {
                                        cartObject.setQuantity(jsonObject.getString("quantity"));
                                    }

                                    if (jsonObject.has("attribute")) {
                                        JSONObject jsonAttribute = jsonObject.getJSONObject("attribute");
                                        cartObject.setAttribute_id(jsonAttribute.getString("id"));
                                        cartObject.setAttribute_name(jsonAttribute.getString("name"));
                                    }


                                    if (jsonObject.has("attribute_value")) {
                                        cartObject.setAttributeValue(jsonObject.getString("attribute_value"));
                                    }

                                    if (jsonObject.has("is_ordered")) {
                                        cartObject.setIs_ordered(jsonObject.getString("is_ordered"));
                                    }

                                    if (jsonObject.has("is_paid")) {
                                        cartObject.setIs_paid(jsonObject.getString("is_paid"));
                                    }
                                    if (jsonObject.has("created")) {
                                        cartObject.setCreated(jsonObject.getString("created"));
                                    }
                                    mListCartProduct.add(cartObject);
                                }
                            }
                            populateList(mListCartProduct);
                        }else{

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
