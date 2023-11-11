//package app_pro;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.sql.*;
//
//
//
//public class Login extends JFrame implements ActionListener {
//    JPanel titleBar;
//    JLabel titleLabel, closeLabel;
//    JLabel label1, label2, label3;
//    JTextField textField1;
//    JPasswordField passwordField1;
//    JButton loginButton, signUpButton;
//    JToggleButton showPasswordToggle; // New toggle button for showing/hiding password
//
//    private ImageIcon eyeIcon = new ImageIcon("eye.png"); // Eye icon representing both states
//
//    private int mouseX, mouseY; // Store initial mouse click coordinates for window movement
//
//    Login() {
//        setLayout(null);
//        setSize(800, 450);
//        setUndecorated(true);
//
//        // Create a custom title bar
//        titleBar = new JPanel();
//        titleBar.setBackground(Color.WHITE);
//        titleBar.setBounds(0, 0, 800, 40);
//        titleBar.setLayout(null);
//        add(titleBar);
//
//        // Custom close button
//        closeLabel = new JLabel("X");
//        closeLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        closeLabel.setForeground(Color.BLACK);
//        closeLabel.setBounds(770, 10, 20, 20);
//        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        closeLabel.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                System.exit(0);
//            }
//        });
//        titleBar.add(closeLabel);
//
//        titleBar.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent e) {
//                mouseX = e.getX();
//                mouseY = e.getY();
//            }
//        });
//
//        titleBar.addMouseMotionListener(new MouseAdapter() {
//            public void mouseDragged(MouseEvent e) {
//                int x = e.getXOnScreen() - mouseX;
//                int y = e.getYOnScreen() - mouseY;
//                setLocation(x, y);
//            }
//        });
//
//
//        label1 = new JLabel("Secure Surfing");
//        label1.setForeground(Color.BLACK);
//        label1.setFont(new Font("Footlight MT Light", Font.BOLD, 40));
//        label1.setBounds(240, 125, 450, 40);
//        add(label1);
//
//        label2 = new JLabel("Username: ");
//        label2.setFont(new Font("Bodoni MT", Font.BOLD, 20));
//        label2.setForeground(Color.BLACK);
//        label2.setBounds(250, 190, 375, 30);
//        add(label2);
//
//        textField1 = new JTextField(15);
//        textField1.setBounds(375, 190, 230, 30);
//        textField1.setFont(new Font("Times New Roman", Font.BOLD, 16));
//        add(textField1);
//
//        label3 = new JLabel("Password: ");
//        label3.setFont(new Font("Bodoni MT", Font.BOLD, 20));
//        label3.setForeground(Color.BLACK);
//        label3.setBounds(250, 250, 375, 30);
//        add(label3);
//
//        passwordField1 = new JPasswordField(15);
//        passwordField1.setBounds(375, 250, 190, 30); // Adjusted the width
//        passwordField1.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        add(passwordField1);
//
//        showPasswordToggle = new JToggleButton(eyeIcon);
//        showPasswordToggle.setBounds(565, 250, 30, 30); // Position it next to the password field
//        showPasswordToggle.addActionListener(this);
//        add(showPasswordToggle);
//
//        loginButton = new JButton("Login");
//        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
//        loginButton.setForeground(Color.WHITE);
//        loginButton.setBackground(Color.BLACK);
//        loginButton.setBounds(250, 310, 100, 30);
//        loginButton.addActionListener(this);
//        add(loginButton);
//
//        signUpButton = new JButton("Sign Up");
//        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
//        signUpButton.setForeground(Color.WHITE);
//        signUpButton.setBackground(Color.BLACK);
//        signUpButton.setBounds(360, 310, 100, 30);
//        signUpButton.addActionListener(this);
//        add(signUpButton);
//
//        setLocationRelativeTo(null); // Open the applet in the middle of the screen
//        setVisible(true);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == loginButton) {
//            // Handle the login logic here
//            String username = textField1.getText();
//            String password = new String(passwordField1.getPassword());
//
//            if (isUserValid(username, password)) {
//                Login.this.dispose(); // Close the login page
//                new WebsiteCheckerSwing(); // Switch to the main page
//                // Successful login
//                JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                // Incorrect credentials, show an error message.
//                JOptionPane.showMessageDialog(this, "Incorrect Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } else if (e.getSource() == signUpButton) {
//            // Handle the sign-up functionality
//            Login.this.dispose(); // Close the login page
//            new CreateUser(); // Switch to the sign-up page
//            // You can create a sign-up form or perform any other action you need.
//        } else if (e.getSource() == showPasswordToggle) {
//            // Toggle the visibility of the password in the password field
//            if (showPasswordToggle.isSelected()) {
//                passwordField1.setEchoChar((char) 0); // Show the password
//            } else {
//                passwordField1.setEchoChar('•'); // Hide the password
//            }
//        }
//    }
//
//    // Add a method to check user credentials in the SQL table
//    private boolean isUserValid(String username, String password) {
//        private static final String DB_URL = "jdbc:mysql://localhost:3306/appproject";
//        private static final String DB_USER = "root";
//        private static final String DB_PASSWORD = "root";
//
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            Statement statement = connection.createStatement();
//            
//            // Query the "user" table for the provided username and password
//            String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'";
//            ResultSet resultSet = statement.executeQuery(query);
//            
//            // Check if any rows are returned (valid user)
//            boolean isValid = resultSet.next();
//            
//            resultSet.close();
//            statement.close();
//            connection.close();
//            
//            return isValid;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }
//
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new Login();
//            }
//        });
//    }
//}


package app_pro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JPanel titleBar;
    JLabel titleLabel, closeLabel;
    JLabel label1, label2, label3;
    JTextField textField1;
    JPasswordField passwordField1;
    JButton loginButton, signUpButton;
    JToggleButton showPasswordToggle; // New toggle button for showing/hiding password

    private ImageIcon eyeIcon = new ImageIcon("eye.png"); // Eye icon representing both states

    private int mouseX, mouseY; // Store initial mouse click coordinates for window movement

    private static final String DB_URL = "jdbc:mysql://localhost:3306/appproject";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    Login() {
        setLayout(null);
        setSize(800, 450);
        setUndecorated(true);

        // Create a custom title bar
        titleBar = new JPanel();
        titleBar.setBackground(Color.WHITE);
        titleBar.setBounds(0, 0, 800, 40);
        titleBar.setLayout(null);
        add(titleBar);

        // Custom close button
        closeLabel = new JLabel("X");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 16));
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

        label1 = new JLabel("Secure Surfing");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("Footlight MT Light", Font.BOLD, 40));
        label1.setBounds(240, 125, 450, 40);
        add(label1);

        label2 = new JLabel("Username: ");
        label2.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        label2.setForeground(Color.BLACK);
        label2.setBounds(250, 190, 375, 30);
        add(label2);

        textField1 = new JTextField(15);
        textField1.setBounds(375, 190, 230, 30);
        textField1.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(textField1);

        label3 = new JLabel("Password: ");
        label3.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        label3.setForeground(Color.BLACK);
        label3.setBounds(250, 250, 375, 30);
        add(label3);

        passwordField1 = new JPasswordField(15);
        passwordField1.setBounds(375, 250, 190, 30); // Adjusted the width
        passwordField1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        add(passwordField1);

        showPasswordToggle = new JToggleButton(eyeIcon);
        showPasswordToggle.setBounds(565, 250, 30, 30); // Position it next to the password field
        showPasswordToggle.addActionListener(this);
        add(showPasswordToggle);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.BLACK);
        loginButton.setBounds(250, 310, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setBounds(360, 310, 100, 30);
        signUpButton.addActionListener(this);
        add(signUpButton);

        setLocationRelativeTo(null); // Open the applet in the middle of the screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle the login logic here
            String username = textField1.getText();
            String password = new String(passwordField1.getPassword());

            if (isUserValid(username, password)) {
                Login.this.dispose(); // Close the login page
                new WebsiteCheckerSwing(); // Switch to the main page
                // Successful login
                JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Incorrect credentials, show an error message.
                JOptionPane.showMessageDialog(this, "Incorrect Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == signUpButton) {
            // Handle the sign-up functionality
            Login.this.dispose(); // Close the login page
            new CreateUser(); // Switch to the sign-up page
            // You can create a sign-up form or perform any other action you need.
        } else if (e.getSource() == showPasswordToggle) {
            // Toggle the visibility of the password in the password field
            if (showPasswordToggle.isSelected()) {
                passwordField1.setEchoChar((char) 0); // Show the password
            } else {
                passwordField1.setEchoChar('•'); // Hide the password
            }
        }
    }

    // Add a method to check user credentials in the SQL table
    private boolean isUserValid(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            
            // Query the "user" table for the provided username and password
            String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);
            
            // Check if any rows are returned (valid user)
            boolean isValid = resultSet.next();
            
            resultSet.close();
            statement.close();
            connection.close();
            
            return isValid;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}
