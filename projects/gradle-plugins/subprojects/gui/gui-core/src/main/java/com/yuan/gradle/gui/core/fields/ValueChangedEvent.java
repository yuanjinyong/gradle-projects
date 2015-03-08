/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import java.awt.event.ItemEvent;

import javax.swing.event.DocumentEvent;


/**
 * @author Yuanjy
 *
 */
public class ValueChangedEvent {
    public static final int INSERT_UPDATE = 0;
    public static final int REMOVE_UPDATE = 1;
    public static final int CHANGED_UPDATE = 2;
    public static final int SELECTED_ITEM = 3;
    public static final int DESELECTED_ITEM = 4;
    private Field<?> eventSource;
    private int eventType;
    private DocumentEvent documentEvent;
    private ItemEvent itemEvent;

    public ValueChangedEvent(Field<?> eventSource, int eventType, DocumentEvent documentEvent) {
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.documentEvent = documentEvent;
    }

    public ValueChangedEvent(Field<?> eventSource, int eventType, ItemEvent itemEvent) {
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.itemEvent = itemEvent;
    }

    public Field<?> getEventSource() {
        return eventSource;
    }

    public void setEventSource(Field<?> eventSource) {
        this.eventSource = eventSource;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public DocumentEvent getDocumentEvent() {
        return documentEvent;
    }

    public void setDocumentEvent(DocumentEvent documentEvent) {
        this.documentEvent = documentEvent;
    }

    public ItemEvent getItemEvent() {
        return itemEvent;
    }

    public void setItemEvent(ItemEvent itemEvent) {
        this.itemEvent = itemEvent;
    }
}
