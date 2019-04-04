package com.jzq.readerlibrary.plugin;

/**
 * 电子书监听
 */
public interface BookPluginListener {

    /**
     * 图书打开失败
     */
    void openBookFail();

    /**
     * 更新UI
     */
    void invalidateUI();
}
