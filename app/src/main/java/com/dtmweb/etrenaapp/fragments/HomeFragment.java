package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.OrderHistoryAdapter;
import com.dtmweb.etrenaapp.adapters.ProductAdapter;
import com.dtmweb.etrenaapp.models.OrderObject;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ListView product_lv = null;
    private List<ProductObject> mListProduct = null;
    private ProductAdapter adapter = null;
    private Context mContext = null;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        product_lv = (ListView) root.findViewById(R.id.product_lv);
        mContext = getActivity();
        populateList();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList() {
        adapter = new ProductAdapter(mContext, mListProduct);
        product_lv.setAdapter(adapter);
    }
}
