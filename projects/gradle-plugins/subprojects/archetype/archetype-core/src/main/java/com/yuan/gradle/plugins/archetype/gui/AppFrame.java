package com.yuan.gradle.plugins.archetype.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JComboBoxField;
import com.yuan.gradle.gui.core.fields.JComboBoxField.ItemType;
import com.yuan.gradle.gui.core.fields.JFileField;
import com.yuan.gradle.gui.core.frames.AbstractFrame;
import com.yuan.gradle.gui.core.panels.NavigateBar;
import com.yuan.gradle.gui.core.panels.TitleBar;
import com.yuan.gradle.gui.core.partitions.BasicTablePartition;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.gui.core.partitions.WizardPartition;
import com.yuan.gradle.plugins.archetype.core.AbstractArchetype;
import com.yuan.gradle.plugins.archetype.core.ArchetypeDescriptor;
import com.yuan.gradle.plugins.archetype.core.ProjectInfo;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


public class AppFrame extends AbstractFrame {
    private static final long serialVersionUID = 1L;
    private static final String MENU_HELP = "帮助";
    private static final String MENUITEM_GUIDE = "使用说明";
    private static final String BTN_CREATE_PROJECT = "创建工程";

    private Map<String, ArchetypeDescriptor> archetypeCache;

    //private TabbedPane tabbedPane;
    private TextArea outputArea;
    private Field<JFileField> projectLocationField;
    private Field<JTextField> projectNameField;
    private Field<JTextField> projectDirField;
    private Field<JTextField> descriptionField;
    private Field<JTextField> groupField;
    private Field<JTextField> archiveBaseNameField;
    private Field<JTextField> versionField;
    private Field<JTextField> packageField;
    private Field<JTextField> projectPathField;
    private Field<JTextField> buildFileNameField;;
    private Field<JFileField> settingsFileField;
    private Field<JComboBoxField<String>> archetypeField;
    private AbstractArchetype archetypeParamsPt;
    private BasicTablePartition t2;
    private JButton createProjectButton;

    public AppFrame() {
        super("创建Gradle工程");
        archetypeCache = loadArchetypes();
        initFrame();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setIconImage(getImage("/image/icon.gif"));
    }

    /**
     * 从Classpath中加载原型
     *
     * @return
     */
    private Map<String, ArchetypeDescriptor> loadArchetypes() {
        Map<String, ArchetypeDescriptor> archetypeCache = new HashMap<String, ArchetypeDescriptor>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:META-INF/archetypes/*.properties");
            for (Resource resource : resources) {
                addArchetypeDescriptor(archetypeCache, resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return archetypeCache;
    }

    private void addArchetypeDescriptor(Map<String, ArchetypeDescriptor> archetypeCache, Resource resource) {
        InputStream in = null;
        try {
            URL url = resource.getURL();
            String filePath = url.getFile();
            String archetypeId = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
            if (archetypeCache.containsKey(archetypeId)) {
                ArchetypeDescriptor archetype = archetypeCache.get(archetypeId);
                System.out.println("原型[" + archetypeId + "|" + archetype.getName() + "|"
                        + archetype.getImplementationClass() + "]中已经在" + archetype.getDescriptorUrl() + "存在，忽略" + url
                        + "。");
            } else {
                in = url.openStream();
                Properties p = new Properties();
                p.load(in);
                ArchetypeDescriptor archetype = new ArchetypeDescriptor();
                archetypeCache.put(archetypeId, archetype);
                archetype.setId(archetypeId);
                archetype.setName(p.getProperty(ArchetypeDescriptor.PROP_NAME));
                archetype.setImplementationClass(p.getProperty(ArchetypeDescriptor.PROP_CLASS));
                archetype.setResourceUrl(new URL(url.getProtocol() + ":"
                        + filePath.substring(0, filePath.indexOf("META-INF")) + "archetype-resources/" + archetypeId));
                archetype.setDescriptorUrl(url);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        outputArea = new TextArea();
        //outputArea.setRows(2);
        //        LogUtil.setOutputArea(outputArea);
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
                .addDocumentListener(new AppDocumentListener(AppFrame.this, projectLocationField));
                projectNameField = createTextField("*工程名称：", "sample");
                projectNameField.getField().getDocument()
                        .addDocumentListener(new AppDocumentListener(AppFrame.this, projectNameField));

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
                groupField = createTextField("Group：", "com.yuan");
                groupField.getField().setToolTipText("项目组。");
                groupField.getField().getDocument()
                        .addDocumentListener(new AppDocumentListener(AppFrame.this, groupField));
                archiveBaseNameField = createTextField("ArchiveBaseName：", "");
                archiveBaseNameField.getField().setToolTipText("输出构件名称。");
                versionField = createTextField("Version：", "1.0.0-SNAPSHOT");
                versionField.getField().setToolTipText("输出构件版本号。");
                packageField = createTextField("Package：", "");
                packageField.getField().setToolTipText("工程默认的包名。");
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

            @SuppressWarnings("unchecked")
            @Override
            protected ContainerTablePartition createContentPane() {
                archetypeField = this.createComboBoxField("*原型：", null, ItemType.TEXT,
                        archetypeCache.keySet().toArray(new String[0]), "Normal Java Project");

                BasicTablePartition t1 = new BasicTablePartition();
                t1.addFieldRow(archetypeField);
                t1.addFieldCol(archetypeField);

                archetypeParamsPt = createArchetypeParamsPartition(archetypeCache.get(archetypeField.getField()
                        .getSelectedItem()));
                t2 = new BasicTablePartition();
                if (archetypeParamsPt != null) {
                    t2.addGroupRow(archetypeParamsPt);
                    t2.addGroupCol(archetypeParamsPt);
                }

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

    public class AppDocumentListener implements DocumentListener {
        private AppFrame app;
        private Field<?> field;

        public AppDocumentListener(AppFrame app, Field<?> field) {
            this.app = app;
            this.field = field;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            app.fieldValueChanged("insertUpdate", e, field);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            app.fieldValueChanged("removeUpdate", e, field);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            app.fieldValueChanged("changedUpdate", e, field);
        }
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
    }

    private void updateAllFields() {
        updateProjectDirField();
        updateArchiveBaseNameField();
        updatePackageField();
        updateProjectPathField();
    }

    private void updateProjectDirField() {
        File projectDir = new File(projectLocationField.getField().getFile(), projectNameField.getField().getText());
        projectDirField.getField().setText(projectDir.getAbsolutePath());
    }

    private void updateArchiveBaseNameField() {
        archiveBaseNameField.getField().setText(projectNameField.getField().getText());
    }

    private void updateProjectPathField() {
        projectPathField.getField().setText(':' + projectNameField.getField().getText());
    }

    private void updatePackageField() {
        String projectName = projectNameField.getField().getText();
        projectName = projectName.toLowerCase().replaceAll("-", ".").replaceAll("_", ".");
        packageField.getField().setText(groupField.getField().getText() + '.' + projectName);
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
        ValidateUtil.isEmptyString(projectDirField.getField().getText(), "工程位置不能为空！");
        ValidateUtil.isEmptyString(projectNameField.getField().getText(), "工程名称不能为空！");

        ProjectInfo project = new ProjectInfo();
        project.setProjectDir(new File(projectDirField.getField().getText()));
        project.setBuildFileName(buildFileNameField.getField().getText());
        project.setName(projectNameField.getField().getText());
        project.setPath(projectPathField.getField().getText());
        project.setDescription(descriptionField.getField().getText());
        project.setGroup(groupField.getField().getText());
        project.setArchiveBaseName(archiveBaseNameField.getField().getText());
        project.setVersion(versionField.getField().getText());
        project.setPkg(packageField.getField().getText());
        project.setPkgPath(project.getPkg().length() == 0 ? "" : project.getPkg().replaceAll(".", "/"));
        project.setSettingsFile(settingsFileField.getField().getFile());

        archetypeParamsPt.generateArchetype(archetypeCache.get(archetypeField.getField().getSelectedItem()), project);
    }
}
