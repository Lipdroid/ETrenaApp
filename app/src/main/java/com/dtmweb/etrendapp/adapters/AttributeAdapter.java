package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.fragments.AddProductFragment;
import com.dtmweb.etrendapp.holders.ImageHolder;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import java.nio.Buffer;
import java.util.List;

/**
 * Created by lipuhossain on 6/13/18.
 */

public class AttributeAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<String> mListData = null;
    private ImageHolder mHolder = null;
    private AddProductFragment fragment = null;

    @Override
    public int getCount() {
        return mListData.size();
    }

    public AttributeAdapter(Context mContext, List<String> mListData,AddProductFragment fragment) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;
        this.mListData = mListData;
        this.fragment = fragment;
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_attribute, viewGroup, false);
            mHolder = new ImageHolder();
            mHolder.main_root = (RelativeLayout) convertView.findViewById(R.id.main_root);
            mHolder.et_attribute = (EditText) convertView.findViewById(R.id.et_attribute);
            mHolder.btn_cross = (ImageView) convertView.findViewById(R.id.btn_cross);
            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ImageHolder) convertView.getTag();
        }
        String data = mListData.get(position);
        mHolder.et_attribute.setText(data);

        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.btn_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity) mContext).addFrag(Constants.FRAG_PRODUCT_DETAILS, null);

                fragment.removeAttributeAtPosition(position);

            }
        });
    }
}
