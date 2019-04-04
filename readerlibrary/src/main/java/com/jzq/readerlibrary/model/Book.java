package com.jzq.readerlibrary.model;

/**
 * 书籍信息
 */
public class Book {

    public long mId;            //书籍id
    public String mName;        //名称
    public String mAuthor;      //作者
    public String mCoverImg;    //封面

    public String mCreateDate;  //创建日期
    public String mFilePath;    //文件路径
    public String mProgress;    //阅读进度
    public String mSummary;     //简介
    public String mContent;     //内容

    public Chapter mChapter;    //章节


}
