/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;


/**
 * @author Yuanjy
 *
 */
public class JTextAreaWidget extends JScrollPane implements Widget<String> {
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;

    public JTextAreaWidget() {
        this(null, null, 0, 0);
    }

    public JTextAreaWidget(String text) {
        this(null, text, 0, 0);
    }

    public JTextAreaWidget(int rows, int columns) {
        this(null, null, rows, columns);
    }

    public JTextAreaWidget(String text, int rows, int columns) {
        this(null, text, rows, columns);
    }

    public JTextAreaWidget(Document doc) {
        this(doc, null, 0, 0);
    }

    public JTextAreaWidget(Document doc, String text, int rows, int columns) {
        textArea = new JTextArea(doc, text, rows, columns);
        super.setViewportView(textArea);
    }

    public Document getDocument() {
        return textArea.getDocument();
    }

    @Override
    public void addValueChangedListener(ValueChangedListener listener) {
        getDocument().addDocumentListener(listener);
    }

    public void setEditable(boolean b) {
        textArea.setEditable(b);
    }

    @Override
    public void setToolTipText(String text) {
        textArea.setToolTipText(text);
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
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
