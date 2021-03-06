/**
 *
 */
package com.yuan.gradle.archetypes.archetype;


import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.DocumentEvent;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JTextWidget;
import com.yuan.gradle.plugins.archetype.core.ArchetypeDescriptor;
import com.yuan.gradle.plugins.archetype.core.BasicArchetype;
import com.yuan.gradle.plugins.archetype.core.FieldValueChangedListener;
import com.yuan.gradle.plugins.archetype.core.ProjectInfo;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;
import com.yuan.gradle.plugins.archetype.utils.StringUtil;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


/**
 * @author Yuanjy
 *
 */
public class GradlePluginArchetype extends BasicArchetype {
    private static final long serialVersionUID = 1L;
    private Field<JTextWidget> idField;
    private Field<JTextWidget> classNameField;
    private Field<JTextWidget> fullClassNameField;

    public GradlePluginArchetype(AppFrame appFrame, ArchetypeDescriptor achetypeDescriptor) {
        super(appFrame, achetypeDescriptor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.plugins.archetype.core.BasicArchetype#getArchetypeFields()
     */
    @Override
    protected List<Field<? extends Component>> getArchetypeFields() {
        List<Field<? extends Component>> fieldList = new ArrayList<Field<? extends Component>>();
        idField = createTextField("*插件ID：", "");
        idField.getField().getDocument().addDocumentListener(new FieldValueChangedListener(appFrame, idField));
        classNameField = createTextField("实现类名：", "");
        classNameField.getField().setEditable(false);
        fullClassNameField = createTextField("实现类全名：", "");
        fullClassNameField.getField().setEditable(false);
        fieldList.add(idField);
        fieldList.add(classNameField);
        fieldList.add(fullClassNameField);

        updateAllFields();
        return fieldList;
    }

    @Override
    public void fieldValueChanged(String eventName, DocumentEvent e, Field<?> field) {
        if (field == appFrame.projectNameField) {
            updateIdField();
        } else if (field == idField) {
            updateClassNameField();
            updateFullClassNameField();
        } else if (field == appFrame.packageField) {
            updateFullClassNameField();
        }
    }

    private void updateAllFields() {
        updateIdField();
        updateClassNameField();
        updateFullClassNameField();
    }

    private void updateIdField() {
        idField.getField().setText(appFrame.projectNameField.getField().getText());
    }

    private void updateClassNameField() {
        classNameField.getField().setText(StringUtil.toClassName(idField.getField().getText(), "Plugin"));
    }

    private void updateFullClassNameField() {
        fullClassNameField.getField().setText(
                StringUtil.toFullClassName(appFrame.packageField.getField().getText(), classNameField.getField()
                        .getText()));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.yuan.gradle.plugins.archetype.core.BasicArchetype#getArchetypeParams(com.yuan.gradle.plugins.archetype.core
     * .ProjectInfo)
     */
    @Override
    protected Map<String, Object> getArchetypeParams(ProjectInfo project) throws Exception {
        ValidateUtil.isEmptyString(idField.getField().getText(), "插件ID不能为空！");
        //ValidateUtil.isEmptyString(classNameField.getField().getText(), "实现类名不能为空！");
        //ValidateUtil.isEmptyString(fullClassNameField.getField().getText(), "实现类全名不能为空！");

        Map<String, Object> archetypeParams = new HashMap<String, Object>();
        archetypeParams.put("pluginId", idField.getField().getText());
        archetypeParams.put("className", classNameField.getField().getText());
        archetypeParams.put("implementationClass", fullClassNameField.getField().getText());
        return archetypeParams;
    }
}
