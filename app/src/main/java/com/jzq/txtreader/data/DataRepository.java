package com.jzq.txtreader.data;

public class DataRepository {

    private static volatile DataRepository sInstance;

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository();
                }
            }
        }
        return sInstance;
    }

}
