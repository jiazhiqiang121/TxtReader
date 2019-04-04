package com.jzq.readerlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 阅读配置
 */
public class ReadConfiguration {

    private static final String READER_CONFIG_SP = "reader_config_sp";

    public static final String READ_KEY_TEXT_SIZE = "reader_key_text_size";
    public static final String READ_KEY_TEXT_COLOR = "reader_key_text_color";
    public static final String READ_KEY_BACKGROUND = "reader_key_background";
    public static final String READ_KEY_LINE_SPACE = "reader_key_line_space";
    public static final String READ_KEY_TEXT_SPACE = "reader_key_text_space";
    public static final String READ_KEY_MARGIN_WIDTH = "reader_key_width";
    public static final String READ_KEY_MARGIN_HEIGHT = "reader_key_height";

    private static volatile ReadConfiguration sInstance;
    private SharedPreferences mSp;

    public static ReadConfiguration getInstance() {
        if (sInstance == null) {
            synchronized (ReadConfiguration.class) {
                if (sInstance == null) {
                    sInstance = new ReadConfiguration();
                }
            }
        }
        return sInstance;
    }

    /**
     * 配置阅读参数
     *
     * @param context 上下文
     */
    public void iniConfiguration(Context context) {
        if (context == null) {
            throw new NullPointerException("SharedPreferences's Context can not be null!");
        }
        Context ctx = context.getApplicationContext();
        mSp = ctx.getSharedPreferences(READER_CONFIG_SP, Context.MODE_PRIVATE);
    }

    /**
     * 添加数据
     *
     * @param key   关键词
     * @param value 值
     */
    public void putObject(String key, Object value) {
        SharedPreferences.Editor editor = mSp.edit();
        if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        }
        editor.apply();
    }

    /**
     * 获取数据
     *
     * @param key      关键词
     * @param defValue 默认词
     * @return
     */
    public Object getObject(String key, Object defValue) {
        if (defValue instanceof Boolean) {
            return mSp.getBoolean(key, (boolean) defValue);
        } else if (defValue instanceof String) {
            return mSp.getString(key, String.valueOf(defValue));
        } else if (defValue instanceof Integer) {
            return mSp.getInt(key, (int) defValue);
        } else if (defValue instanceof Float) {
            return mSp.getFloat(key, (float) defValue);
        } else if (defValue instanceof Long) {
            return mSp.getLong(key, (long) defValue);
        }
        return null;
    }

    /**
     * 清除关键词
     *
     * @param key 关键词
     */
    public void clearKey(String key) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有关键词
     */
    public void clearAllKey() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.clear();
        editor.apply();
    }
}
