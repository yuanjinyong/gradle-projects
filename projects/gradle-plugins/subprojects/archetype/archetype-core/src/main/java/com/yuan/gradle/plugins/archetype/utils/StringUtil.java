package com.yuan.gradle.plugins.archetype.utils;


public final class StringUtil {
    private StringUtil() {

    }

    /**
     * 连接多个字符串
     *
     * @param c
     * @param str
     * @return
     */
    public static String strcat(String... str) {
        StringBuffer sb = new StringBuffer();
        for (String s : str) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 用c连接多个字符串
     *
     * @param c
     * @param str
     * @return
     */
    public static String strcat(String c, String... str) {
        StringBuffer sb = new StringBuffer();
        for (String s : str) {
            sb.append(s).append(c);
        }
        return sb.substring(0, sb.length() - c.length());
    }

    /**
     * 转为小写字母
     *
     * @param c
     * @return
     */
    public static char lower(char c) {
        return (char) (c >= 'a' ? c : c + 32);
    }

    /**
     * 转为大写字母
     *
     * @param c
     * @return
     */
    public static char upper(char c) {
        System.out.println((int) c);
        return (char) (c >= 'a' ? c - 32 : c);
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        if (str.length() > 1) {
            return upper(str.charAt(0)) + str.substring(1);
        } else if (str.length() == 1) {
            return upper(str.charAt(0)) + "";
        } else {
            return str;
        }
    }

    /**
     * 字符串转为类名格式。SampleClass
     *
     * @param str
     * @return
     */
    public static String toClassName(String... str) {
        String[] segments = strcat("_", str).replaceAll("\\.", "_").replaceAll("-", "_").replaceAll("\\\\", "_")
                .replaceAll("/", "_").split("_");
        StringBuffer sb = new StringBuffer();
        for (String seg : segments) {
            sb.append(capitalize(seg));
        }
        return sb.toString();
    }

    /**
     * 字符串转为类名全路径格式。com.yuan.archetype.SampleClass
     *
     * @param str
     * @return
     */
    public static String toFullClassName(String... str) {
        String fullClassName = strcat(".", str).replaceAll("\\\\", ".").replaceAll("/", ".");
        int idx = fullClassName.lastIndexOf('.');
        if (idx < 0) {
            return toClassName(fullClassName);
        }
        return fullClassName.substring(0, idx + 1) + toClassName(fullClassName.substring(idx + 1));
    }

    /**
     * 字符串转为包格式。com.yuan.archetype
     *
     * @param str
     * @return
     */
    public static String toPackage(String... str) {
        return strcat(".", str).toLowerCase().replaceAll("-", ".").replaceAll("_", ".").replaceAll("\\\\", ".")
                .replaceAll("/", ".");
    }

    /**
     * 字符串转为Gradle工程路径格式。:archetype:archetypes:archetype-archetype
     *
     * @param str
     * @return
     */
    public static String toProjectPath(String... str) {
        return ":" + strcat(":", str).replaceAll("\\\\", ":").replaceAll("/", ":");
    }
}
