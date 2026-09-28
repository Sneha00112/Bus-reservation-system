/*
 * Decompiled with CFR 0.152.
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MultiSeatBookingSwing {
    private int rows = 5;
    private int columns = 5;
    private JPanel mainPanel;
    private JPanel seatPanel;
    private JButton bookButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private List<String> selectedSeats = new ArrayList<String>();
    private String route;
    private String travelDate;
    private String travelTime;
    private String contact;
    private List<String> passengerNames;

    public MultiSeatBookingSwing(CardLayout cardLayout, JPanel cardPanel, String route, String travelDate, String travelTime, String contact, List<String> passengerNames) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.route = route;
        this.travelDate = travelDate;
        this.travelTime = travelTime;
        this.contact = contact;
        this.passengerNames = passengerNames;
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("Select your Seat", 0);
        headerLabel.setFont(new Font("Arial", 1, 18));
        headerLabel.setForeground(Color.BLUE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(173, 216, 230));
        headerLabel.setPreferredSize(new Dimension(0, 50));
        this.mainPanel.add((Component)headerLabel, "North");
        this.seatPanel = new JPanel(new GridLayout(this.rows, this.columns, 15, 15));
        this.seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.initializeSeatLayout();
        this.mainPanel.add((Component)this.seatPanel, "Center");
        this.createBottomPanel();
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.WHITE);
        JPanel legendPanel = new JPanel(new FlowLayout(1, 15, 5));
        legendPanel.setBackground(Color.WHITE);
        JLabel availableLabel = new JLabel("Available", 0);
        availableLabel.setForeground(Color.BLACK);
        availableLabel.setOpaque(true);
        availableLabel.setBackground(Color.GREEN);
        JLabel bookedLabel = new JLabel("Booked", 0);
        bookedLabel.setForeground(Color.WHITE);
        bookedLabel.setOpaque(true);
        bookedLabel.setBackground(Color.RED);
        JLabel selectedLabel = new JLabel("Selected", 0);
        selectedLabel.setForeground(Color.BLACK);
        selectedLabel.setOpaque(true);
        selectedLabel.setBackground(Color.YELLOW);
        legendPanel.add(availableLabel);
        legendPanel.add(bookedLabel);
        legendPanel.add(selectedLabel);
        this.bookButton = new JButton("Done");
        this.bookButton.setBackground(new Color(135, 206, 250));
        this.bookButton.setFont(new Font("Arial", 1, 14));
        this.bookButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        this.bookButton.addActionListener(e -> this.confirmBooking());
        bottomPanel.add((Component)legendPanel, "North");
        bottomPanel.add((Component)this.bookButton, "South");
        this.mainPanel.add((Component)bottomPanel, "South");
    }

    private void initializeSeatLayout() {
        int seatNumber = 1;
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.columns; ++j) {
                JButton seatButton;
                if (this.isSeatBooked((seatButton = new JButton(String.valueOf(seatNumber++))).getText())) {
                    seatButton.setBackground(Color.RED);
                    seatButton.setEnabled(false);
                } else {
                    seatButton.setBackground(Color.GREEN);
                    seatButton.addActionListener(e -> this.toggleSeatSelection(seatButton));
                }
                this.seatPanel.add(seatButton);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isSeatBooked(String seatNumber) {
        try (Connection conn = DBConnection.getConnection();){
            String sql = "SELECT COUNT(*) FROM seat_bookings WHERE route = ? AND travel_date = ? AND time = ? AND seat_number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, this.route);
                stmt.setString(2, this.travelDate);
                stmt.setString(3, this.travelTime);
                stmt.setInt(4, Integer.parseInt(seatNumber));
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) return false;
                boolean bl = rs.getInt(1) > 0;
                return bl;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void toggleSeatSelection(JButton seatButton) {
        if (seatButton.getBackground().equals(Color.GREEN)) {
            seatButton.setBackground(Color.YELLOW);
            this.selectedSeats.add(seatButton.getText());
        } else if (seatButton.getBackground().equals(Color.YELLOW)) {
            seatButton.setBackground(Color.GREEN);
            this.selectedSeats.remove(seatButton.getText());
        }
    }

    private void confirmBooking() {
        for (String seat : this.selectedSeats) {
            this.storeBookedSeat(seat);
        }
        MealSelectionSwing mealSelection = new MealSelectionSwing(this.cardLayout, this.cardPanel, this.route, this.travelDate, this.travelTime, this.selectedSeats, this.getTotalPrice(), this.passengerNames, this.contact);
        this.cardPanel.add((Component)mealSelection.getPanel(), "MealSelection");
        this.cardLayout.show(this.cardPanel, "MealSelection");
    }

    private double getTotalPrice() {
        return (double)this.selectedSeats.size() * 100.0;
    }

    private void storeBookedSeat(String seatNumber) {
        try (Connection conn = DBConnection.getConnection();){
            String sql = "INSERT INTO seat_bookings (route, travel_date, time, seat_number, passenger_names, contact) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, this.route);
                stmt.setString(2, this.travelDate);
                stmt.setString(3, this.travelTime);
                stmt.setInt(4, Integer.parseInt(seatNumber));
                stmt.setString(5, String.join((CharSequence)", ", this.passengerNames));
                stmt.setString(6, this.contact);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return this.mainPanel;
    }
}
