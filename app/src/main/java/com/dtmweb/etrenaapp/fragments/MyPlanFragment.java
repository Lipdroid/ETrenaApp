package com.dtmweb.etrenaapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dtmweb.etrenaapp.MainActivity;
import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlanFragment extends Fragment {


    public MyPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_plan, container, false);
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

}
