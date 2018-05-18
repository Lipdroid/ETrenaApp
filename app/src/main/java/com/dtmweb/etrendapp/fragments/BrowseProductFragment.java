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
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseProductFragment extends Fragment {
    private GridView gridview = null;
    private List<ProductObject> mListProduct = null;
    private ProductGridAdapter adapter = null;
    private Context mContext = null;
    private String category;

    public BrowseProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_browse_product, container, false);
        if (getArguments().getString(String.class.toString()) != null) {
            category = getArguments().getString(String.class.toString());
        }
        gridview = (GridView) root.findViewById(R.id.gridview);
        mContext = getActivity();
        populateList();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList() {
        adapter = new ProductGridAdapter(mContext, mListProduct);
        gridview.setAdapter(adapter);
    }

}
