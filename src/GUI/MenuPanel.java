package GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(String username) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TOP PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(30, 144, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel title = new JLabel("Quizkampen");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 32));

        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        //  MIDDLE PANEL
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel welcomeLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        middlePanel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        cardWrapper.setBackground(Color.WHITE);
        cardWrapper.add(createCard("Klassiskt spel"));


        middlePanel.add(cardWrapper, BorderLayout.CENTER);
        add(middlePanel, BorderLayout.CENTER);

        //  BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton startGameButton = new JButton("Starta nytt spel");
        startGameButton.setBackground(new Color(34, 139, 34));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        startGameButton.setFocusPainted(false);
        startGameButton.setPreferredSize(new Dimension(200, 50));
        startGameButton.addActionListener(e -> showMode());

        bottomPanel.add(startGameButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 300));
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));

        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private void showMode() {
        JFrame modeFrame = new JFrame("Klassiskt läge");
        modeFrame.setSize(350, 200);
        modeFrame.setLocationRelativeTo(null);
        modeFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton randomPlayer = new JButton("Slumpad spelare");
        JButton playFriend = new JButton("Spela mot en vän");

        modeFrame.add(randomPlayer);
        modeFrame.add(playFriend);

        modeFrame.setVisible(true);
    }

    //test
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quizkampen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(new MenuPanel("Player1"));
        frame.setVisible(true);
    }
}

