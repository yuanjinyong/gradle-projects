package com.yuan.gradle.gui.core.partitions;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JComboBoxWidget;
import com.yuan.gradle.gui.core.fields.JComboBoxWidget.ItemType;
import com.yuan.gradle.gui.core.fields.JFileWidget;
import com.yuan.gradle.gui.core.fields.JPasswordWidget;
import com.yuan.gradle.gui.core.fields.JRadioWidget;
import com.yuan.gradle.gui.core.fields.JTextAreaWidget;
import com.yuan.gradle.gui.core.fields.JTextWidget;
import com.yuan.gradle.gui.core.layouts.VFlowLayout;


/**
 * 分区抽象基类，主要封装一些公用的方法给子类使用
 *
 * @author Yuanjy
 *
 */
public abstract class AbstractPartition extends JPanel implements ActionListener, ItemListener {
    private static final long serialVersionUID = 1L;
    protected String ADD_NEW_ITEM = "新增选择项";

    protected void initPartition() {
        initLayout();
        initFields();
    }

    public void resetLayout() {
        removeAll();
        initLayout();
    }

    abstract protected void initLayout();

    abstract protected void initFields();

    protected JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        return button;
    }

    protected Field<JTextWidget> createTextField(String label, String value) {
        return new Field<JTextWidget>(label, new JTextWidget(value));
    }

    protected Field<JPasswordWidget> createPasswordField(String label, String value) {
        return new Field<JPasswordWidget>(label, new JPasswordWidget(value));
    }

    protected Field<JTextAreaWidget> createTextAreaField(String label, String value) {
        return createTextAreaField(label, value, 0, 0);
    }

    protected Field<JTextAreaWidget> createTextAreaField(String label, String value, int rows, int columns) {
        return new Field<JTextAreaWidget>(label, new JTextAreaWidget(value, rows, columns));
    }

    protected Field<JComboBoxWidget<String>> createComboBoxField(String label, List<String> options, String value) {
        return createComboBoxField(label, null, options, value);
    }

    protected Field<JComboBoxWidget<String>> createComboBoxField(String label, String createItem, List<String> options,
            String value) {
        return createComboBoxField(label, createItem, ItemType.TEXT, options, value);
    }

    protected Field<JComboBoxWidget<String>> createComboBoxField(String label, String createItem, ItemType itemType,
            List<String> options, String value) {
        return createComboBoxField(label, createItem, itemType, options.toArray(new String[0]), value);
    }

    protected Field<JComboBoxWidget<String>> createComboBoxField(String label, String[] options, String value) {
        return createComboBoxField(label, null, options, value);
    }

    protected Field<JComboBoxWidget<String>> createComboBoxField(String label, String createItem, String[] options,
            String value) {
        return createComboBoxField(label, createItem, ItemType.TEXT, options, value);
    }

    protected Field<JComboBoxWidget<String>> createComboBoxField(String label, String createItem, ItemType itemType,
            String[] options, String value) {
        JComboBoxWidget<String> comboBox = new JComboBoxWidget<String>(options, createItem, itemType) {
            private static final long serialVersionUID = 1L;

            @Override
            protected String buildItem(String itemText) {
                return itemText;
            }
        };
        if (value != null) {
            comboBox.setSelectedItem(value);
        }
        comboBox.addItemListener(this);

        Field<JComboBoxWidget<String>> field = new Field<JComboBoxWidget<String>>(label, comboBox);
        return field;
    }

    protected Field<JRadioWidget> createRadioField(String label, String[] options, String value) {
        return createRadioField(label, options, value, JRadioWidget.FLOWLAYOUT_H);
    }

    protected Field<JRadioWidget> createRadioField(String label, String[] options, String value, int layoutType) {
        return createRadioField(label, options, value, layoutType,
                layoutType == JRadioWidget.FLOWLAYOUT_V ? VFlowLayout.MIDDLE : FlowLayout.LEADING);
    }

    protected Field<JRadioWidget> createRadioField(String label, String[] options, String value, int layoutType,
            int align) {
        JRadioWidget radioField = new JRadioWidget(options, value, layoutType, align);
        radioField.addActionListener(this);
        return new Field<JRadioWidget>(label, radioField);
    }

    protected Field<JFileWidget> createFileField(String label, String value, String toolTipText, int mode) {
        return createFileField(label, value, toolTipText, mode, null);
    }

    protected Field<JFileWidget> createFileField(String label, String value, String toolTipText, int mode,
            FileFilter filter) {
        Field<JFileWidget> fileField = new Field<JFileWidget>(label, new JFileWidget(value));
        fileField.getField().setToolTipText(toolTipText);
        fileField.getField().setFileSelectionMode(mode);
        if (filter != null) {
            fileField.getField().setFileFilter(filter);
        }
        return fileField;
    }

    public List<String> getItems(JComboBoxWidget<?> comboBox) {
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = (String) comboBox.getItemAt(i);
            if (!ADD_NEW_ITEM.equals(item)) {
                items.add(item);
            }
        }

        return items;
    }

    protected void showInfoMsg(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showErrorMsg(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
