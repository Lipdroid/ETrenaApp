package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.FavProductHolder;
import com.dtmweb.etrendapp.holders.PlaceHolder;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import java.util.List;

/**
 * Created by mdmunirhossain on 6/6/18.
 */

public class ChooseAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<PlaceObject> mListData = null;
    private PlaceHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public ChooseAdapter(Context mContext, List<PlaceObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_place, viewGroup, false);
            mHolder = new PlaceHolder();
            mHolder.main_root = (LinearLayout) convertView.findViewById(R.id.main_root);
            mHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (PlaceHolder) convertView.getTag();
        }

        PlaceObject placeObject = mListData.get(position);
        mHolder.tv_name.setText(placeObject.getName());
        return convertView;
    }
}
