package GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(String username) {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        JFrame frame = new JFrame("Quizkampen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout());

//        Topp Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLUE); /* 30,144,255? */
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel title = new JLabel("Quizkampen");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 32));

        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

//        Mitt panel
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.white);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel welcomeLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        middlePanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardWrapper.add(skapaKort("Klassiskt spel"));
        cardWrapper.setBackground(Color.white);

        middlePanel.add(cardWrapper, BorderLayout.CENTER);
        add(middlePanel, BorderLayout.CENTER);


//        middlePanel.setBackground(Color.BLUE);


//        Starta nytt spel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.white);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton startGameButton = new JButton("Starta nytt spel");
        startGameButton.setBackground(Color.GREEN); /*34,139,34*/
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        startGameButton.setPreferredSize(new Dimension(150, 50));

        startGameButton.addActionListener(e -> showMode());

        bottomPanel.add(startGameButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private JPanel skapaKort(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 300));
        card.setBackground(Color.GRAY);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));


        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));

        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private void showMode() {
        JFrame modeFrame = new JFrame("Klassiskt spel");
        modeFrame.setSize(300, 200);
        modeFrame.setLayout(new FlowLayout());

        JButton randomPlayer = new JButton("Slumpad spelare");
        JButton playFriend = new JButton("Spela mot en vän");

//        SETSIZE SENARE??

        modeFrame.add(randomPlayer);
        modeFrame.add(playFriend);

        modeFrame.setVisible(true);


    }


    public static void main(String[] args) {
        new MenuPanel("Player1");
    }


}
