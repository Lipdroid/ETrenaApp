package com.dtmweb.etrendapp.adapters;

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

import java.util.List;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.ProductHolder;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

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
    }

}
