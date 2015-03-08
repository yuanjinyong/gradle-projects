package com.yuan.gradle.plugins.archetype.utils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;


public final class ValidateUtil {
    private ValidateUtil() {

    }

    /**
     * 判断是否为空字符串（包括null和空格）
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        try {
            return isEmptyString(str, null);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为空字符串（包括null和空格），如果传入的errMsg不为null，则抛出异常，异常信息为errMsg。
     *
     * @param str
     * @param errMsg
     * @return
     * @throws Exception
     */
    public static boolean isEmptyString(String str, String errMsg) throws Exception {
        if (str == null || str.trim().length() == 0) {
            if (errMsg != null) {
                throw new Exception(errMsg);
            }
            return true;
        }
        return false;
    }

    /**
     * 判断URL地址的文件是否存在
     *
     * @param url
     * @return
     */
    public static boolean isValidateUrl(URL url) {
        String protocol = url.getProtocol();
        // 如果是以文件的形式
        if (protocol.equals("file")) {
            try {
                return new File(url.toURI()).exists();
            } catch (URISyntaxException e) {
                return false;
            }
        }

        // 如果是jar包文件
        if (protocol.equals("jar")) {
            try {
                JarEntry jarEntry = ((JarURLConnection) url.openConnection()).getJarEntry();
                return jarEntry != null;
            } catch (IOException e) {
                return false;
            }
        }

        // 如果是网络文件
        if (protocol.equals("http")) {
            InputStream in = null;
            try {
                in = url.openConnection().getInputStream();
                return in != null;
            } catch (IOException e) {
                return false;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }
}
