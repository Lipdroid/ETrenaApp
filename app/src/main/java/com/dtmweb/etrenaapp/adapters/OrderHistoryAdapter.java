package com.dtmweb.etrenaapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.holders.OrderHistoryHolder;
import com.dtmweb.etrenaapp.holders.ProductHolder;
import com.dtmweb.etrenaapp.models.OrderObject;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * Created by mdmunirhossain on 3/14/18.
 */

public class OrderHistoryAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<OrderObject> mListData = null;
    private OrderHistoryHolder mHolder = null;


    @Override
    public int getCount() {
        //return mListData.size();
        return 10;
    }

    public OrderHistoryAdapter(Context mContext, List<OrderObject> mListData) {
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

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (OrderHistoryHolder) convertView.getTag();
        }


        return convertView;
    }

}

