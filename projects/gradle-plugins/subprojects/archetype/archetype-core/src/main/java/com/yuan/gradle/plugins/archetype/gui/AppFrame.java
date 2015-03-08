package com.yuan.gradle.plugins.archetype.gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.lang.reflect.Constructor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JComboBoxWidget;
import com.yuan.gradle.gui.core.fields.JFileWidget;
import com.yuan.gradle.gui.core.fields.JTextWidget;
import com.yuan.gradle.gui.core.frames.AbstractFrame;
import com.yuan.gradle.gui.core.panels.NavigateBar;
import com.yuan.gradle.gui.core.panels.TitleBar;
import com.yuan.gradle.gui.core.partitions.BasicTablePartition;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.gui.core.partitions.WizardPartition;
import com.yuan.gradle.plugins.archetype.core.AbstractArchetype;
import com.yuan.gradle.plugins.archetype.core.ArchetypeDescriptor;
import com.yuan.gradle.plugins.archetype.core.ArchetypeService;
import com.yuan.gradle.plugins.archetype.core.BasicArchetype;
import com.yuan.gradle.plugins.archetype.core.FieldValueChangedListener;
import com.yuan.gradle.plugins.archetype.core.ProjectInfo;
import com.yuan.gradle.plugins.archetype.utils.LogUtil;
import com.yuan.gradle.plugins.archetype.utils.StringUtil;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


public class AppFrame extends AbstractFrame {
    private static final long serialVersionUID = 1L;
    private static final String MENU_HELP = "帮助";
    private static final String MENUITEM_GUIDE = "使用说明";
    private static final String BTN_CREATE_PROJECT = "创建工程";

    public Field<JFileWidget> projectLocationField;
    public Field<JTextWidget> projectNameField;
    public Field<JTextWidget> projectDirField;
    public Field<JTextWidget> descriptionField;
    public Field<JTextWidget> groupField;
    public Field<JTextWidget> archiveBaseNameField;
    public Field<JTextWidget> versionField;
    public Field<JTextWidget> packageField;
    public Field<JTextWidget> projectPathField;
    public Field<JTextWidget> buildFileNameField;;
    public Field<JFileWidget> settingsFileField;
    public Field<JComboBoxWidget<String>> archetypeField;
    public BasicTablePartition archetypePartition;
    public JButton createProjectButton;

    private ArchetypeService service = new ArchetypeService();
    private AbstractArchetype archetype;

    //private TabbedPane tabbedPane;
    private TextArea outputArea;

    public AppFrame() {
        super("创建Gradle工程");
        outputArea = new TextArea();
        LogUtil.setOutputArea(outputArea);
        initFrame();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setIconImage(getImage("/image/icon.gif"));
    }

    @Override
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU_HELP);
        menuBar.add(menu);

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
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(outputArea);
        return p;
    }

    @Override
    protected JPanel createCenterPanel() {
        //        tabbedPane = new TabbedPane();
        //        tabbedPane.setCloseButtonEnabled(false);
        //        tabbedPane.addTab("工程信息", null, createFrameContent());
        //        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        //        tabbedPane.setTitleBoldAt(tabbedPane.getTabCount() - 1, true);
        //        return tabbedPane;
        //    }
        //
        //    private JPanel createFrameContent() {
        WizardPartition locationPt = createLocationPartition();
        WizardPartition basicInfoPt = createBasicInfoPartition();
        WizardPartition archetypeInfoPt = createArchetypeInfoPartition();

        updateAllFields();

        BasicTablePartition left = new BasicTablePartition();
        left.addGroupRow(Alignment.LEADING, locationPt);
        left.addGroupRow(Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE, basicInfoPt);
        left.addGroupCol(locationPt, basicInfoPt);

        JPanel table = new JPanel(new GridLayout(1, 2, 10, 10));
        table.add(left);
        table.add(archetypeInfoPt);

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
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
                projectLocationField = createFileField("*工程位置：", new File("").getAbsolutePath(), "请选工程要保存的目录。",
                        JFileChooser.DIRECTORIES_ONLY);
                projectLocationField.getField().setEditable(false);
                projectLocationField.getField().getDocument()
                .addDocumentListener(new FieldValueChangedListener(AppFrame.this, projectLocationField));
                projectNameField = createTextField("*工程名称：", "sample");
                projectNameField.getField().getDocument()
                        .addDocumentListener(new FieldValueChangedListener(AppFrame.this, projectNameField));

                ContainerTablePartition content = new ContainerTablePartition();
                content.addFieldRow(projectLocationField);
                content.addFieldRow(projectNameField);
                content.addFieldCol(projectLocationField, projectNameField);
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
                groupField = createTextField("*Group：", "com.yuan");
                groupField.getField().setToolTipText("项目组。");
                groupField.getField().getDocument()
                        .addDocumentListener(new FieldValueChangedListener(AppFrame.this, groupField));
                archiveBaseNameField = createTextField("ArchiveBaseName：", "");
                archiveBaseNameField.getField().setToolTipText("输出构件名称。");
                versionField = createTextField("Version：", "1.0.0-SNAPSHOT");
                versionField.getField().setToolTipText("输出构件版本号。");
                packageField = createTextField("Package：", "");
                packageField.getField().setToolTipText("工程默认的包名。");
                packageField.getField().getDocument()
                .addDocumentListener(new FieldValueChangedListener(AppFrame.this, packageField));
                updatePackageField();
                descriptionField = createTextField("Description：", "");
                descriptionField.getField().setToolTipText("工程描述。");
                projectPathField = createTextField("Path：", "");
                projectPathField.getField().setToolTipText("多工程结构时，本工程相对Root工程的路径。");
                projectDirField = createTextField("Project Directory：", "");
                projectDirField.getField().setEditable(false);
                buildFileNameField = createTextField("Build File Name：", "build.gradle");
                buildFileNameField.getField().setToolTipText("构建脚本名称。");
                settingsFileField = createFileField("Root Project's Settings File：", "",
                        "多工程结构时，Root工程下多工程设置文件。必须为根工程的settings.gradle文件。", JFileChooser.FILES_ONLY, new FileFilter() {
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
                content.addFieldRow(projectPathField);
                content.addFieldRow(projectDirField);
                content.addFieldRow(buildFileNameField);
                content.addFieldRow(settingsFileField);
                content.addFieldCol(groupField, archiveBaseNameField, versionField, packageField, descriptionField,
                        projectPathField, projectDirField, buildFileNameField, settingsFileField);
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

            @Override
            protected ContainerTablePartition createContentPane() {
                BasicTablePartition top = createTopPartition();
                archetypePartition = new BasicTablePartition();

                ContainerTablePartition content = new ContainerTablePartition();
                content.addGroupRow(top);
                content.addGroupRow(archetypePartition);
                content.addGroupCol(top, archetypePartition);
                return content;
            }

            @SuppressWarnings("unchecked")
            private BasicTablePartition createTopPartition() {
                archetypeField = createComboBoxField("*原型：", null, new String[0], "");

                BasicTablePartition top = new BasicTablePartition();
                top.addFieldRow(archetypeField);
                top.addFieldCol(archetypeField);

                return top;
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ItemEvent.SELECTED == e.getStateChange()) {
                    updateArchetypePartition();
                }
            }
        };
    }

    public void addFieldValueChangedListener(Field<?> field) {

    }

    public void fieldValueChanged(String eventName, DocumentEvent e, Field<?> field) {
        if (field == projectLocationField) {
            updateProjectDirField();
        } else if (field == projectNameField) {
            updateProjectDirField();
            updateArchiveBaseNameField();
            updatePackageField();
            updateProjectPathField();
        } else if (field == groupField) {
            updatePackageField();
        }

        if (archetype != null) {
            archetype.fieldValueChanged(eventName, e, field);
        }
    }

    private void updateAllFields() {
        updateProjectDirField();
        updateArchiveBaseNameField();
        updatePackageField();
        updateProjectPathField();
        updateArchetypeField();
    }

    private void updateProjectDirField() {
        File projectDir = new File(projectLocationField.getField().getFile(), projectNameField.getField().getText());
        projectDirField.getField().setText(projectDir.getAbsolutePath());
    }

    private void updateArchiveBaseNameField() {
        archiveBaseNameField.getField().setText(projectNameField.getField().getText());
    }

    private void updateProjectPathField() {
        projectPathField.getField().setText(StringUtil.toProjectPath(projectNameField.getField().getText()));
    }

    private void updatePackageField() {
        packageField.getField().setText(
                StringUtil.toPackage(groupField.getField().getText(), projectNameField.getField().getText()));
    }

    private void updateArchetypeField() {
        for (String item : service.getArchetypeList()) {
            archetypeField.getField().addItem(item);
        }
        archetypeField.getField().setSelectedIndex(0);
        updateArchetypePartition();
    }

    private void updateArchetypePartition() {
        archetype = createArchetype(service
                .getArchetypeDescriptor((String) archetypeField.getField().getSelectedItem()));
        if (archetype != null) {
            archetypePartition.resetLayout();
            archetypePartition.addGroupRow(archetype);
            archetypePartition.addGroupCol(archetype);
        }
    }

    private AbstractArchetype createArchetype(ArchetypeDescriptor archetypeDescriptor) {
        try {
            String clzName = archetypeDescriptor.getImplementationClass();
            if (ValidateUtil.isEmptyString(clzName)) {
                return new BasicArchetype(AppFrame.this, archetypeDescriptor);
            }

            Class<?> clz = Class.forName(clzName.trim());
            Constructor<?> con = clz.getConstructor(new Class[] { AppFrame.class, ArchetypeDescriptor.class }); //获取带参数的构造方法
            AbstractArchetype archetype = (AbstractArchetype) con.newInstance(new Object[] { AppFrame.this,
                    archetypeDescriptor }); // 构造方法传入的参数
            return archetype;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
        return null;
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
                    LogUtil.info("创建工程完成。");
                    JOptionPane.showMessageDialog(this, "创建工程完成。");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, actionCommand);
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createProject() throws Exception {
        ValidateUtil.isEmptyString(projectDirField, "工程位置不能为空！");
        ValidateUtil.isEmptyString(projectNameField, "工程名称不能为空！");
        ValidateUtil.isEmptyString(groupField, "Group不能为空！");

        ProjectInfo project = new ProjectInfo();
        project.setProjectDir(new File(projectDirField.getField().getText()));
        project.setBuildFileName(buildFileNameField.getField().getText());
        project.setName(projectNameField.getField().getText());
        project.setPath(projectPathField.getField().getText());
        project.setDescription(descriptionField.getField().getText());
        project.setGroup(groupField.getField().getText());
        project.setArchiveBaseName(archiveBaseNameField.getField().getText());
        project.setVersion(versionField.getField().getText());
        project.setPkg((String) packageField.getValue());
        project.setPkgPath(ValidateUtil.isEmptyString(project.getPkg()) ? "" : project.getPkg().replaceAll("\\.", "/"));
        project.setSettingsFile((File) settingsFileField.getValue());

        archetype.generateArchetype(project);
    }
}
