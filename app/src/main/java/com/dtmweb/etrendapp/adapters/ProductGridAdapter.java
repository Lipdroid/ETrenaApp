package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
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

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ProductHolder) convertView.getTag();
        }

        ProductObject productObject = mListData.get(position);
        mHolder.tv_title.setText(productObject.getTitle());
        mHolder.tv_description.setText(productObject.getShort_description());
        mHolder.tv_price.setText("SAR "+productObject.getLowest_price());
        Picasso.get()
                .load(productObject.getImage_url())
                .placeholder(R.color.white)
                .error(R.color.white)
                .into(mHolder.product_image);
        setListenersForViews(position);
        if(productObject.getIs_favourite().equals("true")){
            mHolder.fav_icon.setImageResource(R.drawable.fav_selected);
        }else {
            mHolder.fav_icon.setImageResource(R.drawable.fav_icon);
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
    }

}
