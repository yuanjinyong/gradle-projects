/**
 *
 */
package com.yuan.gradle.gui.core.fields;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import com.yuan.gradle.gui.core.dialogs.AbstractDialog;
import com.yuan.gradle.gui.core.panels.NavigateBar;
import com.yuan.gradle.gui.core.partitions.ContainerTablePartition;
import com.yuan.gradle.gui.core.partitions.WizardPartition;


/**
 * @author Yuanjy
 * @param <E>
 *
 */
public abstract class JComboBoxWidget<E> extends JComboBox<E> implements Widget<E> {
    private static final long serialVersionUID = 1L;
    private static final String ADD_NEWITEM = "添加";
    private CreateItemDialog addNewItemDlg;
    private E createItem;

    public enum ItemType {
        TEXT, FILE, DIRECTOY
    }

    public JComboBoxWidget() {
        this((E) null);
    }

    public JComboBoxWidget(E createItem) {
        this(createItem, ItemType.TEXT);
    }

    public JComboBoxWidget(E createItem, ItemType itemType) {
        initComponents(createItem, itemType);
    }

    public JComboBoxWidget(E[] items) {
        this(items, (E) null);
    }

    public JComboBoxWidget(E[] items, E createItem) {
        this(items, createItem, ItemType.TEXT);
    }

    public JComboBoxWidget(E[] items, E createItem, ItemType itemType) {
        super(items);
        initComponents(createItem, itemType);
    }

    private void initComponents(E createItem, ItemType itemType) {
        if (createItem != null) {
            this.createItem = createItem;
            addItem(createItem);
            addNewItemDlg = new CreateItemDialog(this, itemType);
            addNewItemDlg.setTitle(createItem.toString());
        }
    }

    abstract protected E buildItem(String itemText);

    @Override
    protected void fireItemStateChanged(ItemEvent e) {
        if (e.getItem().equals(createItem)) {
            if (ItemEvent.SELECTED == e.getStateChange()) {
                addNewItemDlg.setVisible(true);
            }

            return;
        }

        super.fireItemStateChanged(e);
    }

    public class CreateItemDialog extends AbstractDialog {
        private static final long serialVersionUID = 1L;
        private Field<JFileWidget> fileField;
        private Field<JTextWidget> textField;
        private JComboBoxWidget<E> comboBox;

        public CreateItemDialog(JComboBoxWidget<E> comboBox, ItemType itemType) {
            super();
            this.comboBox = comboBox;
            setModal(true);
            setItemType(itemType);
            Dimension minimumSize = new Dimension(400, getPreferredSize().height);
            setMinimumSize(minimumSize);
            setSize(minimumSize);
        }

        @Override
        protected WizardPartition createContentPane() {
            return new WizardPartition() {
                private static final long serialVersionUID = 1L;

                @Override
                protected ContainerTablePartition createContentPane() {
                    textField = createTextField("可选项：", "");
                    fileField = createFileField("可选项：", "", "点击按钮选择", JFileChooser.FILES_ONLY);
                    fileField.getField().setEditable(false);

                    ContainerTablePartition content = new ContainerTablePartition();
                    content.addGroupRow(textField, textField.getField());
                    content.addGroupRow(fileField, fileField.getField());
                    content.addGroupCol(Alignment.TRAILING, textField, fileField);
                    content.addGroupCol(textField.getField(), fileField.getField());

                    return content;
                }

                @Override
                protected NavigateBar createNavigateBar() {
                    return new NavigateBar(FlowLayout.TRAILING, createButton(ADD_NEWITEM));
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();
                    if (ADD_NEWITEM.equals(actionCommand)) {
                        E item = comboBox.buildItem(fileField.getField().getText());
                        comboBox.insertItemAt(item, comboBox.getItemCount() - 1);
                        comboBox.setSelectedItem(item);
                        CreateItemDialog.this.dispose();
                    }
                }
            };
        }

        public void setItemType(ItemType itemType) {
            if (ItemType.DIRECTOY.equals(itemType)) {
                fileField.getField().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            }

            if (ItemType.TEXT.equals(itemType)) {
                fileField.setVisible(false);
            } else {
                textField.setVisible(false);
            }
        }
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

    /*
     * (non-Javadoc)
     *
     * @see com.yuan.gradle.gui.core.fields.Widget#getValue()
     */
    @SuppressWarnings("unchecked")
    @Override
    public E getValue() {
        return (E) getSelectedItem();
    }
}
