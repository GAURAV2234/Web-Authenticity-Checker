package app_pro;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class CreateUser extends JFrame implements ActionListener {
    JPanel titleBar;
    JLabel titleLabel, closeLabel;
    JLabel usernameLabel, nameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    JTextField usernameTextField, nameTextField, emailTextField;
    JPasswordField passwordField, confirmPasswordField;
    JButton createButton;
    JToggleButton showPasswordToggle;

    private ImageIcon eyeIcon = new ImageIcon("eye.png");
    private static final String DB_URL = "jdbc:mysql://localhost:3306/appproject";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";


    int posX, posY;

    CreateUser() {
        setUndecorated(true);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#f0f0f0"));

        titleBar = new JPanel();
        titleBar.setBackground(Color.decode("#f0f0f0"));
        titleBar.setBounds(0, 0, 800, 40);
        titleBar.setLayout(null);
        add(titleBar);

        titleLabel = new JLabel("Create New User");
        titleLabel.setFont(new Font("Footlight MT Light", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(10, 5, 300, 20);
        titleBar.add(titleLabel);

        closeLabel = new JLabel("X");
        closeLabel.setFont(new Font("Footlight MT Light", Font.BOLD, 20));
        closeLabel.setForeground(Color.BLACK);
        closeLabel.setBounds(770, 10, 20, 20);
        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        titleBar.add(closeLabel);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBounds(50, 50, 150, 30);
        add(nameLabel);

        nameTextField = new JTextField(20);
        nameTextField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        nameTextField.setBounds(200, 50, 180, 30);
        add(nameTextField);

        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(50, 100, 150, 30);
        add(emailLabel);

        emailTextField = new JTextField(20);
        emailTextField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        emailTextField.setBounds(200, 100, 180, 30);
        add(emailTextField);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(50, 150, 150, 30);
        add(usernameLabel);

        usernameTextField = new JTextField(20);
        usernameTextField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        usernameTextField.setBounds(200, 150, 180, 30);
        add(usernameTextField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(50, 200, 150, 30);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        passwordField.setBounds(200, 200, 140, 30);
        add(passwordField);

        showPasswordToggle = new JToggleButton(eyeIcon);
        showPasswordToggle.setBounds(350, 200, 30, 30);
        showPasswordToggle.addActionListener(this);
        add(showPasswordToggle);

        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        confirmPasswordLabel.setForeground(Color.BLACK);
        confirmPasswordLabel.setBounds(50, 250, 200, 30);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        confirmPasswordField.setBounds(250, 250, 140, 30);
        add(confirmPasswordField);

        createButton = new JButton("Create");
        createButton.setFont(new Font("Arial", Font.BOLD, 20));
        createButton.setBackground(Color.BLACK);
        createButton.setForeground(Color.WHITE);
        createButton.setBounds(160, 300, 100, 40);
        createButton.addActionListener(this);
        add(createButton);

        setVisible(true);

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                int currentX = me.getXOnScreen();
                int currentY = me.getYOnScreen();
                setLocation(currentX - posX, currentY - posY);
            }
        });

        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                posX = me.getX();
                posY = me.getY();
            }
        });
    
        createTableIfNotExists();
}
    private void createTableIfNotExists() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            
            // Check if the "user" table exists
            String tableCheckQuery = "SHOW TABLES LIKE 'user'";
            if (!statement.executeQuery(tableCheckQuery).next()) {
                // Create the "user" table if it doesn't exist
                String createTableQuery = "CREATE TABLE user ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "name VARCHAR(255), "
                        + "email VARCHAR(255), "
                        + "username VARCHAR(255), "
                        + "password VARCHAR(255))";
                statement.executeUpdate(createTableQuery);
            }
            
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void saveUserData(String name, String email, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            
            // Insert user data into the "user" table
            String insertQuery = "INSERT INTO user (name, email, username, password) VALUES ('"
                    + name + "', '" + email + "', '" + username + "', '" + password + "')";
            statement.executeUpdate(insertQuery);
            
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (password.equals(confirmPassword)) {
                // Save user data to the "user" table
                saveUserData(name, email, username, password);
                JOptionPane.showMessageDialog(this, "User created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                CreateUser.this.dispose();
                new Login();
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == showPasswordToggle) {
            if (showPasswordToggle.isSelected()) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
                confirmPasswordField.setEchoChar('•');
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CreateUser();
        });
    }
}

class UserData {
    private static Map<String, String> users = new HashMap<>();

    public static void addUser(String username, String password) {
        users.put(username, password);
    }

    public static boolean isUserValid(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
