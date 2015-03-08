/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.yuan.gradle.gui.core.layouts.VFlowLayout;


/**
 * @author Yuanjy
 *
 */
public class JRadioWidget extends JPanel implements Widget<String> {
    private static final long serialVersionUID = 1L;

    public static final int FLOWLAYOUT_H = 0;
    public static final int FLOWLAYOUT_V = 1;

    private ButtonGroup buttonGroup;
    private JRadioButton[] radioButtons;

    public JRadioWidget(String[] options) {
        this(options, null);
    }

    public JRadioWidget(String[] options, String value) {
        this(options, value, FLOWLAYOUT_H);
    }

    public JRadioWidget(String[] options, int layoutType) {
        this(options, layoutType, layoutType == FLOWLAYOUT_V ? VFlowLayout.MIDDLE : FlowLayout.LEADING);
    }

    public JRadioWidget(String[] options, int layoutType, int align) {
        this(options, null, layoutType, align);
    }

    public JRadioWidget(String[] options, String value, int layoutType) {
        this(options, value, layoutType, layoutType == FLOWLAYOUT_V ? VFlowLayout.MIDDLE : FlowLayout.LEADING);
    }

    public JRadioWidget(String[] options, String value, int layoutType, int align) {
        initComponents(options, value, layoutType, align);
    }

    private void initComponents(String[] options, String value, int layoutType, int align) {
        if (layoutType == FLOWLAYOUT_V) {
            setLayout(new VFlowLayout(align));
        } else {
            setLayout(new FlowLayout(align));
        }
        buttonGroup = new ButtonGroup();
        radioButtons = new JRadioButton[options.length];
        for (int i = 0; i < options.length; i++) {
            radioButtons[i] = new JRadioButton(options[i]);
            // Insets insets = radioButtons[i].getMargin();
            // radioButtons[i].setMargin(new Insets(-4, insets.left, -3, insets.right));
            // Dimension preferredSize = radioButtons[i].getPreferredSize();
            // radioButtons[i].setPreferredSize(new Dimension(preferredSize.width, preferredSize.height - 2));
            buttonGroup.add(radioButtons[i]);
            add(radioButtons[i]);
            if (options[i].equals(value)) {
                radioButtons[i].setSelected(true);
            }
        }
    }

    public void addActionListener(ActionListener l) {
        for (JRadioButton radioButton : radioButtons) {
            radioButton.addActionListener(l);
        }
    }

    public void removeActionListener(ActionListener l) {
        for (JRadioButton radioButton : radioButtons) {
            radioButton.removeActionListener(l);
        }
    }

    public void addItemListener(ItemListener l) {
        for (JRadioButton radioButton : radioButtons) {
            radioButton.addItemListener(l);
        }
    }

    public void removeItemListener(ItemListener l) {
        for (JRadioButton radioButton : radioButtons) {
            radioButton.removeItemListener(l);
        }
    }

    public void setValue(String value) {
        for (JRadioButton radioButton : radioButtons) {
            if (radioButton.getText().equals(value)) {
                radioButton.setSelected(true);
            } else {
                radioButton.setSelected(false);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.yuan.gradle.gui.core.fields.Widget#getValue()
     */
    @Override
    public String getValue() {
        for (JRadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                return radioButton.getText();
            }
        }
        return null;
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
        addItemListener(listener);
    }
}
