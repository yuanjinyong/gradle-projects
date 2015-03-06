/**
 *
 */
package com.yuan.gradle.plugins.archetype.gui;


import java.awt.EventQueue;

import javax.swing.UIManager;


/**
 * @author Yuanjy
 *
 */
public class AppMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String feel = UIManager.getSystemLookAndFeelClassName();
                    UIManager.setLookAndFeel(feel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new AppFrame().setVisible(true);
            }
        });
    }

}
