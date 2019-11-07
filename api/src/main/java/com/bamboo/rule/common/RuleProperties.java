package com.bamboo.rule.common;

import com.bamboo.rule.exception.RuleException;
import java.io.File;
import java.net.URL;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Application properties used by Rule engine
 */
public final class RuleProperties extends PropertiesConfiguration {

  public static final String RULE_CONFIGURATION_DIRECTORY_PROPERTY = "rule.conf";
  public static final String RULE_PROPERTIES = "rule.properties";
  private static volatile Configuration instance = null;

  private RuleProperties(URL url) throws ConfigurationException {
    super(url);
  }

  public static void forceReload() {
    if (instance != null) {
      synchronized (RuleProperties.class) {
        if (instance != null) {
          instance = null;
        }
      }
    }
  }

  public static Configuration get() throws RuleException {
    if (instance == null) {
      synchronized (RuleProperties.class) {
        if (instance == null) {
          instance = get(RULE_PROPERTIES);
        }
      }
    }
    return instance;
  }

  public static Configuration get(String fileName) throws RuleException {
    String confLocation = System.getProperty(RULE_CONFIGURATION_DIRECTORY_PROPERTY);
    try {
      URL url;

      if (confLocation == null) {
        url = RuleProperties.class.getClassLoader().getResource(fileName);
        if (url == null) {
          url = RuleProperties.class.getClassLoader().getResource("/" + fileName);
        }
      } else {
        url = new File(confLocation, fileName).toURI().toURL();
      }

      RuleProperties appProperties = new RuleProperties(url);
      return appProperties.interpolatedConfiguration();
    } catch (Exception e) {
      throw new RuleException("Failed to load application properties", e);
    }
  }

  public static Configuration getSubsetConfiguration(String prefix) throws RuleException {
    return get().subset(prefix);
  }

}
