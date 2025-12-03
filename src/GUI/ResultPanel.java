package GUI;

import Client.ClientBase;
import GameComponents.Game;
import GameComponents.Score;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultPanel extends JPanel {

    private java.util.List<JButton> playerOneButtons = new ArrayList<>();
    private java.util.List<JButton> playerTwoButtons = new ArrayList<>();
    private JPanel bottomPanel = new JPanel();
    private ClientBase client;
    private User user;
    private List<Score> roundScores;

    public ResultPanel(List<Score> roundScores, User user, ClientBase client){
        this.client = client;
        this.user = user;
        this.roundScores = roundScores;
        String playerOne = roundScores.getFirst().getPlayer1().getUsername();
        String playerTwo =  roundScores.getFirst().getPlayer2().getUsername();


        List <String> roundTexts = new ArrayList<>();
        for (Score score : roundScores){
            roundTexts.add(score.getCategory());
        }
        String roundText = roundTexts.getFirst();
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

        // Spelare och poäng
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);

        JLabel playerOneLabel = playerLabel(playerOne);
        JLabel playerTwoLabel = playerLabel(playerTwo);
        String text = "";

        JLabel scoreLabel = new JLabel(text);
        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);

        topPanel.add(playerOneLabel);
        topPanel.add(scoreLabel);
        topPanel.add(playerTwoLabel);
        add(topPanel, BorderLayout.NORTH);

        // Namn
        JPanel namesPanel = new JPanel(new GridLayout(1, 2));
        namesPanel.setOpaque(false);

        JLabel leftName = new JLabel(playerOne, SwingConstants.CENTER);
        JLabel rightName = new JLabel(playerTwo, SwingConstants.CENTER);
        leftName.setOpaque(true);
        leftName.setBackground(Color.WHITE);
        rightName.setOpaque(true);
        rightName.setBackground(Color.WHITE);

        namesPanel.add(leftName);
        namesPanel.add(rightName);
        add(namesPanel, BorderLayout.CENTER);

        // Knapp
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
        JLabel roundLabel = new JLabel(roundText, SwingConstants.CENTER);
        roundLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        roundLabel.setForeground(Color.WHITE);
        roundLabel.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.add(roundLabel);

        add(bottomPanel, BorderLayout.SOUTH);

    }
    public void setNextRoundButton(boolean waiting){

        if (waiting) {
            JLabel waitingForOpponent = new JLabel("Waiting for opponent");
            bottomPanel.add(waitingForOpponent);
        }
        else {
            JButton nextRound = new JButton("Start next round");
            bottomPanel.add(nextRound);
            nextRound.addActionListener(e -> {
                client.sendMessage(new Message(MessageType.START_NEXT_ROUND, user));
            });
        }
    }

    private static JLabel playerLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 90));
        label.setFont(new Font("Times New Roman", Font.BOLD, 30));
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return label;
    }

    //    Markera knappar
    public void markPlayerOneButton(int temp, boolean correct) {
        if(temp < 0 || temp >= playerOneButtons.size()) return;
        Color color;
        if(correct) {
            color = Color.GREEN;
        } else {
            color = Color.RED;
        }
        playerOneButtons.get(temp).setBackground(color);

    }

    public void markPlayerTwoButton(int temp, boolean correct) {
        if(temp < 0 || temp >= playerTwoButtons.size()) return;
        Color color;
        if(correct) {
            color = Color.GREEN;
        } else {
            color = Color.RED;
        }
        playerTwoButtons.get(temp).setBackground(color);

    }

    public void resetButtons() {
        for (JButton b : playerOneButtons) b.setBackground(Color.LIGHT_GRAY);
        for (JButton b : playerTwoButtons) b.setBackground(Color.LIGHT_GRAY);
    }

    public void setPlayerOneRightA(int roundIndex) {
        if(roundIndex < 0 || roundIndex >= playerOneButtons.size()) return;
        JButton b1 = playerOneButtons.get(roundIndex);
        b1.setText("1");
        b1.setBackground(Color.GREEN);

    }

    public void setPlayerTwoRightA(int roundIndex) {
        if(roundIndex < 0 || roundIndex >= playerTwoButtons.size()) return;
        JButton b2 = playerTwoButtons.get(roundIndex);
        b2.setText("2");
        b2.setBackground(Color.RED);
    }


    // TEST
    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test ResultPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ResultPanel panel = new ResultPanel("KÖPA", "CHIPS", 0, 0, "Round 1");
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Timer(1000, e -> panel.markPlayerOneButton(0, true)).start();
        new Timer(2000, e -> panel.markPlayerOneButton(1, false)).start();
        new Timer(3000, e -> panel.markPlayerTwoButton(0, true)).start();
        new Timer(4000, e -> panel.markPlayerTwoButton(2, false)).start();
        new Timer(1000, e -> panel.setPlayerOneRightA(0)).start();
    }
     */
}