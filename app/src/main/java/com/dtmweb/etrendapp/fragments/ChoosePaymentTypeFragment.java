package com.dtmweb.etrendapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoosePaymentTypeFragment extends Fragment {
    private LinearLayout btn_cash = null;
    private LinearLayout btn_visa = null;
    private Context mContext;

    public ChoosePaymentTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_choose_payment_type, container, false);
        mContext = getActivity();
        btn_cash = (LinearLayout) root.findViewById(R.id.btn_cash);
        btn_visa = (LinearLayout) root.findViewById(R.id.btn_visa);
        btn_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).addFrag(Constants.FRAG_ADD_NEW_CARD, null);

            }
        });
        btn_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).addFrag(Constants.FRAG_ADD_NEW_CARD, null);
            }
        });
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

}
