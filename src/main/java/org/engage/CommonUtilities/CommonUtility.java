package org.engage.CommonUtilities;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.engage.Base.BaseUtilities;

import java.io.*;

public class CommonUtility extends BaseUtilities
{
    public static void writePropertyFile(String propertyKey, String propertyValue, String propertyFilePath) {
        File f = new File(propertyFilePath);
        PropertiesConfiguration config = new PropertiesConfiguration();
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
        try {
            try {
                layout.load(new InputStreamReader(new FileInputStream(f)));
            } catch (org.apache.commons.configuration.ConfigurationException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        config.setProperty(propertyKey, propertyValue);
        try {
            try {
                layout.save(new FileWriter(propertyFilePath, false));
            } catch (org.apache.commons.configuration.ConfigurationException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
