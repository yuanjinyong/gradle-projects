/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import javax.swing.JTextField;
import javax.swing.text.Document;


/**
 * @author Yuanjy
 *
 */
public class JTextWidget extends JTextField implements Widget<String> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public JTextWidget() {
    }

    /**
     * @param text
     */
    public JTextWidget(String text) {
        super(text);
    }

    /**
     * @param columns
     */
    public JTextWidget(int columns) {
        super(columns);
    }

    /**
     * @param text
     * @param columns
     */
    public JTextWidget(String text, int columns) {
        super(text, columns);
    }

    /**
     * @param doc
     * @param text
     * @param columns
     */
    public JTextWidget(Document doc, String text, int columns) {
        super(doc, text, columns);
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
        getDocument().addDocumentListener(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.gui.core.fields.Widget#getValue()
     */
    @Override
    public String getValue() {
        return getText();
    }
}
