/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import javax.swing.JPasswordField;
import javax.swing.text.Document;


/**
 * @author Yuanjy
 *
 */
public class JPasswordWidget extends JPasswordField implements Widget<String> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public JPasswordWidget() {
    }

    /**
     * @param text
     */
    public JPasswordWidget(String text) {
        super(text);
    }

    /**
     * @param columns
     */
    public JPasswordWidget(int columns) {
        super(columns);
    }

    /**
     * @param text
     * @param columns
     */
    public JPasswordWidget(String text, int columns) {
        super(text, columns);
    }

    /**
     * @param doc
     * @param txt
     * @param columns
     */
    public JPasswordWidget(Document doc, String txt, int columns) {
        super(doc, txt, columns);
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
        return new String(getPassword());
    }

}
