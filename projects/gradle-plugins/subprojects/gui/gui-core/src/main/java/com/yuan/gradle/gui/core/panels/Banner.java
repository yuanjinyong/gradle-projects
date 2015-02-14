/**
 *
 */
package com.yuan.gradle.gui.core.panels;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * @author Yuanjy
 *
 */
public class Banner extends JPanel {
	private static final long serialVersionUID = 1L;

	public Banner(Component... components) {
		initComponents(FlowLayout.LEFT, components);
	}

	public Banner(int align, Component... components) {
		initComponents(align, components);
	}

	private void initComponents(int align, Component... components) {
		setLayout(new FlowLayout(align));
		setAlignmentX(BOTTOM_ALIGNMENT);
		if (components != null) {
			for (Component component : components) {
				add(component);
			}
		}
	}
}
