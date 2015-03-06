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
public class ArchetypeArchetype extends AbstractArchetype {
    private static final long serialVersionUID = 1L;
    private Field<JTextField> idField;
    private Field<JTextField> nameField;
    private Field<JTextField> classField;

    public ArchetypeArchetype(AppFrame app) {
        super(app);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#getArchetypeDescription()
     */
    @Override
    protected String getArchetypeDescription() {
        return "原型（Archetype）工程。";
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#initArchetypeFields()
     */
    @Override
    protected List<Field<? extends Component>> getArchetypeFields() {
        List<Field<? extends Component>> fieldList = new ArrayList<Field<? extends Component>>();
        idField = createTextField("*原型ID：", "xxx");
        nameField = createTextField("*原型名称：", "Xxx Project");
        classField = createTextField("*实现类：", "com.yuan.gradle.archetypes.archetype.XxxArchetype");
        fieldList.add(idField);
        fieldList.add(nameField);
        fieldList.add(classField);
        return fieldList;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#fillArchetypeParams(java.util.Map)
     */
    @Override
    public void fillArchetypeParams(Map<String, String> archetypeParams) throws Exception {
        ValidateUtil.isEmptyString(idField.getField().getText(), "原型ID不能为空！");
        ValidateUtil.isEmptyString(nameField.getField().getText(), "原型名称不能为空！");
        ValidateUtil.isEmptyString(classField.getField().getText(), "实现类不能为空！");

        archetypeParams.put("archetypeId", idField.getField().getText());
        archetypeParams.put("archetypeName", nameField.getField().getText());
        archetypeParams.put("implementationClass", classField.getField().getText());
    }
}
