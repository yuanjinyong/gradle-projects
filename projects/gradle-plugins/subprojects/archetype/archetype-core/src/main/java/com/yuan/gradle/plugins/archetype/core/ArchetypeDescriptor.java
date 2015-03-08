/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import java.net.URL;


/**
 * 原型描述
 *
 * @author Yuanjy
 *
 */
public class ArchetypeDescriptor {
    public static final String PROP_NAME = "archetype-name";
    public static final String PROP_CLASS = "implementation-class";
    public static final String DESCRIPTION = "description";

    private String id;
    private String name;
    private String implementationClass;
    private String description;
    private URL resourceUrl;
    private URL descriptorUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImplementationClass() {
        return implementationClass;
    }

    public void setImplementationClass(String implementationClass) {
        this.implementationClass = implementationClass;
    }

    public URL getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(URL resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public URL getDescriptorUrl() {
        return descriptorUrl;
    }

    public void setDescriptorUrl(URL descriptorUrl) {
        this.descriptorUrl = descriptorUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
