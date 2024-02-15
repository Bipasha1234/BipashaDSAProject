package app.Test;

import app.TiktokForm;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TiktokFormTesting {

    private TiktokForm tiktokForm;
    private JTextField searchFieldMock;
    private JButton searchBtnMock;

    @Before
    public void setUp() {
        // Initialize TiktokForm with a dummy logged-in user ID
        tiktokForm = new TiktokForm(1);

        // Create mocks for the search field and the search button
        searchFieldMock = new JTextField();
        searchBtnMock = new JButton();
        tiktokForm.setSearchField(searchFieldMock);
        tiktokForm.setSearchBtn(searchBtnMock);
    }

    @After
    public void tearDown() {
        tiktokForm = null;
    }

    @Test
    public void testSearchBtnActionPerformedWithNonEmptyQuery() {
        // Prepare test data
        String query = "John";

        // Set the test query to the search field
        searchFieldMock.setText(query);

        // Simulate a button click event on the search button
        searchBtnMock.doClick();

        // Verify that the searchUsers method is called with the test query
        assertEquals(query, tiktokForm.getSearchField().getText());
    }

    @Test
    public void testSearchBtnActionPerformedWithEmptyQuery() {
        // Prepare test data
        String query = "";

        // Set the test query to the search field
        searchFieldMock.setText(query);

        // Simulate a button click event on the search button
        searchBtnMock.doClick();

        // Verify that the search field remains unchanged
        assertEquals(query, tiktokForm.getSearchField().getText());
    }
    @Test
    public void testDisplayOtherUsersRecommendations() {
        // Test displaying other users' recommendations
        tiktokForm.displayOtherUsersRecommendations();
        assertTrue(tiktokForm.getOtherUsers().getText().contains("Recommendations"));
    }

    @Test
    public void testFetchContent() {
        // Test fetching content
        String content = tiktokForm.fetchContent();
        assertNotNull(content);
    }

    @Test
    public void testDisplayComments() {
        // Test displaying comments
        tiktokForm.displayComments();
        assertTrue(tiktokForm.getContentTextArea().getText().contains("Comments"));
    }
    
}