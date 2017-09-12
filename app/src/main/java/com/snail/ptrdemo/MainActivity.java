package com.snail.ptrdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.snail.ptrdemo.layout.RefreshLayout;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RefreshLayout layout = (RefreshLayout) findViewById(R.id.refresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        layout.setMode(PtrFrameLayout.Mode.BOTH);
        layout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      if (layout.isRefreshing())
                          layout.refreshComplete();
                    }
                },1500);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (layout.isRefreshing())
                            layout.refreshComplete();
                    }
                },1500);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DemoAdapter());
    }
}
