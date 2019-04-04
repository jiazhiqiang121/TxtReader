package com.jzq.readerlibrary.plugin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;

import com.jzq.readerlibrary.model.BookPage;
import com.jzq.readerlibrary.parser.BookParser;
import com.jzq.readerlibrary.parser.IBookParser;
import com.jzq.readerlibrary.util.ReadConfiguration;
import com.jzq.readerlibrary.util.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookPlugin extends RxPlugin implements IPlugin {

    private int mLineSpace;
    private int mTextSpace;
    private int mTextColor;
    private int mTextSize;

    private Paint mPaint;

    private int mContentWidth;
    private int mContentHeight;

    private int mMarginWidth;
    private int mMarginHeight;

    private float mMeasureMarginWidth;

    private int mBackColor;     //背景颜色
    private int mLineCount;     //一页文字行数
    private int mLineTextCount; //一行文字数量
    private Context mContext;

    private BookPluginListener mPluginListener;

    @Override
    public void initPlugin(Context context) {
        if (context == null) {
            throw new NullPointerException("Book Plugin's Context can not be null");
        }
        this.mContext = context;

        ReadConfiguration.getInstance().iniConfiguration(mContext);

        mTextSize = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_TEXT_SIZE, Utils.dp2px(mContext, 16));
        mTextColor = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_TEXT_COLOR, Color.BLACK);
        mBackColor = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_BACKGROUND, Color.WHITE);

        mLineSpace = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_LINE_SPACE, Utils.dp2px(mContext, 8));
        mTextSpace = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_TEXT_SPACE, Utils.dp2px(mContext, 2));
        mMarginWidth = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_MARGIN_WIDTH, Utils.dp2px(mContext, 16));
        mMarginHeight = (int) ReadConfiguration.getInstance().getObject(ReadConfiguration.READ_KEY_MARGIN_HEIGHT, Utils.dp2px(mContext, 16));

        int displayWidthPixels = Utils.getDisplayWidthPixels(mContext);
        int displayHeightPixels = Utils.getDisplayHeightPixels(mContext);

        mContentWidth = displayWidthPixels - mMarginWidth * 2;
        mContentHeight = displayHeightPixels - mMarginHeight * 2;

    }

    @Override
    public void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setSubpixelText(true);
        mPaint.setAntiAlias(true);

        calcPageLineCount();
        measureMarginWidth();
    }

    @Override
    public void measureMarginWidth() {
        float wordWidth = mPaint.measureText("\u3000");
        float width = mContentWidth % wordWidth;
        mMeasureMarginWidth = mMarginWidth + width / 2;
    }

    @Override
    public synchronized void openBook(final String fileName) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                IBookParser parser = new BookParser();
                String result = parser.openBook(fileName);
                emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            //进入排版流程
                            formatCurrentPageText(result);
                            if (mPluginListener != null) {
                                mPluginListener.invalidateUI();
                            }

                        } else {
                            if (mPluginListener != null) {
                                mPluginListener.openBookFail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPluginListener != null) {
                            mPluginListener.openBookFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void closeBook() {
        unDisposable();
    }

    @Override
    public void previousPage() {

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void cancelPage() {

    }

    @Override
    public void currentPage() {

    }

    @Override
    public void calcPageLineCount() {
        mLineCount = mContentHeight / (mTextSize + mLineSpace);
    }

    @Override
    public void formatCurrentPageText(String data) {
        //计算当前页一行文字数量
        mLineTextCount = mContentWidth / (mTextSize + mTextSpace);

        String result = data.substring(0, mLineTextCount * mLineCount);

        BookPage bookPage = new BookPage();
//        bookPage.mLines = mLineCount;
        bookPage.mBegin = 0;
        bookPage.mEnd = 0;

    }

    @Override
    public void onDraw(Canvas canvas) {
        //设置背景要在前面
        canvas.drawColor(mBackColor);
//        if (mLineCount > 0) {
//            float y = mMarginHeight;
//            for (String line : mDataList) {
//                y += mTextSize + mLineSpace;
//                canvas.drawText(line, mMeasureMarginWidth, y, mPaint);
//            }
//
//            canvas.save();
//        }
    }

    @Override
    public void setPluginListener(BookPluginListener listener) {
        this.mPluginListener = listener;
    }


}
