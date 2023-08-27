package com.alekseyld.collegetimetable.view.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.alekseyld.collegetimetable.databinding.WidgetGroupBinding;

/**
 * Created by Alekseyld on 06.09.2017.
 */

public class GroupInputWidget extends LinearLayout {

    private WidgetGroupBinding binding;

    public GroupInputWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = WidgetGroupBinding.inflate(layoutInflater, this, true);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!source.toString().matches("[А-Я]{1,}")) {
                    return "";
                }
                return null;
            }
        };

        binding.abbreviation.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(5)});
    }

    public String getGroup() {

        if (binding.course.getText().toString().equals("") ||
                binding.abbreviation.getText().toString().equals("")) {
            return null;
        }

        if (!binding.indexNumber.getText().toString().equals("")){
            return binding.course.getText().toString() + " " + binding.abbreviation.getText().toString().trim() + "-" + binding.indexNumber.getText().toString();
        } else {
            return binding.course.getText().toString() + " " + binding.abbreviation.getText().toString().trim();
        }
    }

}
