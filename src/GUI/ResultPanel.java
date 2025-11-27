package GUI;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    public ResultPanel(String playerOne, String playerTwo,
                       int scoreOne, int scoreTwo, String roundText) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

//        Top
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        setOpaque???

        JLabel playerOneText = playerLabel("1", Color.BLACK);
        JLabel playerTwoText = playerLabel("2", Color.BLACK);

        JLabel scoreLabel = new JLabel(scoreOne + " - " + scoreTwo);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);

        topPanel.add(playerOneText);
        topPanel.add(scoreLabel);
        topPanel.add(playerTwoText);



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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
