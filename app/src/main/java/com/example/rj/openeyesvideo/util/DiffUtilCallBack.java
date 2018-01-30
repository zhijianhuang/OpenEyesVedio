package com.example.rj.openeyesvideo.util;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.rj.openeyesvideo.model.bean.DailyBean;
import com.example.rj.openeyesvideo.model.bean.ItemListBean;
import com.example.rj.openeyesvideo.model.bean.TagChildBean;

import java.util.List;

/**
 * Created by rj on 2017/12/25.
 */

public class DiffUtilCallBack extends DiffUtil.Callback {

    private List<ItemListBean> moldDatas,mNewDatas;


    public DiffUtilCallBack(List<ItemListBean> OldDatas,List<ItemListBean> NewDatas){
        this.moldDatas=OldDatas;
        this.mNewDatas=NewDatas;
    }


    @Override
    public int getOldListSize() {
        return moldDatas!=null? moldDatas.size():0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas!=null?mNewDatas.size():0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if(moldDatas.get(oldItemPosition).getType().equals("textHeader") ||mNewDatas.get(newItemPosition).getType().equals("textHeader")){
            if(moldDatas.get(oldItemPosition).getType().equals(mNewDatas.get(newItemPosition).getType())){
                return moldDatas.get(oldItemPosition).getData().getText().equals(mNewDatas.get(newItemPosition).getData().getText());
            }else {
                return false;
            }
        }else {
            return moldDatas.get(oldItemPosition).getData().getTitle().equals(mNewDatas.get(newItemPosition).getData().getTitle());
        }
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ItemListBean oldItem=moldDatas.get(oldItemPosition);
        ItemListBean newItem=mNewDatas.get(newItemPosition);
        if(moldDatas.get(oldItemPosition).getType().equals("textHeader") ||mNewDatas.get(newItemPosition).getType().equals("textHeader")){
            if(moldDatas.get(oldItemPosition).getType().equals(mNewDatas.get(newItemPosition).getType())){
                return moldDatas.get(oldItemPosition).getData().getText().equals(mNewDatas.get(newItemPosition).getData().getText());
            }else {
                return false;
            }
        }else {
            return oldItem.getData().getTags().get(0).getName().equals(newItem.getData().getTags().get(0).getName());
        }
    }
}
