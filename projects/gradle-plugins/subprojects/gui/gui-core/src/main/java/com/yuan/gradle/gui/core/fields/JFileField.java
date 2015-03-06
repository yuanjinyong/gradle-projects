/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Document;


/**
 * @author Yuanjy
 *
 */
public class JFileField extends JPanel {
    private static final long serialVersionUID = 1L;
    // 文件路径输入框
    private JTextField textField;
    // ...按钮
    private JButton button;
    // 文件选择对话框
    private JFileChooser fileChooser;

    public JFileField() {
        this((String) null, (FileSystemView) null);
    }

    public JFileField(String currentDirectory) {
        this(currentDirectory, (FileSystemView) null);
    }

    public JFileField(String currentDirectory, FileSystemView fsv) {
        fileChooser = new JFileChooser(currentDirectory, fsv);
        initComponents(currentDirectory, fsv);
    }

    public JFileField(File currentDirectory) {
        this(currentDirectory, (FileSystemView) null);
    }

    public JFileField(File currentDirectory, FileSystemView fsv) {
        fileChooser = new JFileChooser(currentDirectory, fsv);
        initComponents(currentDirectory == null ? null : currentDirectory.getAbsolutePath(), fsv);
    }

    public JFileField(FileSystemView fsv) {
        this((File) null, fsv);
    }

    private void initComponents(String currentDirectory, FileSystemView fsv) {
        textField = new JTextField();
        textField.setText(currentDirectory);

        button = new JButton();
        button.setText("...");
        button.setMargin(new Insets(0, 2, 0, 2));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (textField.getText().trim().length() > 0) {
                    fileChooser.setCurrentDirectory(new File(textField.getText().trim()));
                }

                int returnVal = fileChooser.showOpenDialog(JFileField.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.isMultiSelectionEnabled()) {
                        File[] files = fileChooser.getSelectedFiles();
                        StringBuilder sb = new StringBuilder();
                        for (File file : files) {
                            sb.append(file.getAbsolutePath()).append(',');
                        }
                        textField.setText(sb.substring(0, sb.length() - 1));
                    } else {
                        textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        });

        setLayout(new BorderLayout());
        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
    }

    public Document getDocument() {
        return textField.getDocument();
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void setEditable(boolean b) {
        textField.setEditable(b);
    }

    public void setMultiSelectionEnabled(boolean b) {
        fileChooser.setMultiSelectionEnabled(b);
    }

    @Override
    public void setToolTipText(String text) {
        textField.setToolTipText(text);
        fileChooser.setDialogTitle(text);
    }

    public String getText() {
        return textField.getText();
    }

    public File getFile() {
        if (textField.getText() == null || textField.getText().trim().length() == 0) {
            return null;
        }
        return new File(textField.getText().trim());
    }

    public File[] getFiles() {
        String[] fileNames = textField.getText().trim().split(",");
        File[] files = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            files[i] = new File(fileNames[i]);
        }
        return files;
    }

    public void setFile(File file) {
        textField.setText(file.getAbsolutePath());
    }

    public void setFileSelectionMode(int mode) {
        fileChooser.setFileSelectionMode(mode);
    }

    public void setFileHidingEnabled(boolean b) {
        fileChooser.setFileHidingEnabled(b);
    }

    public void setFileFilter(FileFilter filter) {
        fileChooser.setFileFilter(filter);
    }

    public void selectAll() {
        textField.selectAll();
    }

    public void select(int selectionStart, int selectionEnd) {
        textField.select(selectionStart, selectionEnd);
    }

    public synchronized void addActionListener(ActionListener l) {
        textField.addActionListener(l);
        fileChooser.addActionListener(l);
    }

    public synchronized void removeActionListener(ActionListener l) {
        fileChooser.removeActionListener(l);
        textField.removeActionListener(l);
    }
}
