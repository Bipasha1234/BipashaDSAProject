
package app.Test;
import app.RegistrationForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class RegistrationTesting {

    private RegistrationForm registrationForm;

    @Before
    public void setUp() {
        registrationForm = new RegistrationForm();
    }

    @After
    public void tearDown() {
        registrationForm = null;
    }

    @Test
    public void testRegistrationProcess() {
        // Set up test data
        String firstName = "John";
        String lastName = "Doe";
        String mobileNumber = "123456780";
        String password = "password";
        String gender = "Male";

        // Set the test data to the text fields and password field
        JTextField firstNameField = registrationForm.getFirstNameField();
        JTextField lastNameField = registrationForm.getLastNameField();
        JTextField mobileNumberField = registrationForm.getMobileNumberField();
        JPasswordField newPasswordField = registrationForm.getNewPwField();
        
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        mobileNumberField.setText(mobileNumber);
        newPasswordField.setText(password);
        
        // Set the test gender
        if (gender.equals("Male")) {
            registrationForm.getMaleRadioButton().setSelected(true);
        } else if (gender.equals("Female")) {
            registrationForm.getFemaleRadioButton().setSelected(true);
        } else {
            registrationForm.getOthersRadioButton().setSelected(true);
        }

        // Manually trigger the registration process
        registrationForm.RegisterButton.doClick();

        // Retrieve the registered data
        String registeredFirstName = registrationForm.getRegisteredFirstName();
        String registeredLastName = registrationForm.getRegisteredLastName();
        String registeredMobileNumber = registrationForm.getRegisteredMobileNumber();
        String registeredPassword = registrationForm.getRegisteredPassword();
        String registeredGender = registrationForm.getRegisteredGender();

        // Verify that the registered data matches the input data
        assertEquals(firstName, registeredFirstName);
        assertEquals(lastName, registeredLastName);
        assertEquals(mobileNumber, registeredMobileNumber);
        assertEquals(password, registeredPassword);
        assertEquals(gender, registeredGender);
    }
}
