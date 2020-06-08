package com.wsy.exercise4;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BaseMultiQuickAdapter extends BaseMultiItemQuickAdapter<WanBean.DataBean.DatasBean, BaseViewHolder> {
    private Context mContext;

    public BaseMultiQuickAdapter(List<WanBean.DataBean.DatasBean> data, Context mContext) {
        super(data);
        this.mContext = mContext;
        addItemType(WanBean.DataBean.DatasBean.ITEM_1, R.layout.recy_item1);
        addItemType(WanBean.DataBean.DatasBean.ITEM_2, R.layout.recy_item2);
        addItemType(WanBean.DataBean.DatasBean.ITEM_3, R.layout.recy_item3);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanBean.DataBean.DatasBean item) {
//        设置条目文本的长按事件
        helper.addOnLongClickListener(R.id.item2_tv);
        helper.addOnLongClickListener(R.id.item2_img);
        switch (item.getItemType()) {
            case WanBean.DataBean.DatasBean.ITEM_1:
                Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.item1_img));
                break;
            case WanBean.DataBean.DatasBean.ITEM_2:
                helper.setText(R.id.item2_tv, item.getDesc());
                Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.item2_img));

                break;
            case WanBean.DataBean.DatasBean.ITEM_3:
                helper.setText(R.id.item3_tv, item.getDesc());
                Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.item3_img));

                break;
        }
    }
}
