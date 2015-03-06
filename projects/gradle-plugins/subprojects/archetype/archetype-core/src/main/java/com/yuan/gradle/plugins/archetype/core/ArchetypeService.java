/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


/**
 * @author Yuanjy
 *
 */
public class ArchetypeService {
    /**
     * 从Classpath中加载原型
     *
     * @return
     */
    public Map<String, ArchetypeDescriptor> loadArchetypes() {
        Map<String, ArchetypeDescriptor> archetypeCache = new HashMap<String, ArchetypeDescriptor>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:META-INF/archetypes/*.properties");
            for (Resource resource : resources) {
                addArchetypeDescriptor(archetypeCache, resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return archetypeCache;
    }

    private void addArchetypeDescriptor(Map<String, ArchetypeDescriptor> archetypeCache, Resource resource) {
        InputStream in = null;
        try {
            URL url = resource.getURL();
            String filePath = url.getFile();
            String archetypeId = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
            if (archetypeCache.containsKey(archetypeId)) {
                ArchetypeDescriptor archetype = archetypeCache.get(archetypeId);
                System.out.println("原型[" + archetypeId + "|" + archetype.getName() + "|"
                        + archetype.getImplementationClass() + "]中已经在" + archetype.getDescriptorUrl() + "存在，忽略" + url
                        + "。");
            } else {
                in = url.openStream();
                Properties p = new Properties();
                p.load(in);
                ArchetypeDescriptor archetype = new ArchetypeDescriptor();
                archetypeCache.put(archetypeId, archetype);
                archetype.setId(archetypeId);
                archetype.setName(p.getProperty(ArchetypeDescriptor.PROP_NAME));
                archetype.setImplementationClass(p.getProperty(ArchetypeDescriptor.PROP_CLASS));
                archetype.setResourceUrl(new URL(url.getProtocol() + ":"
                        + filePath.substring(0, filePath.indexOf("META-INF")) + "archetype-resources/" + archetypeId));
                archetype.setDescriptorUrl(url);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param archetype
     *            原型描述
     * @param archetypeParams
     *            原型参数
     */
    public void generateArchetype(ArchetypeDescriptor archetype, Map<String, String> archetypeParams) {
        Map<String, Map<String, String>> buildParams = new HashMap<String, Map<String, String>>();
        buildParams.put("archetype", archetypeParams);
        System.out.println(buildParams);
    }
}
