package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import java.util.List;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.ProductHolder;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;

/**
 * Created by mdmunirhossain on 3/16/18.
 */

public class ProductAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<ProductObject> mListData = null;
    private ProductHolder mHolder = null;


    @Override
    public int getCount() {
        //return mListData.size();
        return 10;
    }

    public ProductAdapter(Context mContext, List<ProductObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_product_main, viewGroup, false);
            mHolder = new ProductHolder();
            mHolder.main_root = (RelativeLayout) convertView.findViewById(R.id.main_root);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ProductHolder) convertView.getTag();
        }

        setListenersForViews(position);
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

