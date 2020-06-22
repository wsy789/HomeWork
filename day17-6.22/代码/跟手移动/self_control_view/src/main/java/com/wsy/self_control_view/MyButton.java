package com.wsy.self_control_view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.wsy.self_control_view.db.ViewBeanDao;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class MyButton extends Button {
    private ViewBean viewBean;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDowX;//按下去的x轴
    private float mDowY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDowX=event.getX();
                mDowY=event.getY();

                Log.e("TAG", "ACTION_DOWN: ----按下去的位置-----mDowX="+mDowX+"mDowY"+mDowY );
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();//移动时候的x轴
                float moveY = event.getY();

                Log.e("TAG", "ACTION_MOVE: ----移动时候的位置-----moveX="+moveX+"moveY"+moveY );

                //X轴终点坐标-X轴起点坐标>0  从左向右滑 ,反之 如果小于0 从右向左滑
                float vX  = moveX-mDowX;//滑动距离
                //Y轴终点坐标-Y轴起点坐标>0   从上向下滑  如果小于0 从下向上滑
                float vY  = moveY-mDowY;
                Log.e("TAG", "moveX-mDowX: ----滑动距离-----vX="+vX+"vY"+vY );

                //Button 左上角在当前父容器里面的位置
                float x=getX();
                float y=getY();
                Log.e("TAG", "Button 左上角在当前父容器里面的位置:--x="+x+"y"+y );
                //button自身左上角的位置(x,y)+移动的距离=最后的自身的位置
                setTranslationX(x+vX);
                setTranslationY(y+vY);

                ViewBeanDao viewBeanDao = BaseApp.getInstance().getDaoSession().getViewBeanDao();
//                viewBeanDao.insertOrReplace(new ViewBean(null,x,y));
                List<ViewBean> viewBeans = viewBeanDao.loadAll();
                if (viewBeans.size()>0){
                    for (int i = 0; i < viewBeans.size(); i++) {
                        viewBean = viewBeans.get(i);
                        float dateX = viewBean.getDateX();
                        float dateY = viewBean.getDateY();
                        setTranslationX(x+dateX);
                        setTranslationY(y+dateY);
                    }
                }

                Log.e("TAG", "setTranslationX:--x="+x+"y"+y );
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
