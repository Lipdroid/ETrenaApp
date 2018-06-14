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

import java.util.List;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.holders.CartHolder;
import com.dtmweb.etrendapp.holders.OrderHistoryHolder;
import com.dtmweb.etrendapp.models.CartObject;
import com.dtmweb.etrendapp.models.OrderObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

/**
 * Created by mdmunirhossain on 3/14/18.
 */

public class OrderHistoryAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<CartObject> mListData = null;
    private OrderHistoryHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public OrderHistoryAdapter(Context mContext, List<CartObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_order_history, viewGroup, false);
            mHolder = new OrderHistoryHolder();

            mHolder.main_root = (LinearLayout) convertView.findViewById(R.id.main_root);
            mHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            mHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            mHolder.tv_attribute = (TextView) convertView.findViewById(R.id.tv_attribute);
            mHolder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            mHolder.tv_order = (TextView) convertView.findViewById(R.id.tv_order);
            mHolder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            mHolder.image = (ImageView) convertView.findViewById(R.id.image);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (OrderHistoryHolder) convertView.getTag();
        }

        CartObject cartObject = mListData.get(position);
        Picasso.get()
                .load(cartObject.getImage().getUrl())
                .placeholder(R.color.common_gray)
                .error(R.color.common_gray)
                .into(mHolder.image);
        mHolder.tv_title.setText(cartObject.getTitle());
        mHolder.tv_date.setText(GlobalUtils.formatDate(cartObject.getCreated()));
        mHolder.tv_price.setText("SAR"+cartObject.getDiscounted_price());
        mHolder.tv_attribute.setText(cartObject.getAttribute_name()+":"+cartObject.getAttributeValue());
        mHolder.tv_attribute.setText("Quantity:"+cartObject.getQuantity());
        mHolder.tv_order.setText("Order no:"+cartObject.getCart_id());
        mHolder.tv_status.setText("Paid");

        return convertView;
    }

}

