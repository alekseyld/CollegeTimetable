package com.alekseyld.collegetimetable.view.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alekseyld.collegetimetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 06.09.2017.
 */

public class GroupInputWidget extends LinearLayout {

    @BindView(R.id.course)
    EditText course;

    @BindView(R.id.abbreviation)
    EditText abbreviation;

    @BindView(R.id.index_number)
    EditText index_number;

    public GroupInputWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.widget_group, this);
        ButterKnife.bind(this);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!source.toString().matches("[А-Я]{1,}")) {
                    return "";
                }
                return null;
            }
        };

        abbreviation.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(5)});
    }

    public String getGroup() {

        if (course.getText().toString().equals("") ||
                abbreviation.getText().toString().equals("") ||
                index_number.getText().toString().equals("")) {
            return null;
        }

        return course.getText().toString() + " " + abbreviation.getText().toString().trim() + "-" + index_number.getText().toString();
    }

}
