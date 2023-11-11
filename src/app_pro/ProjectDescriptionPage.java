package app_pro;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectDescriptionPage extends JFrame implements ActionListener {
    private JButton nextPageButton;

    public ProjectDescriptionPage() {
        setTitle("Project Description");
        setSize(800, 450);

        // Create a JTextArea for the description paragraph
        JTextArea descriptionTextArea = new JTextArea("The \"Website Authenticity Checker\" project provides simple and effective solutions for checking the reliability and authenticity of websites. It comes in both Python and Java versions. The Java version offers a user-friendly interface and integrates with a MySQL database for reliable data storage, while the Python version focuses on real-time website status checks. Both versions prioritize user experience and data integrity, making them valuable tools for web administrators and developers to ensure website reliability.");
        
        // Set font style and size
        Font font = new Font("Bodoni MT", Font.PLAIN, 20);
        descriptionTextArea.setFont(font);

        // Enable word wrap to display the text in a paragraph format
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        // Set the text area as uneditable
        descriptionTextArea.setEditable(false);

        // Add the description text area to the frame
        add(new JScrollPane(descriptionTextArea));

        // Add a button to navigate to the login page
        nextPageButton = new JButton("Next");
        nextPageButton.addActionListener(this);
        add(nextPageButton);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextPageButton) {
            // Switch to the login page
            dispose(); // Close the current page
            new Login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProjectDescriptionPage().setVisible(true);
        });
    }
}
