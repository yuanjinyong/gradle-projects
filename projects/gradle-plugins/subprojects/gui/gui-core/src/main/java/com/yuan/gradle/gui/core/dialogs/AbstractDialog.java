package com.yuan.gradle.gui.core.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import com.yuan.gradle.gui.core.partitions.WizardPartition;

public abstract class AbstractDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	protected WizardPartition contentPane;

	public AbstractDialog() {
		super();

		initDialog();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getBounds().height) / 2);
	}

	public AbstractDialog(Frame parent) {
		super(parent, parent.getTitle(), false);

		initDialog();

		Point parentLocation = parent.getLocation();
		setLocation(parentLocation.x + (parent.getWidth() - getWidth()) / 2, parentLocation.y
				+ (parent.getBounds().height - getBounds().height) / 2);
	}

	private void initDialog() {
		contentPane = createContentPane();
		contentPane.setAutoCreateGaps(true);
		contentPane.setAutoCreateContainerGaps(true);
		setContentPane(contentPane);

		pack();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension preferredSize = getPreferredSize();
		setMinimumSize(new Dimension(preferredSize.width > screenSize.width ? screenSize.width : preferredSize.width,
				preferredSize.height > screenSize.height ? screenSize.height : preferredSize.height));
	}

	abstract protected WizardPartition createContentPane();

	public void showBorder(boolean show) {
		showBorder(this, BorderFactory.createLineBorder(Color.RED), show);
	}

	private void showBorder(Container container, Border line, boolean show) {
		try {
			if (container instanceof JComponent) {
				((JComponent) container).setBorder(show ? line : null);
			}
		} catch (Throwable t) {
			// t.printStackTrace();
		}

		for (Component component : container.getComponents()) {
			if (component instanceof Container) {
				showBorder((Container) component, line, show);
			}
		}
	}

	protected void showInfoMsg(String title, String msg) {
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	protected void showErrorMsg(String title, String msg) {
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
	}
}
