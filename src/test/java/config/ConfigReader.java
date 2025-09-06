package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/java/config/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration properties file cannot be found");
        }
    }

    public static String getProperty(String key) {
        String systemProperty = System.getProperty(key);
        return systemProperty != null ? systemProperty : properties.getProperty(key);
    }
}