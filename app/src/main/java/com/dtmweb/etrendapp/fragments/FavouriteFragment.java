package com.dtmweb.etrendapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.FavProductAdapter;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private ListView fav_lv = null;
    private List<ProductObject> mListFavProduct = null;
    private FavProductAdapter adapter = null;
    private Context mContext = null;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        fav_lv = (ListView) root.findViewById(R.id.fav_lv);
        mContext = getActivity();
        populateList();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }
    private void populateList() {
        adapter = new FavProductAdapter(mContext, mListFavProduct);
        fav_lv.setAdapter(adapter);
    }
}
