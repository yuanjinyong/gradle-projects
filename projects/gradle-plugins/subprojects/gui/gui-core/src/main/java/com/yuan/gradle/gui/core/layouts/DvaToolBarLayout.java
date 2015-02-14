/**
 *
 */
package com.yuan.gradle.gui.core.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

/**
 * @author Yuanjy
 *
 */
public class DvaToolBarLayout implements java.awt.LayoutManager {
	private JPopupMenu popupMenu = new JPopupMenu();
	private JButton popupButton = new JButton(">>");

	public DvaToolBarLayout(Container parent) {
		parent.add(popupButton);
		popupButton.setVisible(false);
		popupButton.setFocusable(false);
		popupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComponent component = (JComponent) e.getSource();
				popupMenu.show(component, 0, component.getHeight());
			}
		});
	}

	@Override
	public void layoutContainer(Container parent) {
		if (parent.isVisible()) {
			// Position all buttons in the container
			Insets insets = parent.getInsets();
			int x = insets.left;
			int toolBarWidth = parent.getSize().width;
			int totalWidth = insets.right + insets.left;

			popupButton.setSize(popupButton.getPreferredSize());
			popupButton.setVisible(false);
			for (Component component : parent.getComponents()) {
				if (!component.equals(popupButton)) {
					component.setVisible(true);
					component.setSize(component.getPreferredSize());
					component.setLocation(x, (parent.getSize().height - component.getSize().height) / 2);
					int componentWidth = component.getPreferredSize().width;
					x += componentWidth;
					totalWidth += componentWidth;
				}
			}

			// All the buttons won't fit, add extender button
			// Note: the size of the extender button changes once it is added
			// to the container. Add it here so correct width is used.
			if (totalWidth > toolBarWidth) {
				popupButton.setVisible(true);
				int popupX = toolBarWidth - insets.right - popupButton.getSize().width;
				popupButton.setLocation(popupX, (parent.getSize().height - popupButton.getSize().height) / 2);
				totalWidth += popupButton.getSize().width;

				// Remove buttons that don't fit and add to the popup menu
				popupMenu.removeAll();
				int lastVisibleButtonIndex = parent.getComponentCount();
				while (totalWidth > toolBarWidth) {
					lastVisibleButtonIndex--;
					Component component = parent.getComponent(lastVisibleButtonIndex);
					component.setVisible(false);
					totalWidth -= component.getSize().width;

					addComponentToPopup(component);
				}
			}
		}
	}

	private void addComponentToPopup(Component component) {
		if (component instanceof AbstractButton) {
			popupMenu.insert(createMenuItem((AbstractButton) component), 0);
		}

		if (component instanceof JToolBar.Separator) {
			popupMenu.insert(new JSeparator(), 0);
		}
	}

	private JMenuItem createMenuItem(AbstractButton button) {
		JMenuItem menuItem = new JMenuItem(button.getText());
		menuItem.setIcon(button.getIcon());
		menuItem.setToolTipText(button.getToolTipText());
		for (ActionListener l : button.getActionListeners()) {
			menuItem.addActionListener(l);
		}

		return menuItem;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return popupButton.getMinimumSize();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// Calculate the width of all components in the container
		Dimension d = new Dimension();
		d.width += parent.getInsets().right + parent.getInsets().left;
		for (Component component : parent.getComponents()) {
			if (component.isVisible()) {
				d.width += component.getPreferredSize().width;
				d.height = Math.max(d.height, component.getPreferredSize().height);
			}
		}

		d.height += parent.getInsets().top + parent.getInsets().bottom + 5;
		return d;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	@Override
	public void removeLayoutComponent(Component comp) {

	}
}