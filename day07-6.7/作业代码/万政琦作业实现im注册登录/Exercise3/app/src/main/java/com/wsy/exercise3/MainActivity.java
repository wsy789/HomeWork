package com.wsy.exercise3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView mTooltvMain;
    private CharSequence toolString;
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
        mTooltvMain = (TextView) findViewById(R.id.main_tooltv);
        initTool();
        initTab();
    }

    private void initTool() {
        mToolMain.setTitle("ToolBar");
        mNaviMain.setItemIconTintList(null);
        setSupportActionBar(mToolMain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawMain, mToolMain, R.string.open, R.string.close);
//        toggle.syncState();
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
        //侧滑菜单头部——点击头像登录注册
        initHead();

        //侧滑菜单——点击菜单条目跳到别的网页
        mNaviMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_my_github:
                        Intent intent = new Intent(MainActivity.this, GitActivity.class);
                        intent.putExtra("web","https://github.com/wsy789?tab=repositories");
                        startActivity(intent);
                        break;
                    case R.id.item_my_csdn:
                        /*Intent intent1 = new Intent(MainActivity.this, GitActivity.class);
                        intent1.putExtra("URL", "http://blog.csdn.net/wapchief");
                        startActivity(intent1);*/
                        Toast.makeText(MainActivity.this, "CSDN", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.item_my_jianshu:
                  /*      Intent intent2 = new Intent(MainActivity.this, GitActivity.class);
                        intent2.putExtra("URL", "http://www.jianshu.com/users/9f0bedd0835c");
                        startActivity(intent2); */
                        Toast.makeText(MainActivity.this, "简书", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.item_icon_user:
                 /*       Intent intent3 = new Intent(MainActivity.this, GitActivity.class);
                        intent3.putExtra("URL", "http://wapchief.github.io");
                        startActivity(intent3);*/
                        Toast.makeText(MainActivity.this, "github.io", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.item_icon_setting:
                        Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_icon_about:
                        Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_cache:
                        Toast.makeText(MainActivity.this, "清除缓存", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });
    }
    //侧滑菜单头部——点击头像登录注册
    private void initHead() {
        View headerView = mNaviMain.getHeaderView(0);
        ImageView head_img = headerView.findViewById(R.id.head_img);
        TextView head_name = headerView.findViewById(R.id.head_name);
        TextView head_idcontent = headerView.findViewById(R.id.head_idcontent);
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //把选项菜单中的文字或图标显示在ToolBar上
        getMenuInflater().inflate(R.menu.menu_option_item1, menu);//图标必须在@drawable下
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option1) {
            Toast.makeText(MainActivity.this, "创建群组", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "添加好友/群", Toast.LENGTH_SHORT).show();
        }
        /*if (toolString.equals("消息")){

        }*/
        return super.onOptionsItemSelected(item);
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

        mTabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTooltvMain.setText(tab.getText());
                toolString = tab.getText();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
