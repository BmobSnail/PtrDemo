package com.snail.ptrdemo.layout;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by snail
 * on 2017/8/24.
 * Todo
 */

public class PtrClassicFrameLayout extends PtrFrameLayout {

    private RefreshHeader mPtrClassicHeader;
    private PtrClassicDefaultFooter mPtrClassicFooter;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new RefreshHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
        mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
        setFooterView(mPtrClassicFooter);
        addPtrUIHandler(mPtrClassicFooter);
    }

    public RefreshHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        setLastUpdateTimeHeaderKey(key);
        setLastUpdateTimeFooterKey(key);
    }

    public void setLastUpdateTimeHeaderKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    public void setLastUpdateTimeFooterKey(String key) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeHeaderRelateObject(object);
        setLastUpdateTimeFooterRelateObject(object);
    }

    public void setLastUpdateTimeHeaderRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }

    public void setLastUpdateTimeFooterRelateObject(Object object) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeRelateObject(object);
        }
    }
}
