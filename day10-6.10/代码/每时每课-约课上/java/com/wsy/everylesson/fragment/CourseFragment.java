package com.wsy.everylesson.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.wsy.everylesson.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {


    private TextView mListClass;
    private ImageView mIcSearchPublic;
    private TextView mTvClassify;
    private ConstraintLayout mColGroup;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        startActivity(new Intent(getActivity(),SearchActivity.class));
//        initView(inflate);
        return inflate;

    }

    private void initView(@NonNull final View itemView) {
        mListClass = (TextView) itemView.findViewById(R.id.class_list);
        mIcSearchPublic = (ImageView) itemView.findViewById(R.id.public_ic_search);
        mTvClassify = (TextView) itemView.findViewById(R.id.classify_tv);
        mColGroup = (ConstraintLayout) itemView.findViewById(R.id.group_col);
        mListClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
