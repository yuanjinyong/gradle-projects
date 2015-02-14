/**
 *
 */
package com.yuan.gradle.gui.core.partitions;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.yuan.gradle.gui.core.panels.Banner;
import com.yuan.gradle.gui.core.panels.NavigateBar;
import com.yuan.gradle.gui.core.panels.TitleBar;
import com.yuan.gradle.gui.core.panels.TitleBarListener;

/**
 * 向导式分区抽象基类，分区顶部是标题栏，中间是内容面板，底部是导航按钮栏
 *
 * @author Yuanjy
 *
 */
public abstract class WizardPartition extends AbstractTablePartition implements TitleBarListener {
	private static final long serialVersionUID = 1L;
	private JPanel titleBar;
	private JPanel banner;
	private JScrollPane contentPane;
	private JPanel navigateBar;

	public WizardPartition() {

	}

	@Override
	protected void initLayout() {
		super.initLayout();
		setAutoCreateGaps(true);
		setAutoCreateContainerGaps(false);
	}

	@Override
	protected void initFields() {
		titleBar = createTitleBar();
		if (titleBar != null) {
			((TitleBar) titleBar).addTitleBarListener(this);
		} else {
			titleBar = new JPanel();
			titleBar.setVisible(false);
		}

		banner = checkAndHide(createBanner());
		contentPane = new JScrollPane(createContentPane());
		navigateBar = checkAndHide(createNavigateBar());

		addGroupCol(titleBar, banner, contentPane, navigateBar);
		addAloneRow(titleBar);
		addAloneRow(banner);
		addAloneRow(GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, contentPane);
		addAloneRow(navigateBar);
	}

	protected TitleBar createTitleBar() {
		return null;
	}

	abstract protected ContainerTablePartition createContentPane();

	protected NavigateBar createNavigateBar() {
		return null;
	}

	protected Banner createBanner() {
		return null;
	}

	private JPanel checkAndHide(JPanel panel) {
		if (panel == null) {
			panel = new JPanel();
			panel.setVisible(false);
		}
		return panel;
	}

	@Override
	public void expanded() {
		if (banner instanceof Banner) {
			banner.setVisible(true);
		}
		contentPane.setVisible(true);
		if (navigateBar instanceof NavigateBar) {
			navigateBar.setVisible(true);
		}
	}

	@Override
	public void collapsed() {
		banner.setVisible(false);
		contentPane.setVisible(false);
		navigateBar.setVisible(false);
	}
}
