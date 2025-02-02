import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    int person1 = 0;
    int person2 = 0;
    private static final String FILE_PATH = "point_data.txt";

    public static void main(String[] args) {
        Main mainInstance = new Main(); // Create an instance of Main
        mainInstance.loadPointData(); // Load point data from file

        JFrame frame = new JFrame("Point Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("background.png");
        backgroundPanel.setLayout(new GridBagLayout()); // For centering

        // ASCII Art Text
        String asciiText = "\n" +
                "  ___                      _            ___                      ___ \n" +
                " | _ \\___ _ _ ___ ___ _ _ / | __ _____ | _ \\___ _ _ ___ ___ _ _ |_  )\n" +
                " |  _/ -_) '_(_-</ _ \\ ' \\| | \\ V (_-< |  _/ -_) '_(_-</ _ \\ ' \\ / / \n" +
                " |_| \\___|_| /__/\\___/_||_|_|  \\_//__/ |_| \\___|_| /__/\\___/_||_/___|\n" +
                "                                                                     \n";

        JTextArea asciiLabel = new JTextArea(asciiText);
        asciiLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        asciiLabel.setEditable(false);
        asciiLabel.setOpaque(false);
        asciiLabel.setLineWrap(false);

        JPanel asciiPanel = new JPanel();
        asciiPanel.setOpaque(false);
        asciiPanel.add(asciiLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(asciiPanel, gbc);

        // Button Panel (Bottom)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton button1 = new JButton("Check Count");
        button1.setPreferredSize(new Dimension(200, 50));
        button1.addActionListener(e -> mainInstance.currentCount());

        JButton button2 = new JButton("Add");
        button2.setPreferredSize(new Dimension(200, 50));
        button2.addActionListener(e -> mainInstance.addPointToPerson());

        JButton button3 = new JButton("Remove");
        button3.setPreferredSize(new Dimension(200, 50));
        button3.addActionListener(e -> mainInstance.removePointFromPerson());

        JButton button4 = new JButton("Exit");
        button4.setPreferredSize(new Dimension(200, 50));
        button4.addActionListener(e -> {
            mainInstance.savePointData(); // Save point data before exiting
            System.exit(0);
        });

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        frame.add(backgroundPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Background Panel
    static class BackgroundPanel extends JPanel {
        public BackgroundPanel(String imagePath) {
            setBackground(Color.WHITE);
            setLayout(new GridBagLayout());
        }
    }

    public void currentCount() {
        JOptionPane.showMessageDialog(null, "Person1's Points: " + person1 + "\nPerson2's Points: " + person2, "Current Point Count", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addPointToPerson() {
        String personInput = JOptionPane.showInputDialog("Who would you like to add points to? (Person1/Person2)");
        if (personInput != null) {
            if (personInput.equalsIgnoreCase("Person1")) {
                String pointInput = JOptionPane.showInputDialog("Enter the amount of points to add to Person1:");
                if (pointInput != null) {
                    int pointsToAdd = Integer.parseInt(pointInput);
                    person1 += pointsToAdd;
                    currentCount();
                }
            } else if (personInput.equalsIgnoreCase("Person2")) {
                String pointInput = JOptionPane.showInputDialog("Enter the amount of points to add to Person2:");
                if (pointInput != null) {
                    int pointsToAdd = Integer.parseInt(pointInput);
                    person2 += pointsToAdd;
                    currentCount();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid person. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void removePointFromPerson() {
        String personInput = JOptionPane.showInputDialog("Who would you like to remove points from? (Person1/Person2)");
        if (personInput != null) {
            if (personInput.equalsIgnoreCase("Person1")) {
                String pointInput = JOptionPane.showInputDialog("Enter the amount of points to remove from Person1:");
                if (pointInput != null) {
                    int pointsToRemove = Integer.parseInt(pointInput);
                    person1 -= pointsToRemove;
                    currentCount();
                }
            } else if (personInput.equalsIgnoreCase("Person2")) {
                String pointInput = JOptionPane.showInputDialog("Enter the amount of points to remove from Person2:");
                if (pointInput != null) {
                    int pointsToRemove = Integer.parseInt(pointInput);
                    person2 -= pointsToRemove;
                    currentCount();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid person. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void savePointData() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(person1 + "\n" + person2);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while saving point data.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadPointData() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                if (fileScanner.hasNextInt()) {
                    person1 = fileScanner.nextInt();
                }
                if (fileScanner.hasNextInt()) {
                    person2 = fileScanner.nextInt();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "An error occurred while loading point data.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}