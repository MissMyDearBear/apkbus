package com.apkbus.weather.library.app;

import com.apkbus.weather.library.SwipeBackLayout;

public interface SwipeBackActivityBase {
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();
}
