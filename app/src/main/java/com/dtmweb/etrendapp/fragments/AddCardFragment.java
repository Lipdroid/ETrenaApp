package com.dtmweb.etrendapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dtmweb.etrendapp.MainActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.utils.MultipleScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCardFragment extends Fragment {
    private Button btn_done = null;
    private Context mContext;

    public AddCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_card, container, false);
        btn_done = (Button) root.findViewById(R.id.btn_done);
        mContext = getActivity();
        btn_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).mBaseFrag.dismissAllFragmentStack();

            }
        });
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

}
