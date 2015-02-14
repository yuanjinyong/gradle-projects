package com.yuan.gradle.plugins.archetype.core;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JComboBoxField;
import com.yuan.gradle.gui.core.fields.JFileField;
import com.yuan.gradle.gui.core.frames.AbstractFrame;
import com.yuan.gradle.gui.core.panels.NavigateBar;
import com.yuan.gradle.gui.core.panels.TabbedPane;
import com.yuan.gradle.gui.core.panels.TitleBar;
import com.yuan.gradle.gui.core.partitions.BasicTablePartition;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.gui.core.partitions.WizardPartition;


public class AppFrame extends AbstractFrame {
    private static final long serialVersionUID = 1L;
    private static final String MENU_HELP = "帮助";
    private static final String MENUITEM_GUIDE = "使用说明";
    private static final String BTN_CREATE_PROJECT = "创建工程";

    private TextArea outputArea;
    private TabbedPane tabbedPane;

    private Field<JTextField> projectNameField;
    private Field<JFileField> projectLocationField;
    private Field<JTextField> descriptionField;
    private Field<JTextField> groupField;
    private Field<JTextField> archiveBaseNameField;
    private Field<JTextField> versionField;
    private Field<JTextField> packageField;
    private Field<JFileField> settingsGradleField;
    private Field<JComboBoxField<String>> archetypeField;
    private WizardPartition archetypeParamsPt;
    private JButton createProjectButton;

    public AppFrame() {
        super("创建Gradle工程");
        //setIconImage(getImage("/image/icon.gif"));
    }

    @Override
    protected void setFrameSize(Dimension screenSize) {
        // setMinimumSize(new Dimension(1280, 600));
        // setPreferredSize(screenSize);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU_HELP);
        menuBar.add(menu);
        //menu.addActionListener(this);
        JMenuItem menuItem = new JMenuItem(MENUITEM_GUIDE);
        menu.add(menuItem);
        menuItem.addActionListener(this);
        return menuBar;
    }

    @Override
    protected JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar("工具栏");
        toolBar.add(createButton("工具栏按钮"));

        return toolBar;
    }

    @Override
    protected JPanel createCenterPanel() {
        tabbedPane = new TabbedPane();
        tabbedPane.setCloseButtonEnabled(false);
        tabbedPane.addTab("工程信息", null, createFrameContent());
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        return tabbedPane;
    }

    private WizardPartition createFrameContent() {
        return new WizardPartition() {
            private static final long serialVersionUID = 1L;

            @Override
            protected ContainerTablePartition createContentPane() {
                WizardPartition locationPt = createLocationPartition();
                WizardPartition basicInfoPt = createBasicInfoPartition();
                WizardPartition archetypeInfoPt = createArchetypeInfoPartition();

                BasicTablePartition left = new BasicTablePartition();
                left.addGroupRow(Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, locationPt);
                left.addGroupRow(Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, basicInfoPt);
                left.addGroupCol(locationPt, basicInfoPt);

                ContainerTablePartition content = new ContainerTablePartition();
                content.addGroupRow(Alignment.LEADING, left, archetypeInfoPt);
                content.addGroupCol(left);
                content.addGroupCol(archetypeInfoPt);
                return content;
            }

            @Override
            protected NavigateBar createNavigateBar() {
                createProjectButton = createButton(BTN_CREATE_PROJECT);
                return new NavigateBar(FlowLayout.TRAILING, createProjectButton);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                switch (actionCommand) {
                    case BTN_CREATE_PROJECT:
                        JOptionPane.showMessageDialog(this, BTN_CREATE_PROJECT);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, actionCommand);
                }
            }
        };
    }

    private WizardPartition createArchetypeInfoPartition() {
        return new WizardPartition() {
            private static final long serialVersionUID = 1L;

            @Override
            protected TitleBar createTitleBar() {
                return new TitleBar("原型信息");
            }

            @SuppressWarnings("unchecked")
            @Override
            protected ContainerTablePartition createContentPane() {
                List<String> archetypeList = new ArrayList<String>();
                archetypeList.add("Java");
                archetypeList.add("Gradle Archetype");
                archetypeList.add("SSH");
                archetypeField = this.createComboBoxField("原型：", archetypeList, "Java");

                BasicTablePartition t1 = new BasicTablePartition();
                t1.addFieldRow(archetypeField);
                t1.addFieldCol(archetypeField);

                archetypeParamsPt = createArchetypeParamsPartition();

                ContainerTablePartition content = new ContainerTablePartition();
                if (archetypeParamsPt != null) {
                    BasicTablePartition t2 = new BasicTablePartition();
                    t2.addGroupRow(archetypeParamsPt);
                    t2.addGroupCol(archetypeParamsPt);

                    content.addGroupRow(t1);
                    content.addGap(Gap.ROW, 30);
                    content.addGroupRow(t2);
                    content.addGroupCol(t1, t2);
                } else {
                    content.addGroupRow(t1);
                    content.addGroupCol(t1);
                }
                return content;
            }

            private WizardPartition createArchetypeParamsPartition() {
                return null;
            }
        };
    }

    private WizardPartition createBasicInfoPartition() {
        return new WizardPartition() {
            private static final long serialVersionUID = 1L;

            @Override
            protected TitleBar createTitleBar() {
                return new TitleBar("工程基本信息");
            }

            @SuppressWarnings("unchecked")
            @Override
            protected ContainerTablePartition createContentPane() {
                groupField = createTextField("Group：", "com.yuan");
                archiveBaseNameField = createTextField("ArchiveBaseName：", "sample");
                versionField = createTextField("Version：", "1.0.0-SNAPSHOT");
                packageField = createTextField("Package：", groupField.getField().getText() + "."
                        + archiveBaseNameField.getField().getText());
                descriptionField = createTextField("Description：", "");
                settingsGradleField = createFileField("Root Project's Settings File：", "", "请选择根工程的settings.gradle文件",
                        JFileChooser.FILES_ONLY, new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().equals("settings.gradle") || f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "settings.gradle";
                    }
                });

                ContainerTablePartition content = new ContainerTablePartition();
                content.addFieldRow(groupField);
                content.addFieldRow(archiveBaseNameField);
                content.addFieldRow(versionField);
                content.addFieldRow(packageField);
                content.addFieldRow(descriptionField);
                content.addFieldRow(settingsGradleField);
                content.addFieldCol(groupField, archiveBaseNameField, versionField, packageField, descriptionField,
                        settingsGradleField);
                return content;
            }
        };
    }

    private WizardPartition createLocationPartition() {
        return new WizardPartition() {
            private static final long serialVersionUID = 1L;

            @Override
            protected TitleBar createTitleBar() {
                return new TitleBar("工程位置");
            }

            @SuppressWarnings("unchecked")
            @Override
            protected ContainerTablePartition createContentPane() {
                projectNameField = createTextField("工程名称：", "");
                projectLocationField = createFileField("工程位置：", "", "请选工程要保存的目录", JFileChooser.DIRECTORIES_ONLY);

                ContainerTablePartition content = new ContainerTablePartition();
                content.addFieldRow(projectNameField);
                content.addFieldRow(projectLocationField);
                content.addFieldCol(projectNameField, projectLocationField);
                return content;
            }
        };
    }

    @Override
    protected JPanel createSouthPanel() {
        JPanel outputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;

        outputPanel.add(new JLabel("输出"), c);
        outputArea = new TextArea();
        //outputArea.setRows(100);
        c.gridy = 1;
        c.weighty = 2000.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        outputPanel.add(outputArea, c);

        //        LogUtil.setOutputArea(outputArea);

        return outputPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case MENUITEM_GUIDE:
                JOptionPane.showMessageDialog(this, MENU_HELP);
                break;
            case BTN_CREATE_PROJECT:
                JOptionPane.showMessageDialog(this, BTN_CREATE_PROJECT);
                break;
            default:
                JOptionPane.showMessageDialog(this, actionCommand);
        }
    }
}
