package com.dtmweb.etrendapp.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.models.ImageObject;
import com.squareup.picasso.Picasso;

/**
 * Created by mdmunirhossain on 3/19/18.
 */

public class SlidingImage_Adapter extends PagerAdapter {


    private List<ImageObject> images;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, List<ImageObject> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        ImageObject imageObject = images.get(position);
        Picasso.get()
                .load(imageObject.getUrl())
                .placeholder(R.color.common_gray)
                .error(R.color.common_gray)
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
