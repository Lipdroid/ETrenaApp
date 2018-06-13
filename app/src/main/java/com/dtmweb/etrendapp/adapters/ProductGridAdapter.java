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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.ProductHolder;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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
        if (productObject.getIs_favourite().equals("true")) {
            mHolder.fav_icon.setImageResource(R.drawable.fav_selected);
        } else {
            mHolder.fav_icon.setImageResource(R.drawable.fav_icon);
        }

        if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
            mHolder.btn_edit.setVisibility(View.VISIBLE);
            mHolder.btn_delete.setVisibility(View.VISIBLE);
        } else if (GlobalUtils.user_type.equals(Constants.CATEGORY_BUYER)) {
            mHolder.btn_edit.setVisibility(View.GONE);
            mHolder.btn_delete.setVisibility(View.GONE);
        } else {
            mHolder.btn_edit.setVisibility(View.GONE);
            mHolder.btn_delete.setVisibility(View.GONE);
            mHolder.fav_icon.setVisibility(View.GONE);
        }


        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.main_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductObject productObject = mListData.get(position);
                ((MainActivity) mContext).addFrag(Constants.FRAG_PRODUCT_DETAILS, productObject.getId());

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
                if(productObject.getIs_favourite().equals("false")) {
                    requetToAddFavListBuyer(productObject.getId());
                }else{
                    removeProductFromFavListBuyer(productObject.getId());
                }


            }
        });
    }

    private void removeProductFromFavListBuyer(final String product_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT_ID, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_REMOVE_FROM_FAV_LIST, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("remove favourite", result);
                try {
                    for (ProductObject product : mListData
                            ) {
                        if (product.getId().equals(product_id)) {
                            //set is fav false
                            product.setIs_favourite("false");
                            notifyDataSetChanged();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
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


    private void requetToAddFavListBuyer(final String product_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_ADD_IN_FAV_LIST, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Buyer favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("product")) {
                            JSONObject jsonProduct = jsonObject.getJSONObject("product");
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

                            //if not error then update
                            for (ProductObject product : mListData
                                    ) {
                                if (product.getId().equals(product_id)) {
                                    //toogle update the product is favourite
                                    product.setIs_favourite(productObject.getIs_favourite());
                                    notifyDataSetChanged();
                                }

                            }
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
