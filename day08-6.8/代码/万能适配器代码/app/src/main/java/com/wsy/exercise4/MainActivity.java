package com.wsy.exercise4;

import android.database.Observable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wsy.exercise4.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        initView();

    }

    private void initView() {
        mRecy = (RecyclerView) findViewById(R.id.recy);
        mRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecy.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        initModel();


    }

    private void initModel() {
        new Retrofit.Builder()
                .baseUrl(ApiService.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getNet(1, "294")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanBean wanBean) {
                        List<WanBean.DataBean.DatasBean> datas = wanBean.getData().getDatas();
                        returnDate(datas);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void returnDate(final List<WanBean.DataBean.DatasBean> datas) {
        for (int i = 0; i < datas.size(); i++) {
            WanBean.DataBean.DatasBean datasBean = datas.get(i);
            if (i==0){
                datasBean.setmItemType(WanBean.DataBean.DatasBean.ITEM_1);
            }else if (i%3==0){
                datasBean.setmItemType(WanBean.DataBean.DatasBean.ITEM_2);
            }else {
                datasBean.setmItemType(WanBean.DataBean.DatasBean.ITEM_3);
            }
        }
        BaseMultiQuickAdapter adapter = new BaseMultiQuickAdapter( datas,MainActivity.this);
        mRecy.setAdapter(adapter);
        //点击
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, datas.get(position).getChapterName(), Toast.LENGTH_SHORT).show();

            }
        });
        //长按
        adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
               if (view.getId()==R.id.item2_tv){
                   Toast.makeText(MainActivity.this, datas.get(position).getNiceDate(), Toast.LENGTH_SHORT).show();

               }else if (view.getId()==R.id.item2_img){
                   Toast.makeText(MainActivity.this, datas.get(position).getEnvelopePic(), Toast.LENGTH_SHORT).show();

               }
                return false;
            }
        });

        //条目动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);//设置重复执行代码

        //添加头布局
        View inflate = View.inflate(MainActivity.this, R.layout.item_header, null);
        adapter.addHeaderView(inflate);
    }
}
