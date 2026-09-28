/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class BookingSummarySwing {
    private JPanel summaryPanel;

    public BookingSummarySwing(String route, String travelDate, String travelTime, String contact, List<String> passengerNames, double totalPrice, List<String> selectedSeats, List<String> meals, double totalMealPrice) {
        ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\vmohu\\Bus reseveration system\\res\\summarybus\\summarybuses.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());
        this.summaryPanel = new JPanel();
        this.summaryPanel.setOpaque(false);
        this.summaryPanel.setLayout(new BoxLayout(this.summaryPanel, 1));
        this.summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel summaryLabel = new JLabel("Booking Summary", 0);
        summaryLabel.setFont(new Font("Arial", 1, 36));
        summaryLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(summaryLabel);
        JLabel routeLabel = new JLabel("Route: " + route);
        routeLabel.setFont(new Font("Arial", 0, 24));
        routeLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(routeLabel);
        JLabel dateLabel = new JLabel("Travel Date: " + travelDate);
        dateLabel.setFont(new Font("Arial", 0, 24));
        dateLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(dateLabel);
        JLabel timeLabel = new JLabel("Travel Time: " + travelTime);
        timeLabel.setFont(new Font("Arial", 0, 24));
        timeLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(timeLabel);
        JLabel contactLabel = new JLabel("Contact: " + contact);
        contactLabel.setFont(new Font("Arial", 0, 24));
        contactLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(contactLabel);
        JLabel passengerCountLabel = new JLabel("Number of Passengers: " + passengerNames.size());
        passengerCountLabel.setFont(new Font("Arial", 0, 24));
        passengerCountLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(passengerCountLabel);
        JLabel passengerNamesLabel = new JLabel("Passenger Names: " + String.join((CharSequence)", ", passengerNames));
        passengerNamesLabel.setFont(new Font("Arial", 0, 24));
        passengerNamesLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(passengerNamesLabel);
        JLabel seatsLabel = new JLabel("Booked Seats: " + String.join((CharSequence)", ", selectedSeats));
        seatsLabel.setFont(new Font("Arial", 0, 24));
        seatsLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(seatsLabel);
        JLabel mealsLabel = new JLabel("Meals Selected: " + String.join((CharSequence)", ", meals));
        mealsLabel.setFont(new Font("Arial", 0, 24));
        mealsLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(mealsLabel);
        double totalWithMeals = totalPrice + totalMealPrice;
        JLabel totalPriceLabel = new JLabel("Total Price (without meals): Rs" + totalPrice);
        totalPriceLabel.setFont(new Font("Arial", 0, 24));
        totalPriceLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(totalPriceLabel);
        JLabel totalWithMealsLabel = new JLabel("Total Price (with meals): Rs" + totalWithMeals);
        totalWithMealsLabel.setFont(new Font("Arial", 0, 24));
        totalWithMealsLabel.setForeground(Color.BLACK);
        this.summaryPanel.add(totalWithMealsLabel);
        JButton finishButton = new JButton("Exit");
        finishButton.setFont(new Font("Arial", 1, 24));
        finishButton.addActionListener(e -> JOptionPane.showMessageDialog(this.summaryPanel, "Thank you for your booking!"));
        this.summaryPanel.add(finishButton);
        backgroundLabel.add(this.summaryPanel);
        this.summaryPanel.setAlignmentX(0.5f);
    }

    public JPanel getPanel() {
        return this.summaryPanel;
    }
}
