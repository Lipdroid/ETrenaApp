package com.dtmweb.etrendapp.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtmweb.etrendapp.R;

/**
 * Created by mdmunirhossain on 5/8/18.
 */

public class ValueSelector extends RelativeLayout {
    View rootView;
    TextView valueTextView;
    View minusButton;
    View plusButton;
    private static int value = 0;

    public ValueSelector(Context context) {
        super(context);
        init(context);
    }

    public ValueSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //do setup work here
        rootView = inflate(context, R.layout.value_selector, this);
        valueTextView = (TextView) rootView.findViewById(R.id.valueTextView);

        minusButton = rootView.findViewById(R.id.minusButton);
        plusButton = rootView.findViewById(R.id.plusButton);
        value = 0;
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValue(); //we'll define this method later
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValue(); //we'll define this method later//
            }
        });
    }

    public void setCenterText(String text){
        valueTextView.setText(text);
    }

    private void incrementValue() {
        int currentVal = value;
        if (currentVal < maxValue) {
            valueTextView.setText(String.valueOf(currentVal + 1));
            value = currentVal+1;
        }
    }

    private void decrementValue() {
        int currentVal = value;
        if (currentVal > minValue) {
            valueTextView.setText(String.valueOf(currentVal - 1));
            value = currentVal-1;
        }
    }

    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue() {
        return Integer.valueOf(valueTextView.getText().toString());
    }

    public void setValue(int newValue) {
        int value = newValue;
        if (newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }

        valueTextView.setText(String.valueOf(value));
    }

}
