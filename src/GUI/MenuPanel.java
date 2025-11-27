package GUI;

import Client.ClientBase;
import Client.ClientStart;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private JFrame frame;

    public MenuPanel(String username, JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TOP PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(30, 144, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JLabel title = new JLabel("Quizkampen");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 32));

        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        //  MIDDLE PANEL
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(30, 144, 255));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel welcomeLabel = new JLabel("Välkommen " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);

        middlePanel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cardWrapper.setBackground(new Color(30, 144, 255));
        cardWrapper.add(createCard("Klassiskt läge"));
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


        middlePanel.add(cardWrapper, BorderLayout.CENTER);
        add(middlePanel, BorderLayout.CENTER);

        //  BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(30, 144, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JButton startGameButton = new JButton("Starta nytt spel");
        startGameButton.setBackground(new Color(34, 139, 34));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        startGameButton.setFocusPainted(false);
        startGameButton.setPreferredSize(new Dimension(200, 50));
        startGameButton.addActionListener(e -> {
            ClientStart cs = new ClientStart(frame);
            ClientBase client = cs.getClient();

            client.sendMessage(new Message(MessageType.MATCHMAKING, null));

            MatchmakingPanel panel = new MatchmakingPanel(() -> {
                frame.setContentPane(new MenuPanel(username, frame));
                frame.revalidate();
                frame.repaint();
            });

            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        });

        bottomPanel.add(startGameButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));

        card.setPreferredSize(new Dimension(300, 400));


        card.add(label, BorderLayout.CENTER);
        return card;
    }

//    private void showMode() {
//        JFrame modeFrame = new JFrame("Klassiskt läge");
//        modeFrame.setSize(350, 200);
//        modeFrame.setLocationRelativeTo(null);
//        modeFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
//
//        JButton playFriend = new JButton("Spela");
//        playFriend.setBackground(new Color(34, 139, 34));
//        playFriend.setForeground(Color.WHITE);
//        playFriend.setFont(new Font("Arial", Font.BOLD, 16));
//        playFriend.setFocusPainted(false);
//        playFriend.setPreferredSize(new Dimension(200, 50));

//        modeFrame.add(playFriend);

//        playFriend.addActionListener(e -> new ClientStart());

//        modeFrame.setVisible(true);
//    }

    //test
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Quizkampen");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(700, 700);
//        frame.setLocationRelativeTo(null);
//
//        frame.setContentPane(new MenuPanel("Player1"));
//        frame.setVisible(true);
//    }
}

