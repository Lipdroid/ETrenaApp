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
import com.dtmweb.etrenaapp.adapters.ProductGridAdapter;
import com.dtmweb.etrenaapp.models.OrderObject;
import com.dtmweb.etrenaapp.models.ProductObject;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageOrderFragment extends Fragment {

    private ListView order_lv = null;
    private List<OrderObject> mListOrder = null;
    private OrderHistoryAdapter adapter = null;
    private Context mContext = null;

    public ManageOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_manage_order, container, false);
        order_lv = (ListView) root.findViewById(R.id.order_lv);
        mContext = getActivity();
        populateList();
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }
    private void populateList() {
        adapter = new OrderHistoryAdapter(mContext, mListOrder);
        order_lv.setAdapter(adapter);
    }
}
