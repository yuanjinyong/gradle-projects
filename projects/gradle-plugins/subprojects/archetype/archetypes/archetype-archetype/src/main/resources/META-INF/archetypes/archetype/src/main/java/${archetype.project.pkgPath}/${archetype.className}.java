/**
 *
 */
package ${archetype.project.pkg};


import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.gui.core.fields.JTextWidget;
import com.yuan.gradle.plugins.archetype.core.ArchetypeDescriptor;
import com.yuan.gradle.plugins.archetype.core.BasicArchetype;
import com.yuan.gradle.plugins.archetype.core.ProjectInfo;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


/**
 * @author Yuanjy
 *
 */
public class ${archetype.className} extends BasicArchetype {
    private static final long serialVersionUID = 1L;
    //    private Field<JTextWidget> idField;
    //    private Field<JTextWidget> nameField;
    //    private Field<JTextWidget> classField;
    //    private Field<JTextWidget> descriptionField;

    public ${archetype.className}(AppFrame appFrame, ArchetypeDescriptor achetypeDescriptor) {
        super(appFrame, achetypeDescriptor);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.BasicArchetype#getArchetypeFields()
     */
    @Override
    protected List<Field<? extends Component>> getArchetypeFields() {
        //        List<Field<? extends Component>> fieldList = new ArrayList<Field<? extends Component>>();
        //        idField = createTextField("*原型ID：", "xxx");
        //        nameField = createTextField("*原型名称：", "Xxx Project");
        //        classField = createTextField("实现类：", "com.yuan.gradle.archetypes.archetype.XxxArchetype");
        //        descriptionField = createTextField("原型描述：", "请修改原型描述。");
        //        fieldList.add(idField);
        //        fieldList.add(nameField);
        //        fieldList.add(classField);
        //        fieldList.add(descriptionField);
        //        return fieldList;
        return null;
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
        //        ValidateUtil.isEmptyString(idField.getField().getText(), "原型ID不能为空！");
        //        ValidateUtil.isEmptyString(nameField.getField().getText(), "原型名称不能为空！");
        //
        //        Map<String, Object> archetypeParams = new HashMap<String, Object>();
        //        archetypeParams.put("archetypeId", idField.getField().getText());
        //        archetypeParams.put("archetypeName", nameField.getField().getText());
        //        archetypeParams.put("implementationClass", classField.getField().getText());
        //        archetypeParams.put("description", descriptionField.getField().getText());
        //        return archetypeParams;
        return null;
    }
}
