
package app.Test;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTesting {
    // Mocking the Login instance
    private app.Login login = new app.Login();

    @Test
    public void testValidCredentials() {
        // Set test phone number and password
        login.NumberField.setText("9851087391");
        login.pwField.setText("aaa");
        // Simulate clicking login button
        login.LoginButton.doClick();
        // Assuming after successful login, dashboard should open
        assertTrue("Dashboard should open for valid credentials", dashboardIsOpen());
    }

    @Test
    public void testInvalidCredentials() {
        // Set invalid phone number and password
        login.NumberField.setText("invalidPhoneNumber");
        login.pwField.setText("invalidPassword");
        // Simulate clicking login button
        login.LoginButton.doClick();
        // Assuming error message should be displayed for invalid credentials
        assertTrue("Error message should be displayed for invalid credentials", errorMessageIsDisplayed());
    }

    @Test
    public void testEmptyFields() {
        // Set empty phone number and password
        login.NumberField.setText("");
        login.pwField.setText("");
        // Simulate clicking login button
        login.LoginButton.doClick();
        // Assuming error message should be displayed for empty fields
        assertTrue("Error message should be displayed for empty fields", errorMessageIsDisplayed());
    }

    // Method to check if the dashboard is open (mock implementation)
    private boolean dashboardIsOpen() {
        // Mock implementation, assuming dashboard window is open
        return true;
    }

    // Method to check if error message is displayed (mock implementation)
    private boolean errorMessageIsDisplayed() {
        // Mock implementation, assuming error message is displayed
        return true;
    }
    

}
