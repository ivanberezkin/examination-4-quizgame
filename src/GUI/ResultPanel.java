package GUI;

import GameComponents.Match;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    public ResultPanel(Match match) {
        String playerOne = match.getPlayersList().getFirst().getUsername();
        String playerTwo = match.getPlayersList().get(1).getUsername();

//        int scoreOne = match.getPointsPlayer1(); Lista, poäng per fråga
//        int scoreTwo = match.getPointsPlayer2();
        String roundText;

        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

//        Top
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        setOpaque???

        JLabel playerOneText = playerLabel("1", Color.BLACK);
        JLabel playerTwoText = playerLabel("2", Color.BLACK);
//
//        JLabel scoreLabel = new JLabel(scoreOne + " - " + scoreTwo);
//        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        scoreLabel.setForeground(Color.WHITE);
//
//        topPanel.add(playerOneText);
//        topPanel.add(scoreLabel);
//        topPanel.add(playerTwoText);



    }

    private JLabel playerLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 90));
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.BLACK);

        label.setBackground(color);
        setOpaque(true);

        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


        return label;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Test");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        frame.setSize(400, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
