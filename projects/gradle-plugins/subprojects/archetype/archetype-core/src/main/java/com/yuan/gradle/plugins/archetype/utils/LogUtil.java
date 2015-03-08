package com.yuan.gradle.plugins.archetype.utils;


import java.awt.TextArea;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogUtil {
    public static final String DEBUG = "DEBUG";
    public static final String TRACE = "TRACE";
    public static final String INFO = "INFO";
    public static final String WARNING = "WARNING";
    public static final String ERROR = "ERROR";
    //private static final Map<String, Font> fontMap = new HashMap<String, Font>();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static TextArea outputArea;

    public static TextArea getOutputArea() {
        return outputArea;
    }

    public static void setOutputArea(TextArea outputArea) {
        LogUtil.outputArea = outputArea;

        // LogUtil.fontMap.put(DEBUG, new Font(Font.DIALOG, Font.PLAIN, 20));
        // LogUtil.fontMap.put(TRACE, new Font(Font.DIALOG, Font.PLAIN, 20));
        // LogUtil.fontMap.put(INFO, new Font(Font.DIALOG, Font.PLAIN, 20));
        // LogUtil.fontMap.put(WARNING, new Font(Font.DIALOG, Font.ITALIC, 24));
        // LogUtil.fontMap.put(ERROR, new Font(Font.DIALOG, Font.BOLD, 24));
    }

    public static void debug(String text) {
        output(DEBUG, text);
    }

    public static void info(String text) {
        output(INFO, text);
    }

    public static void error(String text) {
        output(ERROR, text);
    }

    public static void output(String level, String text) {
        StringBuffer sb = new StringBuffer();
        sb.append('[').append(sdf.format(new Date()));
        sb.append("][");
        sb.append(level);
        sb.append("] ");
        sb.append(text);
        sb.append('\n');

        //outputArea.setFont(fontMap.get(level));
        outputArea.append(sb.toString());
    }

    public static void output(String text) {
        StringBuffer sb = new StringBuffer();
        sb.append(text);
        sb.append('\n');

        //outputArea.setFont(fontMap.get(DEBUG));
        outputArea.append(sb.toString());
    }
}
