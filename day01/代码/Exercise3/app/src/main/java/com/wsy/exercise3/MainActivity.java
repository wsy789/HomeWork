package com.wsy.exercise3;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.wsy.exercise3.fragment.AnimalFragment;
import com.wsy.exercise3.fragment.LinkmanFragment;
import com.wsy.exercise3.fragment.NewsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolMain;
    private ViewPager mVpMain;
    private TabLayout mTabMain;
    private ConstraintLayout mLlMain;
    private NavigationView mNaviMain;
    private DrawerLayout mDrawMain;
    private ArrayList<Fragment> fragList;
    private TabVpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mToolMain = (Toolbar) findViewById(R.id.main_tool);
        mVpMain = (ViewPager) findViewById(R.id.main_vp);
        mTabMain = (TabLayout) findViewById(R.id.main_tab);
        mLlMain = (ConstraintLayout) findViewById(R.id.main_ll);
        mNaviMain = (NavigationView) findViewById(R.id.main_navi);
        mDrawMain = (DrawerLayout) findViewById(R.id.main_draw);
        initTool();
        initTab();
    }

    private void initTool() {
        mToolMain.setTitle("ToolBar");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawMain, mToolMain, R.string.open, R.string.close);
        toggle.syncState();
        mDrawMain.addDrawerListener(toggle);
        mDrawMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                int right = drawerView.getRight();
                mLlMain.setX(right);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initTab() {
        fragList = new ArrayList<>();
        fragList.add(new NewsFragment());
        fragList.add(new LinkmanFragment());
        fragList.add(new AnimalFragment());
        adapter = new TabVpAdapter(getSupportFragmentManager(), fragList, this);
        mVpMain.setAdapter(adapter);
        mTabMain.setupWithViewPager(mVpMain);
        mTabMain.getTabAt(0).setText("消息").setIcon(R.drawable.tab_message_sel);
        mTabMain.getTabAt(1).setText("联系人").setIcon(R.drawable.tab_im_sel);
        mTabMain.getTabAt(2).setText("动态").setIcon(R.drawable.tab_d_sel);


    }
}
