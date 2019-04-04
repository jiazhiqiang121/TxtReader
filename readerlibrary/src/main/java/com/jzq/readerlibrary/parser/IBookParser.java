package com.jzq.readerlibrary.parser;

public interface IBookParser {

    /**
     * 打开电子书
     *
     * @param path 图书路径
     * @return
     */
    String openBook(String path);

    /**
     * 关闭电子书
     */
    void closeBook();

    /**
     * 打开电子书失败
     */
    void openBookFail();

    /**
     * 图书分页
     */
    void formatBookPaging();

}
