package GUI;

import Client.ClientBase;
import Database.AnswerOption;
import Database.Question;
import GameComponents.Score;
import Quizgame.shared.User;
import Server.SettingsLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TESTResultPanel extends JPanel {

    private java.util.List<JButton> playerOneButtons = new ArrayList<>();
    private java.util.List<JButton> playerTwoButtons = new ArrayList<>();
    private java.util.List<JLabel> roundLabels = new ArrayList<>();
    private JLabel scoreLabel;
    private User user;
    private ClientBase client;
    private List<Score> roundScores = new ArrayList<>();
    private String category;
    private List<String> categories = new ArrayList<>();
    private int numberOfQuestions;
    private JPanel centerPanel;
    private User playerOne;
    private User playerTwo;
    int maxNumberOfRounds = SettingsLoader.getRoundsPerGame();

    public TESTResultPanel(List<Score> roundScores, User user, ClientBase client) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));
        this.roundScores = roundScores;
        this.user = user;
        this.playerOne = roundScores.getFirst().getPlayer1();
        this.playerTwo = roundScores.getFirst().getPlayer2();
        numberOfQuestions = roundScores.getFirst().getNumberOfQuestions();
        for (Score score : roundScores) {
            String category = score.getCategory();
            categories.add(category);
            System.out.println("category is: " + category);
        }


        // Top panelen
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);

        JLabel p1 = playerLabel(playerOne.getAvatar(), playerOne.getUsername());
        JLabel p2 = playerLabel(playerTwo.getAvatar(), playerTwo.getUsername());

        this.scoreLabel = new JLabel(playerOne.getUsername() + " - " + playerTwo.getUsername());
        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        scoreLabel.setForeground(Color.WHITE);

        topPanel.add(p1);
        topPanel.add(scoreLabel);
        topPanel.add(p2);

        add(topPanel, BorderLayout.NORTH);

        centerPanel = new JPanel(new BorderLayout());
        updateCenterPanel(roundScores);
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);

    }

//        for (int i = 0; i < 5; i++) {
//
//            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
//            row.setOpaque(false);

    // Vänster knappar
//            for (int j = 0; j < 3; j++) {
//                JButton left = new JButton();
//                left.setPreferredSize(new Dimension(30, 10));
//                left.setBackground(Color.LIGHT_GRAY);
//                playerOneButtons.add(left);
//                row.add(left);
//            }

    // Round label i mitten
//            JLabel rl = new JLabel("Round " + (i + 1));
//            rl.setFont(new Font("Times New Roman", Font.BOLD, 20));
//            rl.setForeground(Color.WHITE);
//            roundLabels.add(rl);
//            row.add(rl);


    // Höger knappar
//            for (int j = 0; j < 3; j++) {
//                JButton right = new JButton();
//                right.setPreferredSize(new Dimension(30, 10));
//                right.setBackground(Color.LIGHT_GRAY);
//                playerTwoButtons.add(right);
//                row.add(right);
//            }
//
//            centerPanel.add(row);
//        }
//
//    }


    private static JLabel playerLabel(Icon avatar, String name) {
        JLabel label = new JLabel(name, avatar, SwingConstants.CENTER);

        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);

        label.setPreferredSize(new Dimension(90, 110)); // lite högre för text
        label.setFont(new Font("Times New Roman", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return label;
    }

    public void updateScore(int newScoreOne, int newScoreTwo) {
        scoreLabel.setText(newScoreOne + " - " + newScoreTwo);
    }
    public void markAnswer(int questionIndex, boolean isCorrect, boolean isPlayerOne) {
        JButton btn;

        if (isPlayerOne) {
            btn = playerOneButtons.get(questionIndex);
        } else {
            btn = playerTwoButtons.get(questionIndex);
        }

        if (isCorrect) {
            btn.setBackground(Color.GREEN);
        } else {
            btn.setBackground(Color.RED);
        }
    }


    public void setRoundText(int index, String text) {
        if (index < 0 || index >= roundLabels.size()) return;
        roundLabels.get(index).setText(text);
    }
    public void markRoundAnswer(int round, String userAnswerP1, String userAnswerP2, Database.Question question) {
        List<AnswerOption> options = question.getAnswerOptions();

        for (int i = 0; i < options.size(); i++) {
            JButton btn = playerOneButtons.get(round * 3 + i);
            AnswerOption opt = options.get(i);

            btn.setText(opt.getText());

            if (opt.getCorrect()) {
                btn.setBackground(Color.GREEN);
            } else if (opt.getText().equals(userAnswerP1)) {
                btn.setBackground(Color.RED);
            } else {
                btn.setBackground(Color.LIGHT_GRAY);
            }
        }

        for (int i = 0; i < options.size(); i++) {
            JButton btn = playerTwoButtons.get(round * 3 + i);
            AnswerOption opt = options.get(i);

            btn.setText(opt.getText());

            if (opt.getCorrect()) {
                btn.setBackground(Color.GREEN);
            } else if (opt.getText().equals(userAnswerP2)) {
                btn.setBackground(Color.RED);
            } else {
                btn.setBackground(Color.LIGHT_GRAY);
            }
        }
    }
    private void updateCenterPanel(List<Score>roundScores) {
        createButtons(roundScores);
        if (centerPanel != null) {
            centerPanel.removeAll();
            JPanel scorePanel = new JPanel(new GridLayout(maxNumberOfRounds,1));
            for (int i = 0; i < maxNumberOfRounds; i++){
                scorePanel.add(getRow(i));
            }
            centerPanel.add(scorePanel, BorderLayout.CENTER);
            add(centerPanel, BorderLayout.CENTER);
            repaint();
            revalidate();
        }
    }

    private void createButtons(List<Score>roundScores){
        playerOneButtons.clear();
        playerTwoButtons.clear();
        for (Score score : roundScores) {
            int [] playerOneScores = score.getRoundScoresPlayer1();
            for (int i = 0; i < playerOneScores.length; i++) {
                int points = playerOneScores[i];
                System.out.println("In createButtons, points is: " + points);
                JButton questionResult = new JButton();
                questionResult.setPreferredSize(new Dimension(40, 40));
                questionResult.setBackground(assignColorToButton(points));
                playerOneButtons.add(questionResult);
            }
        }
        for (Score score : roundScores){
            int [] playerTwoScores = score.getRoundScoresPlayer2();
            for (int i = 0; i < playerTwoScores.length; i++) {
                int points = playerTwoScores[i];
                System.out.println("In createButtons, points is: " + points);
                JButton questionResult = new JButton();
                questionResult.setPreferredSize(new Dimension(40, 40));
                questionResult.setBackground(assignColorToButton(points));
                playerTwoButtons.add(questionResult);
            }
        }
    }
    private JPanel getScoreButtonBatch(User player, int index) {
        int size = roundScores.size();
        if (index < size) {
            if (player.getUsername().equalsIgnoreCase(playerOne.getUsername())) {
                JPanel scoreButtonBatch = new JPanel(new GridLayout(1, numberOfQuestions));
                scoreButtonBatch.add(getPlayerButtons(playerOne, index));
                return scoreButtonBatch;
            } else if (player.getUsername().equalsIgnoreCase(playerTwo.getUsername())) {
                JPanel scoreButtonBatch = new JPanel(new GridLayout(1, numberOfQuestions));
                scoreButtonBatch.add(getPlayerButtons(playerTwo, index));
                return scoreButtonBatch;
            }
        }
        else {
            JPanel scoreButtonBatch = new JPanel(new GridLayout(1, numberOfQuestions));
            List<JButton> emptyButtons = getEmptyButtons();
            for (JButton button : emptyButtons) {
                scoreButtonBatch.add(button);
            }
            return scoreButtonBatch;
        }
        return null;
    }
    private JPanel getPlayerButtons(User player, int index){
        JPanel scoreButtonBatch = new JPanel(new GridLayout(1, numberOfQuestions));
        int start = numberOfQuestions*index;
        int end = start + numberOfQuestions;

        if (player.getUsername().equalsIgnoreCase(playerOne.getUsername())) {
            for (int i = start; i < end; i++) {
                scoreButtonBatch.add(playerOneButtons.get(i));
                System.out.println("In getPlayerButtons, returned button is button number: " + i);
            }
            return scoreButtonBatch;
        }
        else {
            for (int i = start; i < end; i++) {
                scoreButtonBatch.add(playerTwoButtons.get(i));
            }
            return scoreButtonBatch;
        }
    }


    private List<JButton> getEmptyButtons(){
        List<JButton> emptyButtons = new ArrayList<>();
            for (int i = 0; i < numberOfQuestions; i++) {
                JButton emptyButton = new JButton();
                emptyButton.setPreferredSize(new Dimension(40, 40));
                emptyButton.setBackground(Color.LIGHT_GRAY);
                emptyButton.setBackground(assignColorToButton(2));
                emptyButtons.add(emptyButton);
        }
        return emptyButtons;
    }

    private JPanel getRow(int index) {
        System.out.println("getRows in TESTResultPanel was reached, maxNumberOfRounds is: " + maxNumberOfRounds + " and roundScores.size is: " + roundScores.size());
        JPanel row = new JPanel(new GridLayout(1, 4));
        JPanel roundCategories = new JPanel(new GridLayout(maxNumberOfRounds, 1));
        int printIndex = index+1;
        JLabel roundIndex = new JLabel("Round: " + printIndex);



        String roundCategoryString = "";
        if (index < roundScores.size()) {
            roundCategoryString = roundScores.get(index).getCategory();
        }
        JLabel roundCategory = new JLabel(roundCategoryString);
        roundCategory.setFont(new Font("Times New Roman", Font.BOLD, 20));
        roundCategories.add(roundCategory);

        roundIndex.setFont(new Font("Times New Roman", Font.BOLD, 20));
        roundIndex.setForeground(Color.black);
        row.add(roundIndex);
        row.add(getScoreButtonBatch(playerOne, index));
        row.add(roundCategory);
        row.add(getScoreButtonBatch(playerTwo, index));
        return row;
    }

    private Color assignColorToButton(int i){
        if (i == 1) {
            return Color.GREEN;
        } else if (i == 0) {
            return Color.RED;
        } else {
            return Color.LIGHT_GRAY;
        }
    }
}
