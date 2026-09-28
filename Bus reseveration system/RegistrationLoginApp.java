/*
 * Decompiled with CFR 0.152.
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class RegistrationLoginApp {
    private JPanel mainPanel;
    private ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/login/loginicon.jpeg")));
    private JButton registerButton;
    private JButton loginButton;

    public RegistrationLoginApp(CardLayout cardLayout, JPanel cardPanel) {
        if (this.logoIcon.getImageLoadStatus() == 4) {
            System.out.println("Image not loaded. Check the file path.");
        }
        this.mainPanel = this.createMainPanel(cardLayout, cardPanel);
        cardPanel.add((Component)this.mainPanel, "RegistrationLoginApp");
    }

    private JPanel createMainPanel(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 220, 240));
        JLabel logoLabel = new JLabel(this.logoIcon);
        logoLabel.setHorizontalAlignment(0);
        panel.add((Component)logoLabel, "North");
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, 1));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        inputPanel.add(new JLabel("Username:", 4));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:", 4));
        inputPanel.add(passwordField);
        formPanel.add(inputPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        this.registerButton = new JButton("Register if not registered");
        this.loginButton = new JButton("Login");
        buttonPanel.add(this.registerButton);
        buttonPanel.add(this.loginButton);
        formPanel.add(buttonPanel);
        panel.add((Component)formPanel, "Center");
        this.registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Username and password cannot be empty!", "Input Error", 0);
            } else {
                this.registerUser(username, password);
                JOptionPane.showMessageDialog(panel, "Registration successful! Please log in.");
            }
        });
        this.loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Username and password cannot be empty!", "Input Error", 0);
            } else {
                boolean loggedIn = this.loginUser(username, password);
                if (loggedIn) {
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username + "!");
                    cardLayout.show(cardPanel, "TravelBooking");
                } else {
                    JOptionPane.showMessageDialog(panel, "Login failed! Check your username and password.", "Login Error", 0);
                }
            }
        });
        return panel;
    }

    private void registerUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection();){
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, username);
                stmt.setString(2, password);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "User registered successfully!");
                }
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Registration failed: " + e.getMessage(), "Registration Error", 0);
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private boolean loginUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection();){
            boolean bl;
            block14: {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                try {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    bl = rs.next();
                    if (stmt == null) break block14;
                }
                catch (Throwable throwable) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                stmt.close();
            }
            return bl;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage(), "Login Error", 0);
            return false;
        }
    }

    public JPanel getPanel() {
        return this.mainPanel;
    }
}
