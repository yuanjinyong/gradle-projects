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
public class JavaArchetype extends AbstractArchetype {
    private static final long serialVersionUID = 1L;
    private Field<JTextField> buildFileNameField;

    public JavaArchetype(AppFrame app) {
        super(app);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#getArchetypeDescription()
     */
    @Override
    protected String getArchetypeDescription() {
        return "普通的Java工程。";
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#initArchetypeFields()
     */
    @Override
    protected List<Field<? extends Component>> getArchetypeFields() {
        List<Field<? extends Component>> fieldList = new ArrayList<Field<? extends Component>>();
        buildFileNameField = createTextField("*Build File Name：", "build.gradle");
        fieldList.add(buildFileNameField);
        return fieldList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#fillArchetypeParams(java.util.Map)
     */
    @Override
    public void fillArchetypeParams(Map<String, String> archetypeParams) throws Exception {
        ValidateUtil.isEmptyString(buildFileNameField.getField().getText(), "Build File Name不能为空！");
        archetypeParams.put("buildFileName", buildFileNameField.getField().getText());
    }
}
