/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * @author Yuanjy
 *
 */
public abstract class ValueChangedListener implements DocumentListener, ItemListener {
    private Field<?> widget;

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        valueChanged(new ValueChangedEvent(widget, ValueChangedEvent.INSERT_UPDATE, e));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        valueChanged(new ValueChangedEvent(widget, ValueChangedEvent.REMOVE_UPDATE, e));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        valueChanged(new ValueChangedEvent(widget, ValueChangedEvent.CHANGED_UPDATE, e));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        int eventType = ItemEvent.SELECTED == e.getStateChange() ? ValueChangedEvent.SELECTED_ITEM
                : ValueChangedEvent.DESELECTED_ITEM;
        valueChanged(new ValueChangedEvent(widget, eventType, e));
    }

    public abstract void valueChanged(ValueChangedEvent e);
}
