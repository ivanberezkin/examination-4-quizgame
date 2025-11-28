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

        JLabel playerOneLabel = playerLabel(playerOne);
        JLabel playerTwoLabel = playerLabel(playerTwo);

        JLabel scoreLabel = new JLabel(scoreOne + "-" +  scoreTwo);
        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);

        topPanel.add(playerOneLabel);
        topPanel.add(playerTwoLabel);
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);
        // Namn
        JPanel namesPanel = new JPanel(new GridLayout(2, 1));
        namesPanel.setOpaque(false);

        JLabel leftName = new JLabel(playerOne, SwingConstants.CENTER);
        JLabel rightName = new JLabel(playerTwo, SwingConstants.CENTER);
        leftName.setOpaque(true);
        leftName.setForeground(Color.WHITE);
        rightName.setOpaque(true);
        rightName.setForeground(Color.WHITE);

        namesPanel.add(leftName);
        namesPanel.add(rightName);
        add(namesPanel, BorderLayout.CENTER);

        // Knapp
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel buttonRow1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 ,10));
        buttonRow1.setOpaque(false);
        JPanel buttonRow2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonRow2.setOpaque(false);

        for (int i = 0; i < 3; i++) { /* antal knappar */
            JButton b1 = new JButton();
            b1.setPreferredSize(new Dimension(50, 50));
            b1.setBackground(Color.LIGHT_GRAY);
            playerOneButtons.add(b1);
            buttonRow1.add(b1);

            JButton b2 = new JButton();
            b2.setPreferredSize(new Dimension(50, 50));
            b2.setBackground(Color.LIGHT_GRAY);
            playerTwoButtons.add(b2);
            buttonRow2.add(b2);

        }
        bottomPanel.add(buttonRow1);
        bottomPanel.add(buttonRow2);

//        Runda
        JPanel roundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    }

    private static JLabel playerLabel(String text) {

        return label;
    }

    //    Markera knappar
    public void markPlayerOneButton(int temp, boolean correct) {

    }

    public void markPlayerTwoButton(int temp, boolean correct) {

    }

    public void resetButtons() {

    }

    public void setPlayerOneRoundWin(int roundIndex) {
    }

    public void setPlayerTwoRoundWin(int roundIndex) {
    }


    // TEST
    public static void main(String[] args) {
    }
}