package com.xuyang.utilcode.util;

import java.io.File;

/**
 * Created by XuYang
 * 2020/5/13
 * Email:544066591@qq.com
 */
public class FileUtils {
    /*
     * 删除文件或者文件夹
     */
    public static void deleteFile(String path) {

        File file = new File(path);
        deleteFile(file);
    }

    public static void deleteFile(File file) {

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    file.delete();
                } else {
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                    file.delete();
                }
            }
        } else {
            //
        }
    }
}
