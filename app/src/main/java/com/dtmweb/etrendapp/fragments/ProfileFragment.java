package com.dtmweb.etrendapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.ProductGridAdapter;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private GridView gridview = null;
    private List<ProductObject> mListProduct = null;
    private ProductGridAdapter adapter = null;
    private Context mContext = null;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = null;
        switch (GlobalUtils.user_type) {
            case Constants.CATEGORY_BUYER:
                root = inflater.inflate(R.layout.fragment_profile_buyer, container, false);
                break;
            case Constants.CATEGORY_SELLER:
                root = inflater.inflate(R.layout.fragment_profile_seller, container, false);
                gridview = (GridView) root.findViewById(R.id.gridview);
                mContext = getActivity();
                populateList();
                break;
            case Constants.CATEGORY_NON_LOGGED:
                root = inflater.inflate(R.layout.fragment_profile_buyer, container, false);
                break;
        }
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList() {
        adapter = new ProductGridAdapter(mContext, mListProduct);
        gridview.setAdapter(adapter);
    }

}
