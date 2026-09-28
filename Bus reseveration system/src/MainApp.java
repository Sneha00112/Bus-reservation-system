/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  MainApp$1
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bus Reservation System");
            frame.setDefaultCloseOperation(3);
            frame.setSize(800, 600);
            CardLayout cardLayout = new CardLayout();
            JPanel cardPanel = new JPanel(cardLayout);
            JPanel mainPage = MainApp.createMainPage(cardLayout, cardPanel);
            cardPanel.add((Component)mainPage, "MainPage");
            RegistrationLoginApp registrationLoginApp = new RegistrationLoginApp(cardLayout, cardPanel);
            cardPanel.add((Component)registrationLoginApp.getPanel(), "RegistrationLoginApp");
            TravelBooking travelBooking = new TravelBooking(cardLayout, cardPanel);
            cardPanel.add((Component)travelBooking.getPanel(), "TravelBooking");
            frame.getContentPane().add(cardPanel);
            frame.setVisible(true);
        });
    }

    private static JPanel createMainPage(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = 10;
        JLabel welcomeLabel = new JLabel("Welcome to the Royal Bus Travels", 0);
        welcomeLabel.setFont(new Font("Arial", 1, 40));
        welcomeLabel.setForeground(Color.white);
        panel.add((Component)welcomeLabel, gbc);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton bookTicketButton = new JButton("Book Ticket");
        buttonPanel.add(bookTicketButton);
        gbc.gridy = 1;
        panel.add((Component)buttonPanel, gbc);
        bookTicketButton.addActionListener(e -> cardLayout.show(cardPanel, "RegistrationLoginApp"));
        return panel;
    }
}
