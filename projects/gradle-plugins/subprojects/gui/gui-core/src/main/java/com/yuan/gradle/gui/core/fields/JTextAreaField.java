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
public class JTextAreaField extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;

	public JTextAreaField() {
		this(null, null, 0, 0);
	}

	public JTextAreaField(String text) {
		this(null, text, 0, 0);
	}

	public JTextAreaField(int rows, int columns) {
		this(null, null, rows, columns);
	}

	public JTextAreaField(String text, int rows, int columns) {
		this(null, text, rows, columns);
	}

	public JTextAreaField(Document doc) {
		this(doc, null, 0, 0);
	}

	public JTextAreaField(Document doc, String text, int rows, int columns) {
		textArea = new JTextArea(doc, text, rows, columns);
		super.setViewportView(textArea);
	}

	public Document getDocument() {
		return textArea.getDocument();
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
}
