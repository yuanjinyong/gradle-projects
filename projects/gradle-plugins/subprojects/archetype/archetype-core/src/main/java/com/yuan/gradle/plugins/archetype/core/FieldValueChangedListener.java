/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.yuan.gradle.gui.core.fields.Field;
import com.yuan.gradle.plugins.archetype.gui.AppFrame;


/**
 * @author Yuanjy
 *
 */
public class FieldValueChangedListener implements DocumentListener {
    private AppFrame appFrame;
    private Field<?> field;

    public FieldValueChangedListener(AppFrame appFrame, Field<?> field) {
        this.appFrame = appFrame;
        this.field = field;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        appFrame.fieldValueChanged("insertUpdate", e, field);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        appFrame.fieldValueChanged("removeUpdate", e, field);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        appFrame.fieldValueChanged("changedUpdate", e, field);
    }
}
