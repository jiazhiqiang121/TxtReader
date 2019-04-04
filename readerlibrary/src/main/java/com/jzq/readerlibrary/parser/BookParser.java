package com.jzq.readerlibrary.parser;

import android.content.ContentValues;
import android.text.TextUtils;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookParser implements IBookParser {

    @Override
    public String openBook(String path) {
        return parser(path);
    }

    @Override
    public void closeBook() {

    }

    @Override
    public void openBookFail() {

    }

    @Override
    public void formatBookPaging() {

    }

    private long mBookLength;

    private void formatBookByPage(String path) throws IOException {
        File file = new File(path);
        String charset = getCharset(path);
        if (charset == null) {
            charset = "UTF-8";
        }
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
        int index = 0;
        while (true) {
            char[] buf = new char[30000];
            int result = reader.read(buf);
            if (result == -1) {
                reader.close();
                break;
            }
            String buffer = new String(buf);
            buffer = buffer.replaceAll("\r\n+\\s*", "\r\n\u3000\u3000");
            buffer = buffer.replaceAll("\u0000", "");
            buf = buffer.toCharArray();
            mBookLength += buf.length;



        }
    }

    /**
     * 获取编码方式
     *
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    private static String getCharset(String filePath) throws IOException {
        String charset;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] bytes = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);
        int read;
        while ((read = fileInputStream.read(bytes)) > 0 && !detector.isDone()) {
            detector.handleData(bytes, 0, read);
        }
        detector.dataEnd();
        charset = detector.getDetectedCharset();
        detector.reset();
        return charset;
    }

    /**
     * 解析文件
     *
     * @param path 文件地址
     * @return
     */
    private String parser(String path) {
        byte[] bytes = formatFileContent(path);
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    /**
     * 获取所有Txt文本内容
     *
     * @param inFilePath 文件路径
     * @return
     */
    private byte[] formatFileContent(String inFilePath) {
        if (TextUtils.isEmpty(inFilePath)) {
            return null;
        }
        byte[] bytes = null;
        FileInputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        File file = new File(inFilePath);
        if (file.exists() && file.canRead()) {
            try {
                outStream = new ByteArrayOutputStream();
                inStream = new FileInputStream(inFilePath);

                BufferedInputStream bin = new BufferedInputStream(inStream);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(bin, "GBK"));

                String line = reader.readLine();
                while (line != null) {
                    outStream.write(line.getBytes("UTF-8"));
                    line = reader.readLine();
                }
                bytes = outStream.toByteArray();

                outStream.flush();
                reader.close();
                bin.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (outStream != null) {
                        outStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }
}
