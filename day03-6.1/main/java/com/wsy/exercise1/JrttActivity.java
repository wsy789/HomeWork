package com.wsy.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import me.jessyan.autosize.internal.CustomAdapt;

public class JrttActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jrtt);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
