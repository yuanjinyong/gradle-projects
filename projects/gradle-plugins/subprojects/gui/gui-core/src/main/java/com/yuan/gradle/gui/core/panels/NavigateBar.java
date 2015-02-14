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
public class NavigateBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public NavigateBar(Component... components) {
		initComponents(FlowLayout.LEFT, components);
	}

	public NavigateBar(int align, Component... components) {
		initComponents(align, components);
	}

	private void initComponents(int align, Component... components) {
		setLayout(new FlowLayout(align));
		// setAlignmentX(BOTTOM_ALIGNMENT);
		// setAlignmentX(CENTER_ALIGNMENT);
		if (components != null) {
			for (Component component : components) {
				add(component);
			}
		}
	}
}
