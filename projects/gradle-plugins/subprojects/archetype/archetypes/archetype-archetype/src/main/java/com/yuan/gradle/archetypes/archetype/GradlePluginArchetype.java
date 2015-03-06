/**
 *
 */
package com.yuan.gradle.archetypes.archetype;


import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.plugins.archetype.core.AbstractArchetype;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


/**
 * @author Yuanjy
 *
 */
public class GradlePluginArchetype extends AbstractArchetype {
    private static final long serialVersionUID = 1L;
    private Field<JTextField> idField;
    //private Field<JTextField> nameField;
    private Field<JTextField> classField;

    public GradlePluginArchetype(AppFrame app) {
        super(app);
    }

    @Override
    protected String getArchetypeDescription() {
        return "Gradle插件工程。";
    }

    @Override
    protected List<Field<? extends Component>> getArchetypeFields() {
        List<Field<? extends Component>> fieldList = new ArrayList<Field<? extends Component>>();
        idField = createTextField("*插件ID：", "java");
        //nameField = createTextField("*插件名称：", "java");
        classField = createTextField("*实现类：", "com.yuan.gradle.plugins.java.JavaPlugin");
        fieldList.add(idField);
        //fieldList.add(nameField);
        fieldList.add(classField);
        return fieldList;
    }

    @Override
    public void fillArchetypeParams(Map<String, String> archetypeParams) throws Exception {
        ValidateUtil.isEmptyString(idField.getField().getText(), "插件ID不能为空！");
        //ValidateUtil.isEmptyString(nameField.getField().getText(), "插件名称不能为空！");
        ValidateUtil.isEmptyString(classField.getField().getText(), "实现类不能为空！");

        archetypeParams.put("pluginId", idField.getField().getText());
        //archetypeParams.put("pluginName", nameField.getField().getText());
        archetypeParams.put("implementationClass", classField.getField().getText());
    }
}