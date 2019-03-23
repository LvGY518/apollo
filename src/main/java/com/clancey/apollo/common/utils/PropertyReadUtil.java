package com.clancey.apollo.common.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class PropertyReadUtil {

    private static PropertySource<?> propertySource= null;

    private static org.springframework.core.io.Resource resource = new ClassPathResource("application.yml");
    static {
        try {
        	YamlPropertySourceLoader ymls = new YamlPropertySourceLoader();
        	propertySource = ymls.load("spring", resource).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByKey(String key) {

        if (propertySource == null) {
            return null;
        }
        return (String) propertySource.getProperty(key);
    }

    public static String getValue(String path, String key) {

        org.springframework.core.io.Resource resource = new ClassPathResource(path);
        Properties p = null;
        try {
            p = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (p == null) {
            throw new RuntimeException("Resource Not Found " + path + "!");
        }
        return p.getProperty(key);
    }

}
