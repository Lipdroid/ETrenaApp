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
        mHolder.tv_description.setText(productObject.getShort_description());
        mHolder.tv_price.setText("SAR " + productObject.getLowest_price());
        Picasso.get()
                .load(productObject.getImage_url())
                .placeholder(R.color.common_gray)
                .error(R.color.common_gray)
                .into(mHolder.product_image);

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

    private void removeProductFromFavListBuyer(String product_id){
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT_ID, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_REMOVE_FROM_FAV_LIST_BUYER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("remove favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(Constants.DATA);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            JSONObject jsonProduct = json.getJSONObject("product");
                            ProductObject productObject = new ProductObject();
                            productObject.setId(jsonProduct.getString("id"));
                            productObject.setTitle(jsonProduct.getString("title"));
                            productObject.setShort_description(jsonProduct.getString("short_description"));
                            productObject.setIs_favourite(jsonProduct.getString("is_favourite"));
                            productObject.setLowest_price(jsonProduct.getString("lowest_price"));
                            productObject.setImage_url(jsonProduct.getString("image_url"));
                        }
                        //remove the product and refresh the list

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

    private void requestToggleFav(String is_fav, String product_id){
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_IS_FAVOURITE, is_fav);
        params.put(Constants.PARAM_PRODUCT_ID, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_UPDATE_IS_FAVOURITE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("update favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonProduct = new JSONObject(result);
                        if(jsonProduct.has("id")) {
                            ProductObject productObject = new ProductObject();
                            productObject.setId(jsonProduct.getString("id"));
                            productObject.setTitle(jsonProduct.getString("title"));
                            productObject.setShort_description(jsonProduct.getString("short_description"));
                            productObject.setIs_favourite(jsonProduct.getString("is_favourite"));
                            productObject.setLowest_price(jsonProduct.getString("lowest_price"));
                            productObject.setImage_url(jsonProduct.getString("image_url"));
                        }
                        //refresh the list

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


