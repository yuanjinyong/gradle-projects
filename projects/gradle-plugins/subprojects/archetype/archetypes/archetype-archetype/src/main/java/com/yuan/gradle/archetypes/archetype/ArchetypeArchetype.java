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
public class ArchetypeArchetype extends BasicArchetype {
    private static final long serialVersionUID = 1L;
    private Field<JTextWidget> idField;
    private Field<JTextWidget> nameField;
    private Field<JTextWidget> classNameField;
    private Field<JTextWidget> fullClassNameField;
    private Field<JTextWidget> descriptionField;

    public ArchetypeArchetype(AppFrame appFrame, ArchetypeDescriptor achetypeDescriptor) {
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
        idField = createTextField("*原型ID：", appFrame.projectNameField.getField().getText());
        idField.getField().getDocument().addDocumentListener(new FieldValueChangedListener(appFrame, idField));
        nameField = createTextField("*原型名称：", "");
        descriptionField = createTextField("原型描述：", "请修改原型描述。");
        classNameField = createTextField("实现类名：", "");
        classNameField.getField().getDocument()
                .addDocumentListener(new FieldValueChangedListener(appFrame, classNameField));
        fullClassNameField = createTextField("实现类全称：", "");
        fullClassNameField.getField().setEditable(false);
        fieldList.add(idField);
        fieldList.add(nameField);
        fieldList.add(descriptionField);
        fieldList.add(classNameField);
        fieldList.add(fullClassNameField);

        updateAllFields();
        return fieldList;
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
        ValidateUtil.isEmptyString(idField.getField().getText(), "原型ID不能为空！");
        ValidateUtil.isEmptyString(nameField.getField().getText(), "原型名称不能为空！");

        Map<String, Object> archetypeParams = new HashMap<String, Object>();
        archetypeParams.put("archetypeId", idField.getField().getText());
        archetypeParams.put("archetypeName", nameField.getField().getText());
        archetypeParams.put("description", descriptionField.getField().getText());
        archetypeParams.put("className", classNameField.getField().getText());
        archetypeParams.put("implementationClass", fullClassNameField.getField().getText());
        return archetypeParams;
    }

    @Override
    public void fieldValueChanged(String eventName, DocumentEvent e, Field<?> field) {
        if (field == appFrame.projectNameField) {
            updateIdField();
        } else if (field == appFrame.packageField) {
            updateFullClassNameField();
        } else if (field == idField) {
            updateNameField();
            updateClassNameField();
        } else if (field == classNameField) {
            updateFullClassNameField();
        }
    }

    private void updateAllFields() {
        updateIdField();
        updateNameField();
        updateClassNameField();
        updateFullClassNameField();
    }

    private void updateIdField() {
        idField.getField().setText(appFrame.projectNameField.getField().getText());
    }

    private void updateNameField() {
        nameField.getField().setText(idField.getField().getText() + " Project");
    }

    private void updateClassNameField() {
        classNameField.getField().setText(StringUtil.toClassName(idField.getField().getText(), "Archetype"));
    }

    private void updateFullClassNameField() {
        if (ValidateUtil.isEmptyString(classNameField.getField().getText())) {
            fullClassNameField.getField().setText("");
        } else {
            fullClassNameField.getField().setText(
                    StringUtil.toFullClassName(appFrame.packageField.getField().getText(), classNameField.getField()
                            .getText()));
        }
    }
}
