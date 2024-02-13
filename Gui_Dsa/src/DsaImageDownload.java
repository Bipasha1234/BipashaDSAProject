import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class DsaImageDownload extends JFrame {
//download_directory is the directory where download images will be saved.
    private static final String DOWNLOAD_DIRECTORY = "C:/Downloads/";

    // GUI components
    private JButton downloadButton, pauseButton, resumeButton, cancelButton;
    private JTextField urlField;
    private JLabel logLabel;
    private JProgressBar progressBar;
    private JLabel imageLabel;

    // Executor service for managing image download tasks
    private ExecutorService executorService;
    private Future<?> downloadTask;
    private Semaphore semaphore;

    // Constructor for the main frame
    public DsaImageDownload() {
        setTitle("Multithreaded Asynchronous Image Downloader");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Initialize GUI components
        urlField = new JTextField(60);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        cancelButton = new JButton("Cancel");
        logLabel = new JLabel("<html>");
        logLabel.setVerticalAlignment(JLabel.TOP);

        progressBar = new JProgressBar();
        imageLabel = new JLabel();

        // Set backgrounds and sizes for components
        downloadButton.setBackground(Color.GREEN);
        pauseButton.setBackground(Color.GREEN);
        resumeButton.setBackground(Color.GREEN);
        cancelButton.setBackground(Color.GREEN);
        progressBar.setBackground(Color.darkGray);

        // Change the height of the progress bar
        Dimension progressBarSize = new Dimension(progressBar.getPreferredSize().width, 80);
        progressBar.setPreferredSize(progressBarSize);
        urlField.setPreferredSize(new Dimension(urlField.getPreferredSize().width, 50));

        // Set layout for the frame
        setLayout(new BorderLayout());

        // Add components to the frame
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(new JLabel("Enter URLs (separated by spaces):"));
        inputPanel.add(urlField);
        inputPanel.add(downloadButton);
        inputPanel.add(pauseButton);
        inputPanel.add(resumeButton);
        inputPanel.add(cancelButton);
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(logLabel), BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
        add(imageLabel, BorderLayout.WEST);


    // Initialize ExecutorService and Semaphore
        executorService = Executors.newFixedThreadPool(5);
        semaphore = new Semaphore(1);

        // Add action listeners for the buttons.
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] urls = urlField.getText().split("\\s+");
                progressBar.setMaximum(urls.length);
                progressBar.setValue(0);
                progressBar.setStringPainted(true);
                for (String url : urls) {
                    downloadImage(url);
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownload();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownload();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownload();
            }
        });
    }
    // Getters for GUI components
    public JTextField getUrlField() {
        return urlField;
    }
    public JLabel getLogLabel() {
        return logLabel;
    }
    public JProgressBar getProgressBar() {
        return progressBar;
    }



    // Method to download an image from a given URL
    private void downloadImage(String urlString) {
        downloadTask = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    URL url = new URL(urlString);
                    log("Downloading image from URL: " + url);

                    // Get filename from URL
                    String fileName = url.getFile();
                    int lastSlashIndex = fileName.lastIndexOf('/');
                    if (lastSlashIndex != -1) {
                        fileName = fileName.substring(lastSlashIndex + 1);
                    }
                    // Extract the file extension
                    String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

                    // Check if the file extension corresponds to a valid image format
                    if (!fileExtension.matches("(?i)(jpg|jpeg|png|gif|bmp)")) {
                        throw new MalformedURLException("Unsupported image format: " + fileExtension);
                    }

                    // Save the image to the specified directory
                    File outputImageFile = new File(DOWNLOAD_DIRECTORY + fileName);
                    ImageIO.write(ImageIO.read(url), fileExtension, outputImageFile);

                    log("Image saved to: " + outputImageFile.getAbsolutePath());

                    // Check if the file extension corresponds to a valid image format
                    if (!fileExtension.matches("(?i)(jpg|jpeg|png|gif|bmp)")) {
                        throw new MalformedURLException("Unsupported image format: " + fileExtension);
                    }

                    // Replace this with your actual image downloading logic
                    ImageIcon imageIcon = new ImageIcon(url);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(scaledImage);

                    // Load and display the downloaded image
                    imageLabel.setIcon(imageIcon);
                    log("Download completed: " + url);

                    // Increment progress bar value only upon successful download
                    progressBar.setValue(progressBar.getValue() + 1);

                    // Calculate the progress percentage based on the current number of completed downloads
                    int progressPercentage = (progressBar.getValue()) * 100 / progressBar.getMaximum();
                    progressBar.setString(progressPercentage + "%");

                } catch (MalformedURLException e) {
                    log("Unsupported URL: " + urlString);
                    // Set progress bar to 0% when encountering an unsupported URL
                    progressBar.setValue(0);
                } catch (InterruptedException e) {
                    log("Download interrupted: " + urlString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }
        });
    }


    // Method to pause the image download
    private void pauseDownload() {
        if (downloadTask != null && !downloadTask.isDone() && !downloadTask.isCancelled()) {
            downloadTask.cancel(true);
            log("Download paused.");
        }
    }

    // Method to resume a paused download
    private void resumeDownload() {
        if (downloadTask != null && downloadTask.isCancelled()) {
            String[] urls = urlField.getText().split("\\s+");
            progressBar.setMaximum(urls.length);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            for (String url : urls) {
                downloadImage(url);
            }
            log("Download resumed.");
        }
    }
    // Method to cancel the image download
    private void cancelDownload() {
        if (downloadTask != null && !downloadTask.isDone()) {
            downloadTask.cancel(true);
            progressBar.setValue(0);
            log("Download canceled.");
        }
    }


    // Method to log messages to the label
    private void log(String message) {
        logLabel.setText(logLabel.getText() + message + "<br>");
        progressBar.setValue(progressBar.getValue() + 1);
    }

    //getter methods for buttons.
    public JButton getDownloadButton() {
        return downloadButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getResumeButton() {
        return resumeButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DsaImageDownload().setVisible(true);
            }
        });
    }
}