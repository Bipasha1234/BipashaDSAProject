package app.Test;

import app.Test.TiktokFormTesting;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunnerTiktokForm {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TiktokFormTesting.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("All tests passed successfully.");
        } else {
            System.out.println("Some tests failed.");
        }
    }
}
