/**
 *
 */
package com.yuan.gradle.gui.core.frames;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


/**
 * @author Yuanjy
 *
 */
public abstract class AbstractFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    /**
     * @throws HeadlessException
     */
    public AbstractFrame() throws HeadlessException {
    }

    /**
     * @param gc
     */
    public AbstractFrame(GraphicsConfiguration gc) {

        super(gc);
    }

    /**
     * @param title
     * @throws HeadlessException
     */
    public AbstractFrame(String title) throws HeadlessException {
        super(title);
    }

    /**
     * @param title
     * @param gc
     */
    public AbstractFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    protected void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = createMenuBar();
        if (menuBar != null) {
            setJMenuBar(menuBar);
        }

        setLayout(new BorderLayout()); // 设置布局管理器
        JToolBar toolBar = createToolBar();
        if (toolBar != null) {
            add(BorderLayout.NORTH, toolBar);
        }
        add(BorderLayout.CENTER, createCenterPanel());
        JPanel southPanel = createSouthPanel();
        if (southPanel != null) {
            add(BorderLayout.SOUTH, southPanel);
        }

        // showBorder(this, BorderFactory.createLineBorder(Color.RED));

        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(800, 480));
        setMaximumSize(screenSize);

        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setFrameSize(screenSize);

        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getBounds().height) / 2);
    }

    protected void showBorder(boolean show) {
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

    protected void setFrameSize(Dimension screenSize) {

    }

    protected abstract JMenuBar createMenuBar();

    protected abstract JToolBar createToolBar();

    protected abstract JPanel createCenterPanel();

    protected abstract JPanel createSouthPanel();

    protected Image getImage(String imagePath) {
        URL imgUrl = this.getClass().getResource(imagePath);
        return Toolkit.getDefaultToolkit().getImage(imgUrl);
    }

    protected JMenuBar createMenuBar(File menuFile) {
        //        try {
        //            MenuBar menuBarData = XmlUtil.xmlFileToObject(menuFile, MenuBar.class);
        //            JMenuBar menuBar = new JMenuBar();
        //            for (Menu menuData : menuBarData.getMenus()) {
        //                JMenu menu = new JMenu(menuData.getText());
        //                menuBar.add(menu);
        //                for (MenuItem menuItemData : menuData.getMenuItems()) {
        //                    if (menuItemData.isSeparator() != null && menuItemData.isSeparator()) {
        //                        menu.addSeparator();
        //                    } else {
        //                        menu.add(createMenuItem(menuItemData.getText()));
        //                    }
        //                }
        //            }
        //
        //            return menuBar;
        //        } catch (JAXBException e) {
        //            e.printStackTrace();
        //            return null;
        //        }

        return null;
    }

    protected JMenuItem createMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        return menuItem;
    }

    protected JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        return button;
    }

    protected JToggleButton createToggleButton(String label) {
        JToggleButton button = new JToggleButton(label);
        button.addActionListener(this);
        return button;
    }

}
