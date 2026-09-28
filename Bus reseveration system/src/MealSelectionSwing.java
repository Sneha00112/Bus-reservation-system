/*
 * Decompiled with CFR 0.152.
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MealSelectionSwing
extends JPanel {
    private JCheckBox sambarCheckBox;
    private JCheckBox curdRiceCheckBox;
    private JCheckBox pongalCheckBox;
    private JCheckBox idliCheckBox;
    private JCheckBox dosaCheckBox;
    private JButton confirmButton;
    private String route;
    private String travelDate;
    private String travelTime;
    private List selectedSeats;
    private double seatPrice;
    private List passengerNames;
    private String contact;

    public MealSelectionSwing(CardLayout cardLayout, JPanel cardPanel, String route, String travelDate, String travelTime, List selectedSeats, double seatPrice, List passengerNames, String contact) {
        this.route = route;
        this.travelDate = travelDate;
        this.travelTime = travelTime;
        this.selectedSeats = selectedSeats;
        this.seatPrice = seatPrice;
        this.passengerNames = passengerNames;
        this.contact = contact;
        this.setLayout(new BorderLayout());
        JLabel instructionLabel = new JLabel("Please select your desired meals:", 0);
        instructionLabel.setFont(new Font("Arial", 1, 16));
        this.add((Component)instructionLabel, "North");
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.sambarCheckBox = new JCheckBox("Sambar Rice");
        this.curdRiceCheckBox = new JCheckBox("Curd Rice");
        this.pongalCheckBox = new JCheckBox("Pongal");
        this.idliCheckBox = new JCheckBox("Idli (Rice Cake)");
        this.dosaCheckBox = new JCheckBox("Dosa (Rice Crepe)");
        mainPanel.add(this.sambarCheckBox);
        mainPanel.add(this.createImageLabel("C:/Users/vmohu/Bus reseveration system/res/sambar/sambar.jpg"));
        mainPanel.add(this.curdRiceCheckBox);
        mainPanel.add(this.createImageLabel("C:/Users/vmohu/Bus reseveration system/res/curdrice/curdrice.jpg"));
        mainPanel.add(this.pongalCheckBox);
        mainPanel.add(this.createImageLabel("C:/Users/vmohu/Bus reseveration system/res/pongal/pongal.jpg"));
        mainPanel.add(this.idliCheckBox);
        mainPanel.add(this.createImageLabel("C:/Users/vmohu/Bus reseveration system/res/idly/idly.jpg"));
        mainPanel.add(this.dosaCheckBox);
        mainPanel.add(this.createImageLabel("C:/Users/vmohu/Bus reseveration system/res/dosa/dosa.jpg"));
        this.add((Component)mainPanel, "Center");
        this.confirmButton = new JButton("Confirm Selection");
        this.confirmButton.addActionListener(e -> {
            StringBuilder meals = new StringBuilder("Selected Meals: ");
            ArrayList<String> selectedMeals = new ArrayList<String>();
            if (this.sambarCheckBox.isSelected()) {
                selectedMeals.add("Sambar Rice");
            }
            if (this.curdRiceCheckBox.isSelected()) {
                selectedMeals.add("Curd Rice");
            }
            if (this.pongalCheckBox.isSelected()) {
                selectedMeals.add("Pongal");
            }
            if (this.idliCheckBox.isSelected()) {
                selectedMeals.add("Idli");
            }
            if (this.dosaCheckBox.isSelected()) {
                selectedMeals.add("Dosa");
            }
            meals.append(String.join((CharSequence)", ", selectedMeals));
            JOptionPane.showMessageDialog(this, meals.toString(), "Meals Confirmed", 1);
            double totalMealPrice = (double)selectedMeals.size() * 30.0;
            BookingSummarySwing bookingSummary = new BookingSummarySwing(route, travelDate, travelTime, contact, passengerNames, seatPrice, selectedSeats, selectedMeals, totalMealPrice);
            cardPanel.add((Component)bookingSummary.getPanel(), "BookingSummary");
            cardLayout.show(cardPanel, "BookingSummary");
        });
        this.add((Component)this.confirmButton, "South");
    }

    private JLabel createImageLabel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image img = imageIcon.getImage();
        Image resizedImg = img.getScaledInstance(150, 150, 4);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        return new JLabel(resizedIcon);
    }

    public JPanel getPanel() {
        return this;
    }
}
