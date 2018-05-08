package com.dtmweb.etrenaapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.utils.GlobalUtils;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


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
                break;
        }
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

}
