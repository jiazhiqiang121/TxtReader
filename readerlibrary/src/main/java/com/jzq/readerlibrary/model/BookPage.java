package com.jzq.readerlibrary.model;

import java.util.List;

/**
 * 一页的数据
 */
public class BookPage {

    public long mBegin;
    public long mEnd;
    public List<String> mLines;

    public String getLinesToString() {
        if (mLines != null) {
            StringBuilder textBuilder = new StringBuilder();
            for (String line : mLines) {
                textBuilder.append(line);
            }
            return textBuilder.toString();
        }
        return "";
    }
}
