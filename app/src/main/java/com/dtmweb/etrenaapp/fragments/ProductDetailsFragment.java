package com.dtmweb.etrenaapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.SlidingImage_Adapter;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.MultipleScreen;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private ViewPager product_pager = null;
    private static final Integer[] IMAGES = {R.drawable.test_pager_image, R.drawable.test_pager_image, R.drawable.test_pager_image, R.drawable.test_pager_image};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_details, container, false);
        product_pager = (ViewPager) root.findViewById(R.id.product_pager);
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
