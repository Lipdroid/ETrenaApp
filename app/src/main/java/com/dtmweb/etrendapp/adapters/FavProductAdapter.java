package com.dtmweb.etrendapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.FavProductHolder;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lipuhossain on 5/4/18.
 */

public class FavProductAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<ProductObject> mListData = null;
    private FavProductHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public FavProductAdapter(Context mContext, List<ProductObject> mListData) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;
        this.mListData = mListData;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_favourite, viewGroup, false);
            mHolder = new FavProductHolder();
            mHolder.main_root = (LinearLayout) convertView.findViewById(R.id.main_root);
            mHolder.product_image = (ImageView) convertView.findViewById(R.id.product_image);
            mHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHolder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            mHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            mHolder.btn_remove_fav = (TextView) convertView.findViewById(R.id.btn_remove_fav);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (FavProductHolder) convertView.getTag();
        }

        ProductObject productObject = mListData.get(position);
        mHolder.tv_title.setText(productObject.getTitle());
        mHolder.tv_description.setText(productObject.getDetails());
        mHolder.tv_price.setText("SAR " + productObject.getDiscounted_price());
        if (productObject.getImages().size() > 0) {
            ImageObject image = productObject.getImages().get(0);
            Picasso.get()
                    .load(image.getUrl())
                    .placeholder(R.color.common_gray)
                    .error(R.color.common_gray)
                    .into(mHolder.product_image);
        }

        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.btn_remove_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove from list
                ProductObject productObject = mListData.get(position);
                removeProductFromFavListBuyer(productObject.getId());

            }
        });
    }

    private void removeProductFromFavListBuyer(final String fav_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT_ID, fav_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_REMOVE_FROM_FAV_LIST, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("remove favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        //parse errors
                        parseErrors(params, jsonObject);
                        //if not error remove
                        for (ProductObject product : mListData
                                ) {
                            if (product.getFavourite_id().equals(fav_id)) {
                                //remove the product and refresh the list
                                mListData.remove(product);
                                notifyDataSetChanged();
                            }

                        }
//                        JSONArray jsonArray = jsonObject.getJSONArray(Constants.DATA);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject json = jsonArray.getJSONObject(i);
//                            JSONObject jsonProduct = json.getJSONObject("product");
//                            ProductObject productObject = new ProductObject();
//                            productObject.setId(jsonProduct.getString("id"));
//                            productObject.setTitle(jsonProduct.getString("title"));
//                            productObject.setShort_description(jsonProduct.getString("short_description"));
//                            productObject.setIs_favourite(jsonProduct.getString("is_favourite"));
//                            productObject.setLowest_price(jsonProduct.getString("lowest_price"));
//                            productObject.setImage_url(jsonProduct.getString("image_url"));
//                        }

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


}


