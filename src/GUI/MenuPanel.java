package GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPanel {

    public MenuPanel(String username) {

        JFrame frame = new JFrame("Quizkampen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout());

//        Topp Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLUE);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("Quizkampen");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        topPanel.add(title);

//        Mitt panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));

        middlePanel.add(welcomeLabel, BorderLayout.NORTH);
        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardWrapper.add(skapaKort("Klassiskt spel"));

        middlePanel.add(cardWrapper, BorderLayout.CENTER);


//        middlePanel.setBackground(Color.BLUE);


//        Starta nytt spel
        JPanel bottomPanel = new JPanel();

        JButton startGameButton = new JButton("Starta nytt spel");
        startGameButton.setBackground(Color.GREEN);
        startGameButton.setForeground(Color.WHITE);

        startGameButton.addActionListener(e -> showMode());

        bottomPanel.add(startGameButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private JPanel skapaKort(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 300));
        card.setBackground(new Color(230, 230, 255));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));


        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private void showMode() {
        JFrame modeFrame = new JFrame("Klassiskt läge");
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
