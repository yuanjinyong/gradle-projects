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
    private String description;
    private String implementationClass;
    private URL resources;
    private URL descriptorFile;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("id=").append(id);
        sb.append(", ").append("name=").append(name);
        sb.append(", ").append("description=").append(description);
        sb.append(", ").append("implementationClass=").append(implementationClass);
        sb.append(", ").append("resources=").append(resources);
        sb.append(", ").append("descriptorFile=").append(descriptorFile).append('}');
        return sb.toString();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImplementationClass() {
        return implementationClass;
    }

    public void setImplementationClass(String implementationClass) {
        this.implementationClass = implementationClass;
    }

    public URL getResources() {
        return resources;
    }

    public void setResources(URL resources) {
        this.resources = resources;
    }

    public URL getDescriptorFile() {
        return descriptorFile;
    }

    public void setDescriptorFile(URL descriptorFile) {
        this.descriptorFile = descriptorFile;
    }
}
