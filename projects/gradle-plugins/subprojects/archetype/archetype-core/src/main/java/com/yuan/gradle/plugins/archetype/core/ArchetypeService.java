package com.yuan.gradle.plugins.archetype.core;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.yuan.gradle.plugins.archetype.utils.LogUtil;
import com.yuan.gradle.plugins.archetype.utils.ValidateUtil;


public class ArchetypeService {
    private Map<String, ArchetypeDescriptor> archetypeDescriptorCache;

    public ArchetypeService() {
        //archetypeDescriptorCache = loadArchetypes();
    }

    public List<String> getArchetypeList() {
        if (archetypeDescriptorCache == null) {
            archetypeDescriptorCache = loadArchetypes();
        }
        return Arrays.asList(archetypeDescriptorCache.keySet().toArray(new String[0]));
    }

    public ArchetypeDescriptor getArchetypeDescriptor(String archetypeId) {
        if (archetypeDescriptorCache == null) {
            archetypeDescriptorCache = loadArchetypes();
        }
        return archetypeDescriptorCache.get(archetypeId);
    }

    /**
     * 从Classpath中加载原型
     *
     * @return
     */
    private Map<String, ArchetypeDescriptor> loadArchetypes() {
        Map<String, ArchetypeDescriptor> archetypeCache = new HashMap<String, ArchetypeDescriptor>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            LogUtil.info("加载原型描述文件classpath*:META-INF/archetypes/*.properties");
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
            String filePath = url.getPath();
            String archetypeId = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
            if (archetypeCache.containsKey(archetypeId)) {
                ArchetypeDescriptor archetype = archetypeCache.get(archetypeId);
                LogUtil.error("原型[" + archetypeId + "|" + archetype.getName() + "|"
                        + archetype.getImplementationClass() + "]已存在[" + archetype.getDescriptorFile() + "]，忽略" + url
                        + "。");
            } else {
                URL resources = new URL(url.getProtocol(), url.getHost(), filePath.substring(0,
                        filePath.lastIndexOf('.')));
                if (!ValidateUtil.isValidateUrl(resources)) {
                    LogUtil.error("原型[" + archetypeId + "]对应的模板文件目录[" + resources + "]不存在");
                    return;
                }

                in = url.openStream();
                Properties p = new Properties();
                p.load(in);
                ArchetypeDescriptor archetype = new ArchetypeDescriptor();
                archetypeCache.put(archetypeId, archetype);
                archetype.setId(archetypeId);
                archetype.setName(p.getProperty(ArchetypeDescriptor.PROP_NAME));
                archetype.setImplementationClass(p.getProperty(ArchetypeDescriptor.PROP_CLASS));
                archetype.setDescription(p.getProperty(ArchetypeDescriptor.DESCRIPTION));
                archetype.setResources(resources);
                archetype.setDescriptorFile(url);
                LogUtil.info(archetype.toString());
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
}
