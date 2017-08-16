package com.restservice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

@Configuration
public class PropertiesLogger {

  private static final Logger LOG = LoggerFactory.getLogger(PropertiesLogger.class);

  @Autowired
  private AbstractEnvironment env;

  
  @PostConstruct
  public void printProperties() {
    LOG.info("**** APPLICATION PROPERTIES SOURCES ****");

    Set<String> properties = new TreeSet<>();
    List<PropertiesPropertySource> propertySources = findPropertiesPropertySources();
    for (PropertiesPropertySource propertySource : propertySources) {
      LOG.info(propertySource.toString());
      properties.addAll(Arrays.asList(propertySource.getPropertyNames()));
    }

    LOG.info("**** APPLICATION PROPERTIES VALUES ****");
    print(properties);
  }

  
  private List<PropertiesPropertySource> findPropertiesPropertySources() {
    List<PropertiesPropertySource> propertiesPropertySource = new LinkedList<>();

    for (PropertySource<?> propertySource : env.getPropertySources()) {
      if (propertySource instanceof PropertiesPropertySource) {
        propertiesPropertySource.add((PropertiesPropertySource) propertySource);
      }
    }
    return propertiesPropertySource;
  }

  private void print(Set<String> properties) {
    for (String propertyName : properties) {
      LOG.info("{}={}", propertyName, env.getProperty(propertyName));
    }
  }
}
