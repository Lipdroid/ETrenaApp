package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dtmweb.etrenaapp.MainActivity;
import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {

    private LinearLayout btn_accessorize = null;
    private LinearLayout btn_clothes = null;
    private LinearLayout btn_perfumes = null;
    private Context mContext;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        mContext = getActivity();
        btn_accessorize = (LinearLayout) root.findViewById(R.id.btn_accessorize);
        btn_clothes = (LinearLayout) root.findViewById(R.id.btn_clothes);
        btn_perfumes = (LinearLayout) root.findViewById(R.id.btn_perfumes);
        btn_accessorize.setOnClickListener(this);
        btn_clothes.setOnClickListener(this);
        btn_perfumes.setOnClickListener(this);
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accessorize:
                ((MainActivity) mContext).addFrag(Constants.FRAG_BROWSE_PRODUCT, Constants.CATEGORY_ACCESSORIZE);
                break;
            case R.id.btn_clothes:
                ((MainActivity) mContext).addFrag(Constants.FRAG_BROWSE_PRODUCT, Constants.CATEGORY_CLOTHES);
                break;
            case R.id.btn_perfumes:
                ((MainActivity) mContext).addFrag(Constants.FRAG_BROWSE_PRODUCT, Constants.CATEGORY_PERFUME);
                break;
        }
    }
}
