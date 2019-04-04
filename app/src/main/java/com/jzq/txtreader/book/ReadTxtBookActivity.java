package com.jzq.txtreader.book;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.jzq.readerlibrary.view.ReaderView;
import com.jzq.txtreader.R;
import com.jzq.txtreader.base.BaseActivity;

import java.io.File;

import butterknife.BindView;

public class ReadTxtBookActivity extends BaseActivity implements
        ReadBookContract.View {

    private static final String FILE_PATH = "file_path";

    public static void startActivity(Context context, String filePath) {
        Intent intent = new Intent(context, ReadTxtBookActivity.class);
        intent.putExtra(FILE_PATH, filePath);
        context.startActivity(intent);
    }

    @BindView(R.id.reader_view)
    ReaderView mReaderView;

    ReadBookContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_txt_book;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initDatas() {
        mPresenter = new ReadBookPresenter();
        mPresenter.attachView(this);

        String filePath = getIntent().getStringExtra(FILE_PATH);
        mReaderView.openBook(filePath);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void onDestroy() {
        mReaderView.closeBook();
        mPresenter.detachView();
        super.onDestroy();
    }
}
