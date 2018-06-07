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
import com.dtmweb.etrendapp.holders.BannerHolder;
import com.dtmweb.etrendapp.holders.FavProductHolder;
import com.dtmweb.etrendapp.models.BannerObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mdmunirhossain on 5/9/18.
 */

public class BannerAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<BannerObject> mListData = null;
    private BannerHolder mHolder = null;


    @Override
    public int getCount() {
        return mListData.size();
    }

    public BannerAdapter(Context mContext, List<BannerObject> mListData) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_image_banner, viewGroup, false);
            mHolder = new BannerHolder();
            mHolder.main_root = (LinearLayout) convertView.findViewById(R.id.main_root);
            mHolder.banner_image = (ImageView) convertView.findViewById(R.id.banner_image);
            mHolder.title = (TextView) convertView.findViewById(R.id.title);
            mHolder.description = (TextView) convertView.findViewById(R.id.description);
            mHolder.btn_shop = (TextView) convertView.findViewById(R.id.btn_shop);
            mHolder.text_ln = (LinearLayout) convertView.findViewById(R.id.text_ln);

            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (BannerHolder) convertView.getTag();
        }

        BannerObject bannerObject = mListData.get(position);
        if(bannerObject.getTitle() == null || bannerObject.getTitle().equals("")){
            mHolder.text_ln.setVisibility(View.GONE);
            mHolder.banner_image.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(bannerObject.getImage())
                    .placeholder(R.color.common_gray)
                    .error(R.color.common_gray)
                    .into(mHolder.banner_image);
        }else{
            mHolder.banner_image.setVisibility(View.GONE);
            mHolder.text_ln.setVisibility(View.VISIBLE);

            mHolder.title.setText(bannerObject.getTitle());
            mHolder.description.setText(bannerObject.getText());

        }

        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.main_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) mContext).addFrag(Constants.FRAG_PRODUCT_DETAILS, null);

            }
        });

        mHolder.btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
