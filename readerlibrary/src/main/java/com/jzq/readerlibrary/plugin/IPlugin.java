package com.jzq.readerlibrary.plugin;

import android.content.Context;
import android.graphics.Canvas;

/**
 * 排版引擎
 */
public interface IPlugin {
    /**
     * 初始化引擎
     */
    void initPlugin(Context context);

    /**
     * 初始化画笔
     */
    void initPaint();

    /**
     * 测量宽度
     */
    void measureMarginWidth();

    /**
     * 打开电子书
     *
     * @param fileName 电子书路径
     * @return
     */
    void openBook(String fileName);

    /**
     * 关闭图书
     */
    void closeBook();

    /**
     * 上一页
     */
    void previousPage();

    /**
     * 下一页
     */
    void nextPage();

    /**
     * 取消翻页
     */
    void cancelPage();

    /**
     * 当前页
     */
    void currentPage();

    /**
     * 计算当前页文字行数
     */
    void calcPageLineCount();

    /**
     * 计算当前页一行文字数量
     */
    void formatCurrentPageText(String data);

    /**
     * 画板
     *
     * @param canvas
     */
    void onDraw(Canvas canvas);

    /**
     * 引擎监听
     *
     * @param listener 监听
     */
    void setPluginListener(BookPluginListener listener);

}
