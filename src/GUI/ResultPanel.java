package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultPanel extends JPanel {

    private java.util.List<JButton> playerOneButtons = new ArrayList<>();
    private java.util.List<JButton> playerTwoButtons = new ArrayList<>();

    public ResultPanel(String playerOne, String playerTwo,
                       int scoreOne, int scoreTwo, String roundText) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

        // Spelare och poäng
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);

        JLabel scoreLabel = new JLabel(scoreOne + " - " + scoreTwo);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
    }
}
