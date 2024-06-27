package org.engage.CommonUtilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class RetryAnalyzer implements IRetryAnalyzer {
    private final Map<String, Integer> retryCounts = new HashMap<>();
    private final Map<String, ITestResult> firstRunFailures = new HashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String testName = result.getName();
        int retryCount = retryCounts.getOrDefault(testName, 0);

        if (retryCount < 1 && result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Retrying test " + testName + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            retryCounts.put(testName, retryCount + 1);
            firstRunFailures.put(testName, result);
            return true;
        } else if (retryCount > 0 && firstRunFailures.containsKey(testName)) {
            ITestResult firstRunResult = firstRunFailures.get(testName);
            System.out.println("Marking the first run failure of test " + testName + " as SKIP.");
            firstRunResult.setStatus(ITestResult.SKIP);
            return false;
        }

        return false;
    }

    private String getResultStatusName(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return "SUCCESS";
            case ITestResult.FAILURE:
                return "FAILURE";
            case ITestResult.SKIP:
                return "SKIP";
            default:
                return "UNKNOWN";
        }
    }
}
