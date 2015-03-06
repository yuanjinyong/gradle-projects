/**
 *
 */
package com.yuan.gradle.archetypes.archetype;


import java.awt.Component;
import java.util.List;
import java.util.Map;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.plugins.archetype.core.AbstractArchetype;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;


/**
 * @author Yuanjy
 *
 */
public class JavaArchetype extends AbstractArchetype {
    private static final long serialVersionUID = 1L;

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
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.plugins.archetype.core.AbstractArchetype#fillArchetypeParams(java.util.Map)
     */
    @Override
    public void fillArchetypeParams(Map<String, String> archetypeParams) throws Exception {

    }
}
