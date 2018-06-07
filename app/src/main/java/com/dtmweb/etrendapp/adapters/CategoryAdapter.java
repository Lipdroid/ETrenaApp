package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.holders.CategoryHolder;
import com.dtmweb.etrendapp.holders.FavProductHolder;
import com.dtmweb.etrendapp.models.CategoryObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mdmunirhossain on 6/7/18.
 */

public class CategoryAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<CategoryObject> mListData = null;
    private CategoryHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public CategoryAdapter(Context mContext, List<CategoryObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_category, viewGroup, false);
            mHolder = new CategoryHolder();
            mHolder.main_root = (LinearLayout) convertView.findViewById(R.id.main_root);
            mHolder.icon = (ImageView)convertView.findViewById(R.id.icon);
            mHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (CategoryHolder) convertView.getTag();
        }

        CategoryObject object = mListData.get(position);
        mHolder.tv_name.setText(object.getName());
        Picasso.get()
                .load(object.getIcon())
                .placeholder(R.color.common_gray)
                .error(R.color.common_gray)
                .into(mHolder.icon);
        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.main_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).addFrag(Constants.FRAG_BROWSE_PRODUCT, mListData.get(position).getId());

            }
        });
    }
}

