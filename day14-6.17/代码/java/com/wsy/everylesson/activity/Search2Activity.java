package com.wsy.everylesson.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wsy.everylesson.R;
import com.wsy.everylesson.app.BaseApp;
import com.wsy.everylesson.bean.SearchGreenDao;
import com.wsy.everylesson.db.SearchGreenDaoDao;
import com.wsy.everylesson.util.FlowLayout;

import java.util.List;
import java.util.zip.Inflater;

public class Search2Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackSearch;
    private ImageView mImgSearch;
    private ConstraintLayout mBackgroundSearch;
    private TextView mIcSearchPublic;
    private TextView mLine1Search;
    private TextView mResultSearch;
    private ImageView mDeleteSearch;
    private EditText mEdSearch;
    private SearchGreenDaoDao greenDaoDao;
    private TextView mResult22Search;
    private List<SearchGreenDao> daoList;
    private String search_str;
    private FlowLayout mFlowLayoutSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        greenDaoDao = BaseApp.getInstance().getDaoSession().getSearchGreenDaoDao();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //再次打开,显示搜索结果
//        String search_Str = mEdSearch.getText().toString();
        List<SearchGreenDao> daoList1 = greenDaoDao.loadAll();
        // List<SearchGreenDao> daoList2 = greenDaoDao.queryBuilder().where(SearchGreenDaoDao.Properties.Date.eq(search_Str)).list();
        for (int i = 0; i < daoList1.size(); i++) {

            mResult22Search.setText(daoList1.get(i).getDate());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<SearchGreenDao> daoList1 = greenDaoDao.loadAll();
       for (int i = 0; i < daoList1.size(); i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.search_flow, null);
            TextView label = (TextView) inflate.findViewById(R.id.search_flow_tv);
            label.setText(daoList1.get(i).getDate());
            mFlowLayoutSearch.addView(inflate);

        }

    }

    private void initView() {
        mBackSearch = (ImageView) findViewById(R.id.search_back);
        mImgSearch = (ImageView) findViewById(R.id.search_img);
        mBackgroundSearch = (ConstraintLayout) findViewById(R.id.search_background);
        mIcSearchPublic = (TextView) findViewById(R.id.public_ic_search);
        mLine1Search = (TextView) findViewById(R.id.search_line1);
        mResultSearch = (TextView) findViewById(R.id.search_result);
        mDeleteSearch = (ImageView) findViewById(R.id.search_delete);
        mEdSearch = (EditText) findViewById(R.id.search_ed);
//        initListener();

        mBackSearch.setOnClickListener(this);
        mIcSearchPublic.setOnClickListener(this);
        mDeleteSearch.setOnClickListener(this);
        mFlowLayoutSearch = (FlowLayout) findViewById(R.id.search_flowLayout);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                // 返回键
                finish();
                break;
            case R.id.public_ic_search:
                // 点击搜索文字
                onSearch();
                break;
            case R.id.search_delete:
                // 删除搜索结果
                onSearch_delete();
                break;
            default:
                break;
        }
    }

    private void onSearch_delete() {
        // 删除搜索结果
        greenDaoDao.deleteAll();

    }

    private void onSearch() {
        // 点击搜索文字
        search_str = mEdSearch.getText().toString();
        daoList = greenDaoDao.queryBuilder().where(SearchGreenDaoDao.Properties.Date.eq(search_str)).list();
        if (daoList.size() != 0) {
//搜索到就展示数据
            List<SearchGreenDao> daoList1 = greenDaoDao.loadAll();
            for (int i = 0; i < daoList1.size(); i++) {
                View inflate = LayoutInflater.from(this).inflate(R.layout.search_flow, null);
                TextView label = (TextView) inflate.findViewById(R.id.search_flow_tv);
                label.setText(daoList1.get(i).getDate());
                mFlowLayoutSearch.addView(inflate);

            }
//            greenDaoDao.deleteAll();
    /*        List<SearchGreenDao> daoList1 = greenDaoDao.loadAll();
            TextView search_flow_tv = findViewById(R.id.search_flow_tv);
            // List<SearchGreenDao> daoList2 = greenDaoDao.queryBuilder().where(SearchGreenDaoDao.Properties.Date.eq(search_Str)).list();
            for (int i = 0; i < daoList1.size(); i++) {
//                mResult22Search.setText(daoList1.get(i).getDate());
                search_flow_tv.setText(daoList1.get(i).getDate());
            }

            mFlowLayoutSearch.addView(search_flow_tv);*/
        } else {
            //没搜索到就插入
            greenDaoDao.insertOrReplace(new SearchGreenDao(null, search_str));
        }


    }

}
