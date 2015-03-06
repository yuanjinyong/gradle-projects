package com.yuan.gradle.plugins.archetype.utils;


public final class ValidateUtil {
    private ValidateUtil() {

    }

    public static boolean isEmptyString(String str) {
        try {
            return isEmptyString(str, null);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEmptyString(String str, String errMsg) throws Exception {
        if (str == null || str.trim().length() == 0) {
            if (errMsg != null) {
                throw new Exception(errMsg);
            }
            return true;
        }
        return false;
    }
}
