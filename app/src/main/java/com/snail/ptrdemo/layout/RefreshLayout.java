package com.snail.ptrdemo.layout;

import android.content.Context;
import android.util.AttributeSet;

/**
 * author：created by Snail.江
 * time: 4/18/2017 16:40
 * email：409962004@qq.com
 * TODO: 自定义刷新头部
 */
public class RefreshLayout extends PtrClassicFrameLayout {

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initConfig();
    }

    private void initConfig() {

        /**
         * 禁止横滑
         */
        disableWhenHorizontalMove(true);
        /**
         * 阻尼系数
         * 默认: 1.7f，越大，感觉下拉时越吃力
         */
        setResistance(1.7f);

        /**
         * 触发刷新时移动的位置比例
         * 默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作
         */
        setRatioOfHeaderHeightToRefresh(1.2f);

        /**
         * 回弹延时
         * 默认 200ms，回弹到刷新高度所用时间
         */
        setDurationToClose(300);

        /**
         * 头部回弹时间
         * 默认1000ms
         */
        setDurationToCloseHeader(1000);

        /**
         * 刷新是保持头部
         * 默认值 true
         */
        setKeepHeaderWhenRefresh(true);

        /**
         * 下拉刷新 / 释放刷新
         * 默认为释放刷新 false
         */
        setPullToRefresh(false);

        /**
         * 刷新时，保持内容不动，仅头部下移, 使用 Material Design 风格才好看一点
         * 默认 false
         */
        setPinContent(false);

        /**
         * 刷新模式
         * 默认 TOP：只支持下拉
         * Bottom：只支持上拉
         * BOTH：两种同时支持
         */
        setMode(Mode.BOTH);

        /**
         * 显示距离上次刷新时间
         * 默认不显示
         */
        setLastUpdateTimeRelateObject(this);

    }
}
