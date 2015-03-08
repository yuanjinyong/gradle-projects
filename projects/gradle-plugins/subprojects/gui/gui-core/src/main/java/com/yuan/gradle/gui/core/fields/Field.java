/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;


/**
 * @author Yuanjy
 * @param <E>
 *
 */
public class Field<T extends Widget<?>> extends JLabel implements ValueChangedEventSource {
    private static final long serialVersionUID = 1L;
    private T field;

    public Field(String labelText, T field) {
        super(labelText);
        initComponents(field);
    }

    public Field(String labelText, int labelHorizontalAlignment, T field) {
        super(labelText, labelHorizontalAlignment);
        initComponents(field);
    }

    public Field(Icon labelIcon, T field) {
        super(labelIcon);
        initComponents(field);
    }

    public Field(Icon labelIcon, int labelHorizontalAlignment, T field) {
        super(labelIcon, labelHorizontalAlignment);
        initComponents(field);
    }

    public Field(String labelText, Icon labelIcon, int labelHorizontalAlignment, T field) {
        super(labelText, labelIcon, labelHorizontalAlignment);
        initComponents(field);
    }

    private void initComponents(T field) {
        this.field = field;
    }

    public T getField() {
        return field;
    }

    @Override
    public void setVisible(boolean visibleFlag) {
        super.setVisible(visibleFlag);
        ((JComponent) field).setVisible(visibleFlag);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.yuan.gradle.gui.core.fields.ValueChangedEventSource#addValueChangedListener(com.yuan.gradle.gui.core.fields
     * .ValueChangedListener)
     */
    @Override
    public void addValueChangedListener(ValueChangedListener listener) {
        getField().addValueChangedListener(listener);
    }

    public Object getValue() {
        return getField().getValue();
    }
}
