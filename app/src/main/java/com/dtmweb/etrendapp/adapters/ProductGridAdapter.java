package com.dtmweb.etrendapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.ProductHolder;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mdmunirhossain on 3/13/18.
 */

public class ProductGridAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<ProductObject> mListData = null;
    private ProductHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public ProductGridAdapter(Context mContext, List<ProductObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_product, viewGroup, false);
            mHolder = new ProductHolder();
            mHolder.main_root = (RelativeLayout) convertView.findViewById(R.id.main_root);
            mHolder.product_image = (ImageView) convertView.findViewById(R.id.product_image);
            mHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHolder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            mHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            mHolder.fav_icon = (ImageView) convertView.findViewById(R.id.fav_icon);
            mHolder.btn_delete = (ImageView) convertView.findViewById(R.id.btn_delete);
            mHolder.btn_edit = (ImageView) convertView.findViewById(R.id.btn_edit);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ProductHolder) convertView.getTag();
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
        if (productObject.getIs_favourite().equals("true")) {
            mHolder.fav_icon.setImageResource(R.drawable.fav_selected);
        } else {
            mHolder.fav_icon.setImageResource(R.drawable.fav_icon);
        }

        if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
            mHolder.btn_edit.setVisibility(View.VISIBLE);
            mHolder.btn_delete.setVisibility(View.VISIBLE);
        } else {
            mHolder.btn_edit.setVisibility(View.GONE);
            mHolder.btn_delete.setVisibility(View.GONE);
        }


        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.main_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).addFrag(Constants.FRAG_PRODUCT_DETAILS, null);

            }
        });
        mHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Edit:", "Edit the product");
            }
        });
        mHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Delete:", "Delete the product");
            }
        });
        mHolder.fav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Fav:", "toggle favourite");
                ProductObject productObject = mListData.get(position);
                requetToAddFavListBuyer(productObject.getId());

            }
        });
    }

    private void requetToAddFavListBuyer(String product_id){
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT,product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_ADD_IN_FAV_LIST_BUYER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Buyer favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonProduct = jsonObject.getJSONObject("product");
                        ProductObject productObject = new ProductObject();
                        if(jsonProduct.has("id")) {
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


    private void requestToggleFav(String is_fav,String product_id){
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_IS_FAVOURITE, is_fav);
        params.put(Constants.PARAM_PRODUCT_ID,product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_UPDATE_IS_FAVOURITE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("update favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
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
