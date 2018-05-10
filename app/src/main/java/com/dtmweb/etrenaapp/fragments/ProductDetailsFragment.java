package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import com.dtmweb.etrenaapp.LoginActivity;
import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.SlidingImage_Adapter;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.GlobalUtils;
import com.dtmweb.etrenaapp.utils.MultipleScreen;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private ViewPager product_pager = null;
    private static final Integer[] IMAGES = {R.drawable.demo_image_clothing, R.drawable.demo_image_clothing_two, R.drawable.demo_image_clothing, R.drawable.demo_image_clothing_two};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private Button btn_add = null;
    private Context mContext = null;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_details, container, false);
        product_pager = (ViewPager) root.findViewById(R.id.product_pager);
        btn_add = (Button) root.findViewById(R.id.btn_add);
        mContext = getActivity();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if not logged in open the login pages
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_NON_LOGGED)) {
                    //Show Login Screens
                    startActivity(new Intent(mContext, LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_scale_to_center);

                }else{
                    //add to cart
                }
            }
        });
        populatePager();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populatePager() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);
        product_pager.setAdapter(new SlidingImage_Adapter(getActivity(), ImagesArray));
    }
}
