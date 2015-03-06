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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JComboBoxField;
import com.yuan.gradle.gui.core.fields.JComboBoxField.ItemType;
import com.yuan.gradle.gui.core.fields.JFileField;
import com.yuan.gradle.gui.core.fields.JRadioField;
import com.yuan.gradle.gui.core.fields.JTextAreaField;
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

    protected Field<JTextField> createTextField(String label, String value) {
        return new Field<JTextField>(label, new JTextField(value));
    }

    protected Field<JPasswordField> createPasswordField(String label, String value) {
        return new Field<JPasswordField>(label, new JPasswordField(value));
    }

    protected Field<JTextAreaField> createTextAreaField(String label, String value) {
        return createTextAreaField(label, value, 0, 0);
    }

    protected Field<JTextAreaField> createTextAreaField(String label, String value, int rows, int columns) {
        return new Field<JTextAreaField>(label, new JTextAreaField(value, rows, columns));
    }

    protected Field<JComboBoxField<String>> createComboBoxField(String label, List<String> options, String value) {
        return createComboBoxField(label, null, options, value);
    }

    protected Field<JComboBoxField<String>> createComboBoxField(String label, String createItem, List<String> options,
            String value) {
        return createComboBoxField(label, createItem, ItemType.TEXT, options, value);
    }

    protected Field<JComboBoxField<String>> createComboBoxField(String label, String createItem, ItemType itemType,
            List<String> options, String value) {
        return createComboBoxField(label, createItem, itemType, options.toArray(new String[0]), value);
    }

    protected Field<JComboBoxField<String>> createComboBoxField(String label, String[] options, String value) {
        return createComboBoxField(label, null, options, value);
    }

    protected Field<JComboBoxField<String>> createComboBoxField(String label, String createItem, String[] options,
            String value) {
        return createComboBoxField(label, createItem, ItemType.TEXT, options, value);
    }

    protected Field<JComboBoxField<String>> createComboBoxField(String label, String createItem, ItemType itemType,
            String[] options, String value) {
        JComboBoxField<String> comboBox = new JComboBoxField<String>(options, createItem, itemType) {
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

        Field<JComboBoxField<String>> field = new Field<JComboBoxField<String>>(label, comboBox);
        return field;
    }

    protected Field<JRadioField> createRadioField(String label, String[] options, String value) {
        return createRadioField(label, options, value, JRadioField.FLOWLAYOUT_H);
    }

    protected Field<JRadioField> createRadioField(String label, String[] options, String value, int layoutType) {
        return createRadioField(label, options, value, layoutType,
                layoutType == JRadioField.FLOWLAYOUT_V ? VFlowLayout.MIDDLE : FlowLayout.LEADING);
    }

    protected Field<JRadioField> createRadioField(String label, String[] options, String value, int layoutType,
            int align) {
        JRadioField radioField = new JRadioField(options, value, layoutType, align);
        radioField.addActionListener(this);
        return new Field<JRadioField>(label, radioField);
    }

    protected Field<JFileField> createFileField(String label, String value, String toolTipText, int mode) {
        return createFileField(label, value, toolTipText, mode, null);
    }

    protected Field<JFileField> createFileField(String label, String value, String toolTipText, int mode,
            FileFilter filter) {
        Field<JFileField> fileField = new Field<JFileField>(label, new JFileField(value));
        fileField.getField().setToolTipText(toolTipText);
        fileField.getField().setFileSelectionMode(mode);
        if (filter != null) {
            fileField.getField().setFileFilter(filter);
        }
        return fileField;
    }

    public List<String> getItems(JComboBoxField<?> comboBox) {
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
