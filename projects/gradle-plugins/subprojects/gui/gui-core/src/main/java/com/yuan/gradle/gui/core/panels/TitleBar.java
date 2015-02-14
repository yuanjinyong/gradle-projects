/**
 *
 */
package com.yuan.gradle.gui.core.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.yuan.gradle.gui.core.layouts.DvaToolBarLayout;

/**
 * @author Yuanjy
 *
 */
public class TitleBar extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String COLLAPSE = String.valueOf((char) 0x25B2);
	private static final String EXPAND = String.valueOf((char) 0x25BC);
	private List<TitleBarListener> listeners = new ArrayList<TitleBarListener>();
	private JLabel label;
	private JToolBar toolBar;
	private JMenuItem switchButton;

	public TitleBar(String title) {
		this(title, (Icon) null);
	}

	public TitleBar(String title, Component... components) {
		this(title, null, components);
	}

	public TitleBar(String title, Icon icon) {
		this(title, icon, new Component[] {});
	}

	public TitleBar(String title, Icon icon, Component... components) {
		initComponents(title, icon, components);
	}

	private void initComponents(String title, Icon icon, Component... components) {
		label = new JLabel(title);
		Font font = label.getFont();
		label.setFont(font.deriveFont(Font.BOLD).deriveFont(font.getSize2D() + 5));
		if (icon != null) {
			label.setIcon(icon);
		}

		toolBar = new JToolBar();
		toolBar.setLayout(new DvaToolBarLayout(toolBar));
		toolBar.setFloatable(false);
		for (Component component : components) {
			toolBar.add(component);
		}

		switchButton = new JMenuItem(COLLAPSE);
		switchButton.setForeground(Color.GRAY);
		switchButton.setOpaque(true);
		switchButton.setFocusable(false);
		switchButton.addActionListener(this);

		setLayout(new BorderLayout());
		add(new JLabel(" "), BorderLayout.WEST);
		add(label, BorderLayout.CENTER);
		JPanel p = new JPanel(new BorderLayout());
		p.add(toolBar, BorderLayout.CENTER);
		p.add(switchButton, BorderLayout.EAST);
		add(p, BorderLayout.EAST);
		toolBar.setPreferredSize(new Dimension(150, getPreferredSize().height));
		switchButton.setPreferredSize(new Dimension(50, getPreferredSize().height));
		setPreferredSize(new Dimension(getPreferredSize().width, 32));

		setBackground(this);
	}

	private void setBackground(Container container) {
		container.setBackground(Color.LIGHT_GRAY);
		for (Component component : container.getComponents()) {
			if (component instanceof Container) {
				setBackground((Container) component);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (COLLAPSE.equals(switchButton.getText())) {
			for (TitleBarListener listener : listeners) {
				listener.collapsed();
			}
			switchButton.setText(EXPAND);
		} else {
			for (TitleBarListener listener : listeners) {
				listener.expanded();
			}
			switchButton.setText(COLLAPSE);
		}
	}

	public void addTitleBarListener(TitleBarListener listener) {
		listeners.add(listener);
	}

	public void removeTitleBarListener(TitleBarListener listener) {
		listeners.remove(listener);
	}
}
