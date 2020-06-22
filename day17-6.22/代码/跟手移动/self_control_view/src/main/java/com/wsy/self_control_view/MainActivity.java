package com.wsy.self_control_view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wsy.self_control_view.db.ViewBeanDao;

import java.util.List;

//自定义View   View的跟手移动（拖拽）
public class MainActivity extends AppCompatActivity  {

    private MyButton mBut;
    private ViewBeanDao viewBeanDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewBeanDao = BaseApp.getInstance().getDaoSession().getViewBeanDao();
        initView();

    }

    private void initView() {
        mBut = (MyButton) findViewById(R.id.but);

//        sp_tv.setText(sharedPreferences.getString("name",""));
    }

    @Override
    protected void onResume() {

        super.onResume();
        float x=mBut.getX();
        float y=mBut.getY();
        float measuredWidth = mBut.getMeasuredWidth();
        float measuredHeight = mBut.getMeasuredHeight();
        mBut.setTranslationX(x+measuredWidth);
        mBut.setTranslationY(y+measuredWidth);
        Log.e("TAG", "onResume:--measuredWidth="+measuredWidth+"--------measuredHeight"+measuredHeight );
        viewBeanDao.insertOrReplace( new ViewBean(null,measuredWidth,measuredHeight));

    }
}
