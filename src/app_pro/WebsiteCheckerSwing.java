package app_pro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class WebsiteCheckerSwing extends JFrame implements ActionListener {
    private JLabel titleLabel, resultLabel;
    private JTextField urlTextField;
    private JButton checkButton;
    private JPanel titleBar;
    private JLabel closeLabel;
    private int mouseX, mouseY;

    private Connection connection;

    WebsiteCheckerSwing() {
    	
    	 try {
             // Establish a database connection
             connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/appproject", "root", "root"
             );

             // Create the table if it doesn't exist
             createWebsiteStatusTable();
         } catch (Exception e) {
             System.out.println(e);
         }
    	
        // Remove the title bar
        setUndecorated(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#f0f0f0"));
        

        // Create a custom title bar
        titleBar = new JPanel();
        titleBar.setBackground(Color.WHITE);
        titleBar.setBounds(0, 0, 800, 40);
        titleBar.setLayout(null);
        add(titleBar);

        // Custom close button
        closeLabel = new JLabel("X");
        closeLabel.setFont(new Font("Footlight MT Light", Font.BOLD, 16));
        closeLabel.setForeground(Color.BLACK);
        closeLabel.setBounds(770, 10, 20, 20);
        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        titleBar.add(closeLabel);

        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                setLocation(x, y);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(60, 20, 20, 20));
        topPanel.setLayout(new BorderLayout());

        titleLabel = new JLabel("Check Website Status");
        titleLabel.setFont(new Font("Footlight MT Light", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        urlTextField = new JTextField(40);
        urlTextField.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(urlTextField, BorderLayout.CENTER);

        checkButton = new JButton("Check");
        checkButton.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        checkButton.setBackground(Color.decode("#007ACC"));
        checkButton.setForeground(Color.WHITE);
        topPanel.add(checkButton, BorderLayout.SOUTH);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Colonna MT", Font.BOLD, 44));
        resultPanel.add(resultLabel);

        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        checkButton.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkButton) {
            String url = urlTextField.getText();

            // Check if the website is up
            boolean isUp = isWebsiteUp(url);

            if (isUp) {
                resultLabel.setText("Website is up!");
                resultLabel.setForeground(Color.decode("#008000"));
            } else {
                resultLabel.setText("Website is down!");
                resultLabel.setForeground(Color.decode("#FF0000"));
            }

            // Save the website status in the database
            saveWebsiteStatus(url, isUp);
        }
    }
    
    private void createWebsiteStatusTable() throws SQLException {
        Statement stmt = connection.createStatement();

        // Check if the table already exists
        ResultSet resultSet = stmt.executeQuery("SHOW TABLES LIKE 'WebsiteStatus'");
        if (!resultSet.next()) {
            // Create the table if it doesn't exist
            stmt.executeUpdate("CREATE TABLE WebsiteStatus (SrNo INT, WEBSITE VARCHAR(255), ENTRY_TIME TIMESTAMP)");
        }
    }
    private void saveWebsiteStatus(String url, boolean isUp) {
        try {
            // Check if the website already exists in the database
            PreparedStatement checkStatement = connection.prepareStatement(
                "SELECT SrNo FROM WebsiteStatus WHERE WEBSITE = ?"
            );
            checkStatement.setString(1, url);

            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // The website already exists, so get the SrNo
                int existingSrNo = resultSet.getInt("SrNo");

                // Delete the existing record
                PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM WebsiteStatus WHERE SrNo = ?"
                );
                deleteStatement.setInt(1, existingSrNo);
                deleteStatement.executeUpdate();
            }

            // Find the maximum SrNo in the WebsiteStatus table
            PreparedStatement maxSrNoStatement = connection.prepareStatement(
                "SELECT MAX(SrNo) AS MaxSrNo FROM WebsiteStatus"
            );

            ResultSet maxSrNoResult = maxSrNoStatement.executeQuery();
            int maxSrNo = 0;

            if (maxSrNoResult.next()) {
                maxSrNo = maxSrNoResult.getInt("MaxSrNo");
            }

            // Insert a new record with the next available serial number
            PreparedStatement insertStatement = connection.prepareStatement(
                "INSERT INTO WebsiteStatus (SrNo, WEBSITE, ENTRY_TIME) VALUES (?, ?, ?)"
            );

            // Set the values for the statement
            insertStatement.setInt(1, maxSrNo + 1);
            insertStatement.setString(2, url);
            insertStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            // Execute the insert statement
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private boolean isWebsiteUp(String url) {
        try {
            URI uri = new URI(url);
            if (!uri.isAbsolute()) {
                uri = new URI("https://" + url);
            }

            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (URISyntaxException | IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/appproject?serverTimezone=UTC", "root", "root"
            );
            Statement stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }

        SwingUtilities.invokeLater(() -> new WebsiteCheckerSwing());
    }
}