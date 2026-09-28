/*
 * Decompiled with CFR 0.152.
 */
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class TravelBooking {
    private JPanel panel;
    private JComboBox routeComboBox;
    private JComboBox timeComboBox;
    private JComboBox typeComboBox;
    private JTextField dateField;
    private JTextField passengerCountField;
    private JTextField contactField;
    private JButton bookButton;
    private ImageIcon backgroundImage = new ImageIcon(this.getClass().getResource("/bus/BUS.jpg"));

    public TravelBooking(CardLayout cardLayout, JPanel cardPanel) {
        this.panel = new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(TravelBooking.this.backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        this.panel.setLayout(new GridLayout(10, 2, 10, 10));
        this.panel.add(new JLabel("Travel Date (YYYY-MM-DD):"));
        this.dateField = new JTextField();
        this.panel.add(this.dateField);
        this.panel.add(new JLabel("Route:"));
        this.routeComboBox = new JComboBox<String>(new String[]{"Chennai to Trichy", "Chennai to Bangalore", "Chennai to Kerala", "Chennai to Hyderabad"});
        this.panel.add(this.routeComboBox);
        this.panel.add(new JLabel("Time:"));
        this.timeComboBox = new JComboBox<String>(new String[]{"08:00:00", "12:00:00", "16:00:00", "20:00:00"});
        this.panel.add(this.timeComboBox);
        this.panel.add(new JLabel("Bus Type:"));
        this.typeComboBox = new JComboBox<String>(new String[]{"AC", "Non-AC", "Semi Sleeper", "Sleeper"});
        this.panel.add(this.typeComboBox);
        this.panel.add(new JLabel("Number of Passengers:"));
        this.passengerCountField = new JTextField("1");
        this.panel.add(this.passengerCountField);
        this.panel.add(new JLabel("Contact:"));
        this.contactField = new JTextField();
        this.panel.add(this.contactField);
        this.bookButton = new JButton("Enter Name");
        this.panel.add(this.bookButton);
        this.bookButton.addActionListener(e -> this.handleBooking(cardLayout, cardPanel));
    }

    private void handleBooking(CardLayout cardLayout, JPanel cardPanel) {
        int passengerCount;
        String route = (String)this.routeComboBox.getSelectedItem();
        String travelDate = this.dateField.getText().trim();
        String travelTime = (String)this.timeComboBox.getSelectedItem();
        String contact = this.contactField.getText().trim();
        try {
            passengerCount = Integer.parseInt(this.passengerCountField.getText());
            if (passengerCount <= 0) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.panel, "Please enter a valid number of passengers.", "Input Error", 0);
            return;
        }
        ArrayList<String> passengerNames = new ArrayList<String>();
        for (int i = 0; i < passengerCount; ++i) {
            String name = JOptionPane.showInputDialog(this.panel, (Object)("Enter name for Passenger " + (i + 1) + ":"));
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this.panel, "Please enter a valid name for Passenger " + (i + 1), "Input Error", 0);
                return;
            }
            passengerNames.add(name.trim());
        }
        MultiSeatBookingSwing multiSeatBookingSwing = new MultiSeatBookingSwing(cardLayout, cardPanel, route, travelDate, travelTime, contact, passengerNames);
        cardPanel.add((Component)multiSeatBookingSwing.getPanel(), "MultiSeatBooking");
        cardLayout.show(cardPanel, "MultiSeatBooking");
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
