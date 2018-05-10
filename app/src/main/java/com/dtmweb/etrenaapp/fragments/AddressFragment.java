package com.dtmweb.etrenaapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dtmweb.etrenaapp.MainActivity;
import com.dtmweb.etrenaapp.R;
import com.dtmweb.etrenaapp.constants.Constants;
import com.dtmweb.etrenaapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {
    private Button btn_go = null;
    private Context mContext = null;
    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_address, container, false);
        mContext = getActivity();
        btn_go = (Button) root.findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open card type chooser
                ((MainActivity) mContext).addFrag(Constants.FRAG_CHOOSE_PAYMENT_METHOD, null);

            }
        });
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

}
