package com.dtmweb.etrendapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }
}

