package com.jzq.readerlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.jzq.readerlibrary.plugin.BookPlugin;
import com.jzq.readerlibrary.plugin.BookPluginListener;
import com.jzq.readerlibrary.plugin.IPlugin;

public class ReaderView extends View {
    public ReaderView(Context context) {
        this(context, null);
    }

    public ReaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private IPlugin mPlugin;

    private BookPluginListener mPluginListener = new BookPluginListener() {
        @Override
        public void openBookFail() {

        }

        @Override
        public void invalidateUI() {
            invalidate();
        }
    };

    private void initViews(Context context) {
        mPlugin = new BookPlugin();
        mPlugin.initPlugin(context);
        mPlugin.initPaint();
        mPlugin.setPluginListener(mPluginListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPlugin.onDraw(canvas);
    }

    public void openBook(String path) {
        mPlugin.openBook(path);
    }

    public void closeBook() {
        mPlugin.closeBook();
    }
}
