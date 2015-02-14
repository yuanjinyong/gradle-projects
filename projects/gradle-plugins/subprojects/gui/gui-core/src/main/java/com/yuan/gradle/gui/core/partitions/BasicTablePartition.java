/**
 *
 */
package com.yuan.gradle.gui.core.partitions;

/**
 * 一个空白的表格分区，自动设置组件的间隙
 *
 * @author Yuanjy
 *
 */
public class BasicTablePartition extends AbstractTablePartition {
	private static final long serialVersionUID = 1L;

	public BasicTablePartition() {

	}

	@Override
	protected void initLayout() {
		super.initLayout();
		setAutoCreateGaps(true);
		setAutoCreateContainerGaps(false);
	}

	@Override
	protected void initFields() {

	}
}
