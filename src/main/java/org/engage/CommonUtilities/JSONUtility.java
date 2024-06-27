package org.engage.CommonUtilities;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class JSONUtility {
      public static Object getValueFromJSON(List<String> jsonFilePaths, String regEx) {
        for (String jsonFilePath : jsonFilePaths) {
            try {
                File schemaFile = new File(jsonFilePath);
                JSONTokener schemaData = new JSONTokener(new FileInputStream(schemaFile));
                JSONObject json = new JSONObject(schemaData);
                return JsonPath.read(json.toString(), regEx);
            } catch (Exception e) {
                ExtentSparkReport.getExtentLogger().info("Failed to get value from JSON file: " + jsonFilePath + ". Error message: " + e.getMessage());
            }
        }
        String errorMessage = "Failed to get value from any JSON file for the regex: " + regEx;
        Assert.fail(errorMessage);
        ExtentSparkReport.getExtentLogger().fail(errorMessage);
        return null;
    }
}
