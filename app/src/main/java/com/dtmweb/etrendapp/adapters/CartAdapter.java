package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.CartHolder;
import com.dtmweb.etrendapp.holders.FavProductHolder;
import com.dtmweb.etrendapp.models.CartObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mdmunirhossain on 5/9/18.
 */

public class CartAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<CartObject> mListData = null;
    private CartHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public CartAdapter(Context mContext, List<CartObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_cart, viewGroup, false);
            mHolder = new CartHolder();
            mHolder.main_root = (LinearLayout) convertView.findViewById(R.id.main_root);
            mHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHolder.tv_details = (TextView) convertView.findViewById(R.id.tv_details);
            mHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            mHolder.tv_attribute_name = (TextView) convertView.findViewById(R.id.tv_attribute_name);
            mHolder.tv_attribute_value = (TextView) convertView.findViewById(R.id.tv_attribute_value);
            mHolder.et_quantity = (EditText) convertView.findViewById(R.id.et_quantity);
            mHolder.image = (ImageView) convertView.findViewById(R.id.image);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (CartHolder) convertView.getTag();
        }

        CartObject cartObject = mListData.get(position);
        mHolder.tv_title.setText(cartObject.getTitle());
        mHolder.tv_details.setText(cartObject.getDetails());
        mHolder.tv_price.setText("SAR "+cartObject.getDiscounted_price());
        mHolder.tv_attribute_name.setText(cartObject.getAttribute_name());
        mHolder.tv_attribute_value.setText(cartObject.getAttributeValue());
        mHolder.et_quantity.setText(cartObject.getQuantity());
        Picasso.get()
                .load(cartObject.getImage().getUrl())
                .placeholder(R.color.common_gray)
                .error(R.color.common_gray)
                .into(mHolder.image);
        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.main_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ((MainActivity) mContext).addFrag(Constants.FRAG_PRODUCT_DETAILS, null);

            }
        });
    }
}
