package com.jzq.txtreader.book;

import com.jzq.txtreader.base.BaseContract;

public interface ReadBookContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

    }
}
