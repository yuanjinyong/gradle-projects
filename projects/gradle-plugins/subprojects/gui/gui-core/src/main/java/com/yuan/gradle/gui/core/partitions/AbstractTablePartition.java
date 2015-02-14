/**
 *
 */
package com.yuan.gradle.gui.core.partitions;


import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import com.yuan.gradle.gui.core.fields.Field;


/**
 * 表格布局分区抽象基类，主要封装了向表格添加单元格的公用方法
 *
 * @author Yuanjy
 *
 */
public abstract class AbstractTablePartition extends AbstractPartition {
    private static final long serialVersionUID = 1L;
    protected GroupLayout layout;
    protected SequentialGroup rows;
    protected SequentialGroup cols;

    public enum Gap {
        ROW, COL
    }

    public AbstractTablePartition() {

    }

    @Override
    protected void initLayout() {
        layout = new GroupLayout(this);
        setLayout(layout);

        rows = layout.createSequentialGroup();
        cols = layout.createSequentialGroup();

        layout.setHorizontalGroup(cols);
        layout.setVerticalGroup(rows);
    }

    public void setAutoCreateGaps(boolean autoCreateGaps) {
        layout.setAutoCreateGaps(autoCreateGaps);
    }

    public void setAutoCreateContainerGaps(boolean autoCreateContainerGaps) {
        layout.setAutoCreateContainerGaps(autoCreateContainerGaps);
    }

    public AbstractTablePartition addAloneCol(Component component) {
        return addAloneCol(GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, component);
    }

    public AbstractTablePartition addAloneCol(int min, int pref, int max, Component component) {
        cols.addComponent(component, min, pref, max);
        return this;
    }

    public AbstractTablePartition addAloneRow(Component component) {
        return addAloneRow(GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                component);
    }

    public AbstractTablePartition addAloneRow(int min, int pref, int max, Component component) {
        rows.addComponent(component, min, pref, max);
        return this;
    }

    @SuppressWarnings("unchecked")
    public AbstractTablePartition addFieldCol(Field<? extends Component>... fields) {
        Component[] components = new Component[fields.length];
        int i = 0;
        for (Field<? extends Component> field : fields) {
            components[i++] = field.getField();
        }
        addGroupCol(Alignment.TRAILING, fields);
        return addGroupCol(components);
    }

    public AbstractTablePartition addGroupCol(Component... components) {
        return addGroupCol(Alignment.LEADING, components);
    }

    public AbstractTablePartition addGroupCol(Alignment alignment, Component... components) {
        return addGroupCol(alignment, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                components);
    }

    public AbstractTablePartition addGroupCol(int min, int pref, int max, Component... components) {
        return addGroupCol(Alignment.LEADING, min, pref, max, components);
    }

    public AbstractTablePartition addGroupCol(Alignment alignment, int min, int pref, int max, Component... components) {
        ParallelGroup col = layout.createParallelGroup(alignment);
        for (Component component : components) {
            col.addComponent(component, min, pref, max);
        }
        cols.addGroup(col);
        return this;
    }

    @SuppressWarnings("unchecked")
    public AbstractTablePartition addFieldRow(Field<? extends Component>... fields) {
        Component[] components = new Component[fields.length * 2];
        int i = 0;
        for (Field<? extends Component> field : fields) {
            components[i++] = field;
            components[i++] = field.getField();
        }
        return addGroupRow(components);
    }

    public AbstractTablePartition addGroupRow(Component... components) {
        return addGroupRow(Alignment.CENTER, components);
    }

    public AbstractTablePartition addGroupRow(Alignment alignment, Component... components) {
        return addGroupRow(alignment, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                GroupLayout.PREFERRED_SIZE, components);
    }

    public AbstractTablePartition addGroupRow(int min, int pref, int max, Component... components) {
        return addGroupRow(Alignment.CENTER, min, pref, max, components);
    }

    public AbstractTablePartition addGroupRow(Alignment alignment, int min, int pref, int max, Component... components) {
        ParallelGroup row = layout.createParallelGroup(alignment);
        for (Component component : components) {
            row.addComponent(component, min, pref, max);
        }
        rows.addGroup(row);
        return this;
    }

    public AbstractTablePartition addGap(Gap mode, int size) {
        switch (mode) {
            case ROW:
                rows.addGap(size);
                break;
            case COL:
                cols.addGap(size);
                break;
            default:
                break;
        }
        return this;
    }
}
