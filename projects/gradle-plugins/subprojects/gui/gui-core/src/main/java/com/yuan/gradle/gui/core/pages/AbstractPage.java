/**
 *
 */
package com.yuan.gradle.gui.core.pages;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import com.yuan.gradle.gui.core.partitions.AbstractPartition;


/**
 * 表格布局分区抽象基类，主要封装了向表格添加单元格的公用方法
 *
 * @author Yuanjy
 *
 */
public abstract class AbstractPage extends AbstractPartition {
    private static final long serialVersionUID = 1L;
    protected GroupLayout layout;
    protected SequentialGroup rows;
    protected SequentialGroup cols;

    public enum Gap {
        ROW, COL
    }

    public AbstractPage() {
        initPartition();
    }

    @Override
    protected void initLayout() {
        layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        rows = layout.createSequentialGroup();
        cols = layout.createSequentialGroup();
        layout.setHorizontalGroup(cols);
        layout.setVerticalGroup(rows);
    }

    @Override
    protected void initFields() {
        initPartitions();
    }

    abstract protected void initPartitions();

    public AbstractPage addCol(AbstractPartition... partitions) {
        ParallelGroup col = layout.createParallelGroup(Alignment.LEADING);
        for (AbstractPartition partition : partitions) {
            col.addComponent(partition, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE);
        }
        cols.addGroup(col);
        return this;
    }

    public AbstractPage addRow(AbstractPartition... partitions) {
        ParallelGroup row = layout.createParallelGroup(Alignment.LEADING);
        for (AbstractPartition partition : partitions) {
            row.addComponent(partition, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE);
        }
        rows.addGroup(row);
        return this;
    }
}
