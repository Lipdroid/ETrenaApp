package com.dtmweb.etrendapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.customViews.CircleImageView;
import com.dtmweb.etrendapp.fragments.AddProductFragment;
import com.dtmweb.etrendapp.holders.FavProductHolder;
import com.dtmweb.etrendapp.holders.ImageHolder;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lipuhossain on 6/12/18.
 */

public class ImageListAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<ImageObject> mListData = null;
    private ImageHolder mHolder = null;
    private AddProductFragment fragment = null;

    @Override
    public int getCount() {
        return mListData.size();
    }

    public ImageListAdapter(Context mContext, List<ImageObject> mListData, AddProductFragment fragment) {
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
            mHolder = new ImageHolder();
            if (fragment != null) {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.item_image, viewGroup, false);
                mHolder.image = (ImageView) convertView.findViewById(R.id.image);
            } else {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.item_image_round, viewGroup, false);
                mHolder.image_circle = (CircleImageView) convertView.findViewById(R.id.image);
            }
            mHolder.main_root = (RelativeLayout) convertView.findViewById(R.id.main_root);
            mHolder.btn_cross = (ImageView) convertView.findViewById(R.id.btn_cross);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ImageHolder) convertView.getTag();
        }
        ImageObject imageObject = mListData.get(position);
        if (fragment != null) {
            Picasso.get()
                    .load(imageObject.getUrl())
                    .placeholder(R.color.common_gray)
                    .error(R.color.common_gray)
                    .into(mHolder.image);
        } else {
            Picasso.get()
                    .load(imageObject.getUrl())
                    .placeholder(R.color.common_gray)
                    .error(R.color.common_gray)
                    .into(mHolder.image_circle);
        }


        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.btn_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity) mContext).addFrag(Constants.FRAG_PRODUCT_DETAILS, null);
                fragment.removeImageAtPosition(position);

            }
        });
    }
}
