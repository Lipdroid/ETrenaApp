package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.ProductGridAdapter;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.GlobalUtils;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

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
