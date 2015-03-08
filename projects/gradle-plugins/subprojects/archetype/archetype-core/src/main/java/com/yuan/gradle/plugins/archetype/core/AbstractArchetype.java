/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;

import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilationFailedException;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.partitions.AbstractPartition;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;
import com.yuan.gradle.plugins.archetype.utils.LogUtil;


/**
 * @author Yuanjy
 *
 */
public abstract class AbstractArchetype extends AbstractPartition {
    private static final long serialVersionUID = 1L;
    protected AppFrame appFrame;
    protected ArchetypeDescriptor achetypeDescriptor;
    private SimpleTemplateEngine engine = new SimpleTemplateEngine();

    public AbstractArchetype(AppFrame appFrame, ArchetypeDescriptor achetypeDescriptor) {
        this.appFrame = appFrame;
        this.achetypeDescriptor = achetypeDescriptor;
        initPartition();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.gui.core.partitions.AbstractPartition#initLayout()
     */
    @Override
    protected void initLayout() {
        setLayout(new BorderLayout());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.gui.core.partitions.AbstractPartition#initFields()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void initFields() {
        JTextPane jta = new JTextPane();
        //jta.setSize(100, Short.MAX_VALUE);
        jta.setText(getArchetypeDescription());
        jta.setSize(100, 76);
        jta.setPreferredSize(new Dimension(100, 76));
        jta.setOpaque(false);
        add(jta, BorderLayout.NORTH);

        List<Field<? extends Component>> fieldList = getArchetypeFields();
        if (fieldList != null) {
            ContainerTablePartition content = new ContainerTablePartition();
            Field<? extends Component>[] fields = new Field[fieldList.size()];
            for (int i = 0; i < fieldList.size(); i++) {
                fields[i] = fieldList.get(i);
                content.addFieldRow(fields[i]);
            }
            content.addFieldCol(fields);
            add(new JScrollPane(content), BorderLayout.CENTER);
        }
    }

    /**
     * 获取原型的描述信息。
     *
     * @return 原型的描述信息
     */
    protected abstract String getArchetypeDescription();

    /**
     * 获取原型的参数字段列表。
     *
     * @return 原型的参数字段列表
     */
    protected abstract List<Field<? extends Component>> getArchetypeFields();

    public void fieldValueChanged(String eventName, DocumentEvent e, Field<?> field) {

    }

    /**
     * @param project
     *            工程信息
     */
    public void generateArchetype(ProjectInfo project) throws Exception {
        //校验工程信息
        verfiyProjectInfo(project);
        //构造模板需要使用到的参数
        Map<String, Map<String, Object>> templateParams = buildTemplateParams(project);

        LogUtil.debug("原型：" + achetypeDescriptor.toString());
        LogUtil.debug("参数：" + templateParams.toString());
        //提取出模板文件到工程目录下
        extractResourcesFiles(project.getProjectDir());
        //对工程文件进行渲染
        LogUtil.info("渲染工程文件：" + project.getProjectDir().getAbsolutePath());
        renderFiles(project.getProjectDir(), templateParams);
    }

    private void verfiyProjectInfo(ProjectInfo project) throws Exception {
        //校验工程名，开头必须是大小写字母，其他位可以有大小写字符、数字、下划线
        if (!project.getName().matches("[a-zA-Z][a-zA-Z0-9_]{4,15}{1}")) {
            //throw new Exception("多工程设置文件" + settingsFile.getAbsolutePath() + "不存在且不为空，请重新选择！");
        }
        File projectDir = project.getProjectDir();
        if (projectDir.exists() && projectDir.list().length > 0) {
            throw new Exception("目录" + projectDir.getAbsolutePath() + "已经存在且不为空，请重新选择！");
        }

        File settingsFile = project.getSettingsFile();
        if (settingsFile != null && !settingsFile.exists()) {
            throw new Exception("多工程设置文件" + settingsFile.getAbsolutePath() + "不存在且不为空，请重新选择！");
        }
    }

    private Map<String, Map<String, Object>> buildTemplateParams(ProjectInfo project) throws Exception {
        Map<String, Object> archetypeParams = getArchetypeParams(project);
        if (archetypeParams == null) {
            archetypeParams = new HashMap<String, Object>();
        }
        archetypeParams.put("project", project);
        Map<String, Map<String, Object>> templateParams = new HashMap<String, Map<String, Object>>();
        templateParams.put("archetype", archetypeParams);
        return templateParams;
    }

    /**
     * 获取原型的参数。
     *
     * @return 原型的参数
     * @throws Exception
     */
    protected abstract Map<String, Object> getArchetypeParams(ProjectInfo project) throws Exception;

    private void extractResourcesFiles(File projectDir) throws URISyntaxException, IOException {
        if (!projectDir.exists()) {
            LogUtil.info("创建工程目录：" + projectDir.getAbsolutePath());
            FileUtils.forceMkdir(projectDir);
        }

        URL resources = achetypeDescriptor.getResources();
        if (resources.getProtocol().equals("file")) {
            File srcDir = new File(resources.toURI());
            LogUtil.info("复制工程文件：" + resources + " -> " + projectDir.getAbsolutePath());
            FileUtils.copyDirectory(srcDir, projectDir, false); //不保留源文件的时间
            return;
        }

        LogUtil.info("提取工程文件：" + resources + " -> " + projectDir.getAbsolutePath());
        extractFiles(resources, projectDir);
    }

    /**
     * 从jar包中提取指定的目录下的所有子目录和文件到指定的目录中。
     *
     * @throws IOException
     */
    private void extractFiles(URL jarEntryUrl, File destDir) throws IOException {
        if (!jarEntryUrl.getProtocol().equals("jar")) {
            return;
        }

        JarURLConnection con = (JarURLConnection) jarEntryUrl.openConnection();
        String entryName = con.getJarEntry() + "/";
        JarFile jarFile = con.getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (!jarEntry.getName().startsWith(entryName)) {
                continue;
            }

            File destFile = new File(destDir, jarEntry.getName().substring(entryName.length()));
            if (jarEntry.isDirectory()) {
                FileUtils.forceMkdir(destFile); // 这段都可以不要，因为每次都貌似从最底层开始遍历的
            } else {
                FileUtils.forceMkdir(destFile.getParentFile());

                int length;
                byte b[] = new byte[1024];
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = jarFile.getInputStream(jarEntry);
                    out = new FileOutputStream(destFile);
                    while ((length = in.read(b)) > 0) {
                        out.write(b, 0, length);
                    }
                    out.flush();
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }
    }

    private void renderFiles(File projectDir, Map<String, Map<String, Object>> params)
            throws CompilationFailedException, ClassNotFoundException, IOException {
        for (File file : projectDir.listFiles()) {
            //先渲染文件名
            String newFileName = engine.createTemplate(file.getAbsolutePath().replaceAll("\\\\", "/")).make(params)
                    .toString();

            //重命名
            File newFile = new File(newFileName);
            if (!file.getAbsolutePath().equals(newFile.getAbsolutePath())) {
                LogUtil.debug("重命名文件：" + file.getAbsolutePath() + " -> " + newFile.getAbsolutePath());
                //如果只是文件名变更，目录结构没有变更，则改名
                if (file.getParentFile().equals(newFile.getParentFile())) {
                    file.renameTo(newFile);
                }
                //如果目录结构也变更了，则移动新目录下，并删除老目录
                else {
                    newFile.mkdirs();
                    FileUtils.copyDirectory(file, newFile, false); //不保留源文件的时间
                    FileUtils.deleteDirectory(file);
                }
            }

            //如果是目录，递归处理
            if (newFile.isDirectory()) {
                renderFiles(newFile, params);
            }
            //如果是文件，渲染文件内容
            else {
                InputStreamReader reader = null;
                OutputStreamWriter writer = null;
                try {
                    reader = new InputStreamReader(new FileInputStream(newFile), "UTF-8");
                    Writable result = engine.createTemplate(reader).make(params);
                    writer = new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8");
                    result.writeTo(writer);
                    writer.flush();
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
        }
    }
}
