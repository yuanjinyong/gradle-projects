/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import java.awt.Component;
import java.util.List;
import java.util.Map;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;


/**
 * @author Yuanjy
 *
 */
public class BasicArchetype extends AbstractArchetype {
    private static final long serialVersionUID = 1L;

    public BasicArchetype(AppFrame appFrame, ArchetypeDescriptor achetypeDescriptor) {
        super(appFrame, achetypeDescriptor);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#getArchetypeDescription()
     */
    @Override
    protected String getArchetypeDescription() {
        return achetypeDescriptor.getDescription();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#initArchetypeFields()
     */
    @Override
    protected List<Field<? extends Component>> getArchetypeFields() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.yuan.gradle.plugins.archetype.core.AbstractArchetype#getArchetypeParams(com.yuan.gradle.plugins.archetype
     * .core.ProjectInfo)
     */
    @Override
    protected Map<String, Object> getArchetypeParams(ProjectInfo project) throws Exception {
        return null;
    }
}
