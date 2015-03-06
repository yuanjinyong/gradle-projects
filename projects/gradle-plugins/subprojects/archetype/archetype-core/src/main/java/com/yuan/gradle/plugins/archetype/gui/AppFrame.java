package com.yuan.gradle.plugins.archetype.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

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
import com.yuan.gradle.gui.core.fields.JComboBoxField.ItemType;
import com.yuan.gradle.gui.core.fields.JFileField;
import com.yuan.gradle.gui.core.frames.AbstractFrame;
import com.yuan.gradle.gui.core.panels.NavigateBar;
import com.yuan.gradle.gui.core.panels.TabbedPane;
import com.yuan.gradle.gui.core.panels.TitleBar;
import com.yuan.gradle.gui.core.partitions.BasicTablePartition;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.gui.core.partitions.WizardPartition;
import com.yuan.gradle.plugins.archetype.core.AbstractArchetype;
import com.yuan.gradle.plugins.archetype.core.ArchetypeDescriptor;
import com.yuan.gradle.plugins.archetype.core.ArchetypeService;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


public class AppFrame extends AbstractFrame {
    private static final long serialVersionUID = 1L;
    private static final String MENU_HELP = "帮助";
    private static final String MENUITEM_GUIDE = "使用说明";
    private static final String BTN_CREATE_PROJECT = "创建工程";

    private ArchetypeService service = new ArchetypeService();
    private Map<String, ArchetypeDescriptor> archetypeCache;

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
    private AbstractArchetype archetypeParamsPt;
    private BasicTablePartition t2;
    private JButton createProjectButton;

    public AppFrame() {
        super("创建Gradle工程");
        archetypeCache = service.loadArchetypes();
        initFrame();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setIconImage(getImage("/image/icon.gif"));
    }

    @Override
    protected void setFrameSize(Dimension screenSize) {
        // setMinimumSize(new Dimension(1280, 600));
        // setPreferredSize(screenSize);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
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
    protected JPanel createCenterPanel() {
        tabbedPane = new TabbedPane();
        tabbedPane.setCloseButtonEnabled(false);
        tabbedPane.addTab("工程信息", null, createFrameContent());
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        tabbedPane.setTitleBoldAt(tabbedPane.getTabCount() - 1, true);
        return tabbedPane;
    }

    private JPanel createFrameContent() {
        WizardPartition locationPt = createLocationPartition();
        WizardPartition basicInfoPt = createBasicInfoPartition();
        WizardPartition archetypeInfoPt = createArchetypeInfoPartition();

        BasicTablePartition left = new BasicTablePartition();
        left.addGroupRow(Alignment.LEADING, locationPt);
        left.addGroupRow(Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE, basicInfoPt);
        left.addGroupCol(locationPt, basicInfoPt);

        JPanel table = new JPanel(new GridLayout(1, 2, 10, 10));
        table.add(left);
        table.add(archetypeInfoPt);

        JPanel p = new JPanel(new BorderLayout());
        p.add(BorderLayout.CENTER, table);
        createProjectButton = createButton(BTN_CREATE_PROJECT);
        p.add(BorderLayout.SOUTH, new NavigateBar(FlowLayout.RIGHT, createProjectButton));

        return p;
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
                projectNameField = createTextField("*工程名称：", "");
                projectLocationField = createFileField("*工程位置：", "", "请选工程要保存的目录", JFileChooser.DIRECTORIES_ONLY);
                projectLocationField.getField().setEditable(false);

                ContainerTablePartition content = new ContainerTablePartition();
                content.addFieldRow(projectNameField);
                content.addFieldRow(projectLocationField);
                content.addFieldCol(projectNameField, projectLocationField);
                return content;
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
                archetypeField = this.createComboBoxField("*原型：", null, ItemType.TEXT,
                        archetypeCache.keySet().toArray(new String[0]), "Normal Java Project");

                BasicTablePartition t1 = new BasicTablePartition();
                t1.addFieldRow(archetypeField);
                t1.addFieldCol(archetypeField);

                t2 = new BasicTablePartition();

                ContainerTablePartition content = new ContainerTablePartition();
                content.addGroupRow(t1);
                content.addGroupRow(t2);
                content.addGroupCol(t1, t2);
                return content;
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ItemEvent.SELECTED == e.getStateChange()) {
                    archetypeParamsPt = createArchetypeParamsPartition(archetypeCache.get(e.getItem()));
                    if (archetypeParamsPt != null) {
                        t2.resetLayout();
                        t2.addGroupRow(archetypeParamsPt);
                        t2.addGroupCol(archetypeParamsPt);
                    }
                }
            }

            private AbstractArchetype createArchetypeParamsPartition(ArchetypeDescriptor archetypeDescriptor) {
                try {
                    Class<?> clz = Class.forName(archetypeDescriptor.getImplementationClass());
                    Constructor<?> con = clz.getConstructor(new Class[] { AppFrame.class }); //获取带参数的构造方法
                    AbstractArchetype archetype = (AbstractArchetype) con.newInstance(new Object[] { AppFrame.this }); // 构造方法传入的参数
                    return archetype;
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorMsg("创建Gradle工程", e.getLocalizedMessage());
                }
                return null;
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        try {
            switch (actionCommand) {
                case MENUITEM_GUIDE:
                    JOptionPane.showMessageDialog(this, actionCommand);
                    break;
                case BTN_CREATE_PROJECT:
                    createProject();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, actionCommand);
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createProject() throws Exception {
        ValidateUtil.isEmptyString(projectNameField.getField().getText(), "工程名称不能为空！");
        ValidateUtil.isEmptyString(projectLocationField.getField().getText(), "工程位置不能为空！");

        Map<String, String> archetypeParams = new HashMap<String, String>();
        archetypeParams.put("name", projectNameField.getField().getText());
        archetypeParams.put("projectDir", new File(projectLocationField.getField().getFile(), projectNameField
                .getField().getText()).getAbsolutePath());
        archetypeParams.put("group", groupField.getField().getText());
        archetypeParams.put("archiveBaseName", archiveBaseNameField.getField().getText());
        archetypeParams.put("version", versionField.getField().getText());
        archetypeParams.put("package", packageField.getField().getText());
        archetypeParams.put("packagePath", packageField.getField().getText().replaceAll(".", "/"));
        archetypeParams.put("description", descriptionField.getField().getText());
        File settingsFile = settingsGradleField.getField().getFile();
        archetypeParams.put("settingsFile", settingsFile == null ? "" : settingsFile.getAbsolutePath());

        archetypeParamsPt.fillArchetypeParams(archetypeParams);

        service.generateArchetype(archetypeCache.get(archetypeField.getField().getSelectedItem()), archetypeParams);
    }
}
