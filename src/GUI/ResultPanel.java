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


        // Knapp


//        Runda

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