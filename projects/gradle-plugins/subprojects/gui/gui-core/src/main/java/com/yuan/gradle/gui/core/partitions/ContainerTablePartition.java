/**
 *
 */
package com.yuan.gradle.gui.core.partitions;

/**
 * 一个空白的表格布局分区，自动设置组件、组的间隙
 *
 * @author Yuanjy
 *
 */
public class ContainerTablePartition extends AbstractTablePartition {
	private static final long serialVersionUID = 1L;

	public ContainerTablePartition() {

	}

	@Override
	protected void initLayout() {
		super.initLayout();
		setAutoCreateGaps(true);
		setAutoCreateContainerGaps(true);
	}

	@Override
	protected void initFields() {

	}
}
