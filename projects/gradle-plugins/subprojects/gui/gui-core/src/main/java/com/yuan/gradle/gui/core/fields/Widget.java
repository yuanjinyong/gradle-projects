/**
 *
 */
package com.yuan.gradle.gui.core.fields;


/**
 * @author Yuanjy
 *
 */
public interface Widget<T> extends ValueChangedEventSource {
    T getValue();

    //void setFocus();
}
