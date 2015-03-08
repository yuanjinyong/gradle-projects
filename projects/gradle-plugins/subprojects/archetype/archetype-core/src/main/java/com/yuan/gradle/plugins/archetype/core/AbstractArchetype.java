/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.partitions.AbstractPartition;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;


/**
 * @author Yuanjy
 *
 */
public abstract class AbstractArchetype extends AbstractPartition {
    private static final long serialVersionUID = 1L;
    protected AppFrame app;

    public AbstractArchetype(AppFrame app) {
        this.app = app;
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

    /**
     * @param archetype
     *            原型描述
     * @param archetypeParams
     *            原型参数
     */
    public void generateArchetype(ArchetypeDescriptor archetype, ProjectInfo project) throws Exception {
        Map<String, Map<String, Object>> templateParams = buildTemplateParams(project);
        System.out.println(templateParams);
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        Template template = engine.createTemplate(new File("'"));
        template.make(templateParams);
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
    public abstract Map<String, Object> getArchetypeParams(ProjectInfo project) throws Exception;
}
