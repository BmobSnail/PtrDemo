package com.snail.ptrdemo.layout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snail.ptrdemo.R;
import com.snail.ptrdemo.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by snail
 * on 2017/8/10.
 * Todo 自定义刷新头部
 */

public class RefreshHeader extends FrameLayout implements PtrUIHandler {

    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
    private long mLastUpdateTime = -1;
    private boolean mShouldShowLastUpdate;
    private String mLastUpdateTimeKey;

    private LastUpdateTimeUpdater mTimeUpdater;
    private AnimationDrawable mLoadingAnim;
    private SpUtils mSpUtils;
    private TextView mMsgTxt, mTimeTxt;
    private ImageView mImage;
    private LinearLayout mMsgLayout;

    public RefreshHeader(@NonNull Context context) {
        this(context, null);
    }

    public RefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSpUtils = SpUtils.getInstance(context);
        mTimeUpdater = new LastUpdateTimeUpdater();
        View header = LayoutInflater.from(context).inflate(R.layout.refresh_header, this);
        mMsgLayout = (LinearLayout) header.findViewById(R.id.refresh_layout);
        mMsgTxt = (TextView) header.findViewById(R.id.refresh_msg_txt);
        mTimeTxt = (TextView) header.findViewById(R.id.refresh_time_txt);
        mImage = (ImageView) header.findViewById(R.id.refresh_image);
        mLoadingAnim = (AnimationDrawable) mImage.getDrawable();
    }

    //设置更新时间
    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    //设置更新
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName());
    }

    //更新最后刷新时间
    private void tryUpdateLastUpdateTime() {
        if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
            mTimeTxt.setVisibility(GONE);
        } else {
            String time = getLastUpdateTime();
            if (TextUtils.isEmpty(time)) {
                mTimeTxt.setVisibility(GONE);
            } else {
                mTimeTxt.setVisibility(VISIBLE);
                mTimeTxt.setText(time);
            }
        }
    }

    //获取更新时间
    private String getLastUpdateTime() {
        if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = mSpUtils.getLong("refresh_time", -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        long diffTime = new Date().getTime() - mLastUpdateTime;
        int seconds = (int) (diffTime / 1000);
        if (diffTime < 0) {
            return null;
        }
        if (seconds <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(in.srain.cube.views.ptr.R.string.cube_ptr_last_update));

        if (seconds < 60) {
            sb.append(seconds).append(getContext().getString(in.srain.cube.views.ptr.R.string.cube_ptr_seconds_ago));
        } else {
            int minutes = (seconds / 60);
            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    Date date = new Date(mLastUpdateTime);
                    sb.append(sDataFormat.format(date));
                } else {
                    sb.append(hours).append(getContext().getString(in.srain.cube.views.ptr.R.string.cube_ptr_hours_ago));
                }

            } else {
                sb.append(minutes).append(getContext().getString(in.srain.cube.views.ptr.R.string.cube_ptr_minutes_ago));
            }
        }
        return sb.toString();
    }

    //销毁view的时候
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTimeUpdater != null) {
            mTimeUpdater.stop();
        }
    }

    //状态重置
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mImage.clearAnimation();
        mLoadingAnim.stop();
        mImage.setVisibility(GONE);
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
    }

    //刷新前的准备状态
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mShouldShowLastUpdate = true;
        mMsgLayout.setVisibility(VISIBLE);
        mImage.setVisibility(GONE);
        mTimeUpdater.start();

        tryUpdateLastUpdateTime();
        if (frame.isPullToRefresh()) {
            mMsgTxt.setText(getResources().getString(in.srain.cube.views.ptr.R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mMsgTxt.setText(getResources().getString(in.srain.cube.views.ptr.R.string.cube_ptr_pull_down));
        }
    }

    //刷新开始
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mShouldShowLastUpdate = false;
        mMsgLayout.setVisibility(GONE);
        mImage.setVisibility(VISIBLE);
        mTimeUpdater.stop();

        mImage.clearAnimation();
        mLoadingAnim.start();
    }

    //结束
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mMsgTxt.setVisibility(VISIBLE);
        mImage.setVisibility(GONE);
        mMsgTxt.setText(getResources().getString(in.srain.cube.views.ptr.R.string.cube_ptr_refresh_complete));

        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = new Date().getTime();
            mSpUtils.putLong("refresh_time", mLastUpdateTime);
        }

        mImage.clearAnimation();
        mLoadingAnim.stop();
    }

    //用户上下拉的状态
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mMsgLayout.setVisibility(VISIBLE);
            mMsgTxt.setText(in.srain.cube.views.ptr.R.string.cube_ptr_release_to_refresh);
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mMsgLayout.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mMsgTxt.setText(getResources().getString(in.srain.cube.views.ptr.R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mMsgTxt.setText(getResources().getString(in.srain.cube.views.ptr.R.string.cube_ptr_pull_down));
        }
    }

    //时间计数器
    private class LastUpdateTimeUpdater implements Runnable {

        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            tryUpdateLastUpdateTime();
            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }
}
