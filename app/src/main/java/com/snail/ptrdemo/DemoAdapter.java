package com.snail.ptrdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by snail
 * on 2017/9/12.
 * Todo
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoHolder> {

    public DemoAdapter() {
    }

    @Override
    public DemoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_textview,parent,false);
        return new DemoHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
