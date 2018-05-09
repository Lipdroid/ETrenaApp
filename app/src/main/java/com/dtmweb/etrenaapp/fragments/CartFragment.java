package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.adapters.CartAdapter;
import com.dtmweb.etrenaapp.adapters.FavProductAdapter;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private ListView cart_lv = null;
    private List<ProductObject> mListCartProduct = null;
    private CartAdapter adapter = null;
    private Context mContext = null;
    private ImageView image_no_data = null;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        cart_lv = (ListView) root.findViewById(R.id.cart_lv);
        image_no_data = (ImageView) root.findViewById(R.id.image_no_data) ;
        mContext = getActivity();
        populateList();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList() {
        adapter = new CartAdapter(mContext, mListCartProduct);
        cart_lv.setAdapter(adapter);
        if(mListCartProduct != null && mListCartProduct.size() == 0){
            image_no_data.setVisibility(View.VISIBLE);
        }
    }

}
