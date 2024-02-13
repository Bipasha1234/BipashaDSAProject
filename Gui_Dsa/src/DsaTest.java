import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;

public class DsaTest {
    private static final String DOWNLOAD_DIRECTORY = "C:/Downloads/"; // Define the download directory

    private DsaImageDownload dsa;

    @Before
    public void setUp() {
        dsa = new DsaImageDownload(); // Initialize the DsaImageDownload instance
        dsa.setVisible(true); // Make the frame visible
    }

    @After
    public void tearDown() {
        dsa.dispose(); // Clean up resources after each test
    }

    @Test
    public void testDownloadSingleImage() {
        // Set the URL field to a single image URL
        dsa.getUrlField().setText("https://example.com/image.jpg");
        dsa.getDownloadButton().doClick(); // Simulate clicking the download button

    }

    @Test
    public void testDownloadMultipleImages() {
        // Set the URL field to multiple image URLs separated by spaces
        dsa.getUrlField().setText("https://example.com/image1.jpg https://example.com/image2.jpg");
        dsa.getDownloadButton().doClick(); // Simulate clicking the download button
        // No assertion is made in this test; ensure that images are downloaded correctly
    }

    @Test
    public void testWindowTitle() {
        // Assert that the window title matches the expected title
        assertEquals("Multithreaded Asynchronous Image Downloader", dsa.getTitle());
    }

    @Test
    public void testButtonColors() {
        // Assert that all buttons have the expected background color (green)
        assertEquals(Color.GREEN, dsa.getDownloadButton().getBackground());
        assertEquals(Color.GREEN, dsa.getPauseButton().getBackground());
        assertEquals(Color.GREEN, dsa.getResumeButton().getBackground());
        assertEquals(Color.GREEN, dsa.getCancelButton().getBackground());
    }

    @Test
    public void testPauseAndResumeDownload() {
        // Set the URL field to a single image URL
        dsa.getUrlField().setText("https://example.com/image.jpg");
        dsa.getDownloadButton().doClick(); // Start the download
        dsa.getPauseButton().doClick(); // Pause the download
        assertTrue(dsa.getLogLabel().getText().contains("Download paused")); // Assert that the log label contains the paused message
        dsa.getResumeButton().doClick(); // Resume the download
        assertTrue(dsa.getLogLabel().getText().contains("Download resumed")); // Assert that the log label contains the resumed message
    }

    @Test
    public void testCancelDownload() {
        // Set the URL field to a single image URL
        dsa.getUrlField().setText("https://example.com/image.jpg");
        dsa.getDownloadButton().doClick(); // Start the download
        dsa.getCancelButton().doClick(); // Cancel the download
        assertTrue(dsa.getLogLabel().getText().contains("Download canceled")); // Assert that the log label contains the canceled message

    }

    @Test
    public void testProgressBarUpdates() {
        // Set the URL field to multiple image URLs separated by spaces
        dsa.getUrlField().setText("https://example.com/image1.jpg https://example.com/image2.jpg");
        dsa.getDownloadButton().doClick(); // Simulate clicking the download button

        // Wait for the download to complete (assuming it takes some time)
        try {
            Thread.sleep(3000); // Adjust this time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the progress bar shows completion (value equals the number of images)
        assertEquals(2, dsa.getProgressBar().getValue());
    }

    @Test
    public void testImageSavedInDirectory() {
        String testImageUrl = "https://png.pngtree.com/element_our/png/20181227/jpg-vector-icon-png_287422.jpg"; // Define the test image URL here
        dsa.getUrlField().setText(testImageUrl); // Set the URL field to the test image URL
        dsa.getDownloadButton().doClick(); // Simulate clicking the download button


        // Get the filename from the URL
        String fileName = testImageUrl.substring(testImageUrl.lastIndexOf('/') + 1);
        // Check if the file exists in the directory
        File savedImageFile = new File(DOWNLOAD_DIRECTORY + fileName);
        assertTrue(savedImageFile.exists()); // Assert that the image file exists in the download directory
    }

    @Test
    public void testDownloadCompleted() {
        // Set the URL field to multiple image URLs separated by spaces
        dsa.getUrlField().setText("https://png.pngtree.com/element_our/png/20181227/jpg-vector-icon-png_287422.jpg https://pngimg.com/uploads/free/free_PNG90774.png");
        dsa.getDownloadButton().doClick(); // Simulate clicking the download button

        // Wait for the download to complete (assuming it takes some time)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the log label contains "Download completed" for each image URL
        assertTrue(dsa.getLogLabel().getText().contains("Download completed: https://png.pngtree.com/element_our/png/20181227/jpg-vector-icon-png_287422.jpg"));
        assertTrue(dsa.getLogLabel().getText().contains("Download completed: https://pngimg.com/uploads/free/free_PNG90774.png"));
    }
}
