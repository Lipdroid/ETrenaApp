package com.dtmweb.etrendapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.HomeAdapter;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ListView banner_lv = null;
    private List<ProductObject> mListBanner = null;
    private HomeAdapter adapter = null;
    private Context mContext = null;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        banner_lv = (ListView) root.findViewById(R.id.banner_lv);
        mContext = getActivity();
        populateList();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList() {
        adapter = new HomeAdapter(mContext, mListBanner);
        banner_lv.setAdapter(adapter);
    }

}
