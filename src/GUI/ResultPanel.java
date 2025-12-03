package GUI;

import Client.ClientBase;
import Database.AnswerOption;
import GameComponents.Score;
import Quizgame.shared.User;
import Server.SettingsLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultPanel extends JPanel {

    private java.util.List<JButton> playerOneButtons = new ArrayList<>();
    private java.util.List<JButton> playerTwoButtons = new ArrayList<>();
    private java.util.List<JLabel> roundLabels = new ArrayList<>();
    private JLabel scoreLabel;
    private User user;
    private ClientBase client;
    private List<Score> roundScores = new ArrayList<>();
    private int numberOfQuestions;
    private JPanel centerPanel;
    private User playerOne;
    private User playerTwo;
    int maxNumberOfRounds = SettingsLoader.getRoundsPerGame();
    private JPanel topPanel;
    private int widthText = 85;
    private int widthScore = 94;
    private int widthLong = 110;
    private int rowHeight = 70;
    private int rowCompHeight = 40;
    private int buttonDimension = 30;

    public ResultPanel(List<Score> roundScores, User user, ClientBase client) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));
        this.roundScores = roundScores;
        this.user = user;
        this.playerOne = roundScores.getFirst().getPlayer1();
        this.playerTwo = roundScores.getFirst().getPlayer2();
        numberOfQuestions = roundScores.getFirst().getNumberOfQuestions();
        for (Score score : roundScores) {
            String category = score.getCategory();
            System.out.println("category is: " + category);
        }


        // Top panelen
        this.topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
//        topPanel.setPreferredSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*3));
//        topPanel.setMaximumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*3));
//        topPanel.setMinimumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*3));
        topPanel.setOpaque(false);

        JLabel p1 = playerLabel(playerOne.getAvatar(), playerOne.getUsername());
        p1.setPreferredSize(new Dimension(widthLong, rowHeight*2));
        p1.setMinimumSize(new Dimension(widthLong, rowHeight*2));
        p1.setMaximumSize(new Dimension(widthLong, rowHeight*2));
        JLabel p2 = playerLabel(playerTwo.getAvatar(), playerTwo.getUsername());

        p2.setPreferredSize(new Dimension(widthLong, rowHeight*2));
        p2.setMinimumSize(new Dimension(widthLong, rowHeight*2));
        p2.setMaximumSize(new Dimension(widthLong, rowHeight*2));

//        this.scoreLabel = new JLabel("       ");
//        scoreLabel.setPreferredSize(new Dimension(widthText, 110));
//        scoreLabel.setMinimumSize(new Dimension(widthText, 110));
//        scoreLabel.setMaximumSize(new Dimension(widthText, 110));
//        scoreLabel.setOpaque(true);
//        scoreLabel.setBackground(new Color(30, 144, 255));
//////        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
////        scoreLabel.setForeground(Color.WHITE);
//        JLabel scoreLabel1 = new JLabel("       ");
//        scoreLabel1.setOpaque(true);
//        scoreLabel1.setPreferredSize(new Dimension(widthText, 110));
//        scoreLabel1.setMinimumSize(new Dimension(widthText, 110));
//        scoreLabel1.setMaximumSize(new Dimension(widthText, 110));
//        scoreLabel1.setBackground(new Color(30, 144, 255));
//
//        JLabel scoreLabel2 = new JLabel("       ");
//        scoreLabel2.setMinimumSize(new Dimension(widthText, 110));
//        scoreLabel2.setMaximumSize(new Dimension(widthText, 110));
//        scoreLabel2.setMinimumSize(new Dimension(widthText, 110));
//        scoreLabel2.setBackground(new Color(30, 144, 255));
//        scoreLabel2.setOpaque(true);
//        scoreLabel2.setBackground(new Color(30, 144, 255));


        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(p1);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(p2);
        topPanel.add(Box.createHorizontalGlue());

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.add(topPanel, BorderLayout.CENTER);
        add(topWrapper, BorderLayout.NORTH);

        centerPanel = new JPanel(new BorderLayout());
//        centerPanel.setPreferredSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*7));
//        centerPanel.setMaximumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*7));
//        centerPanel.setMinimumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*7));

        updateCenterPanel(roundScores);
        centerPanel.setOpaque(false);
        centerPanel.setBackground(new Color(30, 144, 255));
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

    }
    /*public void setNextRoundButton(boolean waiting) {
        // Rensa tidigare komponenter i bottomPanel
        bottomPanel.removeAll();
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);

        if (waiting) {
            JLabel waitingForOpponent = new JLabel("Waiting for opponent");
            waitingForOpponent.setForeground(Color.WHITE);
            waitingForOpponent.setFont(new Font("Arial", Font.BOLD, 16));
            bottomPanel.add(waitingForOpponent);
        } else {
            // Panel för knappar på samma rad
            JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonRow.setOpaque(false);

            JButton backButton = new JButton("Back to Menu");
            backButton.addActionListener(e -> {
                // Gå tillbaka till menyn
                MenuPanel menuPanel = new MenuPanel(user, client, client.getMainframe());
                client.getMainframe().setContentPane(menuPanel);
                client.getMainframe().revalidate();
                client.getMainframe().repaint();
            });

            JButton nextRound = new JButton("Start next round");
            nextRound.addActionListener(e -> {
                client.sendMessage(new Message(MessageType.START_NEXT_ROUND, user));
            });

            buttonRow.add(backButton);
            buttonRow.add(nextRound);

            bottomPanel.add(buttonRow);
        }

        bottomPanel.revalidate();
        bottomPanel.repaint();
    } */
        label.setPreferredSize(new Dimension(90, 110));
        label.setMaximumSize(new Dimension(90, 110));
        label.setMinimumSize(new Dimension(90, 110)); // lite högre för text
        label.setFont(new Font("Montserrat", Font.BOLD, 26));
        label.setForeground(Color.orange);
        label.setBackground(Color.BLACK);
        label.setOpaque(false);
//        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

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
            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
            scorePanel.setOpaque(false);
            scorePanel.setPreferredSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 50), rowHeight*6));
            scorePanel.setMaximumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 50), rowHeight*6));
            scorePanel.setMinimumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 50), rowHeight*6));
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
                questionResult.setOpaque(true);
                questionResult.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));
                questionResult.setMinimumSize(new Dimension(buttonDimension, buttonDimension));
                questionResult.setMaximumSize(new Dimension(buttonDimension, buttonDimension));
                questionResult.setPreferredSize(new Dimension(buttonDimension, buttonDimension));
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
                questionResult.setOpaque(true);
                questionResult.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));
                questionResult.setMinimumSize(new Dimension(buttonDimension, buttonDimension));
                questionResult.setMaximumSize(new Dimension(buttonDimension, buttonDimension));
                questionResult.setPreferredSize(new Dimension(buttonDimension, buttonDimension));
                questionResult.setBackground(assignColorToButton(points));
                playerTwoButtons.add(questionResult);
            }
        }
    }
    private JPanel getScoreButtonBatch(User player, int index) {
        int size = roundScores.size();
        int start = numberOfQuestions*index;
        int end = start + numberOfQuestions;

        if (index < size) {
            if (player.getUsername().equalsIgnoreCase(playerOne.getUsername())) {
                JPanel scoreButtonBatch = new JPanel();
                scoreButtonBatch.setLayout(new BoxLayout(scoreButtonBatch, BoxLayout.X_AXIS));
                scoreButtonBatch.setPreferredSize(new Dimension(widthScore, rowCompHeight));
                scoreButtonBatch.setMinimumSize(new Dimension(widthScore, rowCompHeight));
                scoreButtonBatch.setMaximumSize(new Dimension(widthScore, rowCompHeight));
                scoreButtonBatch.setOpaque(true);
                scoreButtonBatch.setBackground(new Color(30, 144, 255));
                scoreButtonBatch.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));
                for (int i = start; i < end; i++) {
                    scoreButtonBatch.add(playerOneButtons.get(i));
                }
                return scoreButtonBatch;
            } else if (player.getUsername().equalsIgnoreCase(playerTwo.getUsername())) {
                JPanel scoreButtonBatch = new JPanel();
                scoreButtonBatch.setLayout(new BoxLayout(scoreButtonBatch, BoxLayout.X_AXIS));
                scoreButtonBatch.setPreferredSize(new Dimension(widthScore, rowCompHeight));
                scoreButtonBatch.setMinimumSize(new Dimension(widthScore, rowCompHeight));
                scoreButtonBatch.setMaximumSize(new Dimension(widthScore, rowCompHeight));
                scoreButtonBatch.setOpaque(true);
                scoreButtonBatch.setBackground(new Color(30, 144, 255));
                scoreButtonBatch.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));
                for (int i = start; i < end; i++) {
                    scoreButtonBatch.add(playerTwoButtons.get(i));
                }
                return scoreButtonBatch;
            }
        }
        else {
            JPanel scoreButtonBatch = new JPanel();
            scoreButtonBatch.setLayout(new BoxLayout(scoreButtonBatch, BoxLayout.X_AXIS));
            scoreButtonBatch.setPreferredSize(new Dimension(widthScore, rowCompHeight));
            scoreButtonBatch.setMinimumSize(new Dimension(widthScore, rowCompHeight));
            scoreButtonBatch.setMaximumSize(new Dimension(widthScore, rowCompHeight));
            List<JButton> emptyButtons = getEmptyButtons();
            for (JButton button : emptyButtons) {
                scoreButtonBatch.add(button);
            }
            scoreButtonBatch.setOpaque(true);
            scoreButtonBatch.setBackground(new Color(30, 144, 255));
            scoreButtonBatch.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));
            return scoreButtonBatch;
        }
        return null;
    }

    private List<JButton> getEmptyButtons(){
        List<JButton> emptyButtons = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            JButton emptyButton = new JButton();
            emptyButton.setOpaque(true);
            emptyButton.setMinimumSize(new Dimension(buttonDimension, buttonDimension));
            emptyButton.setMaximumSize(new Dimension(buttonDimension, buttonDimension));
            emptyButton.setPreferredSize(new Dimension(buttonDimension, buttonDimension));
            emptyButton.setBackground(Color.LIGHT_GRAY);
            emptyButton.setBackground(assignColorToButton(2));
            emptyButton.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));
            emptyButtons.add(emptyButton);
        }
        return emptyButtons;
    }

    private JPanel getRow(int index) {
        System.out.println("getRows in TESTResultPanel was reached, maxNumberOfRounds is: " + maxNumberOfRounds + " and roundScores.size is: " + roundScores.size());
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.add(Box.createHorizontalStrut(10));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowHeight));
        row.setPreferredSize(new Dimension(0, rowHeight));
        row.setMinimumSize(new Dimension(0, rowHeight));

        row.setOpaque(true);
        row.setBackground(new Color(30, 144, 255));
        row.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 1));

        int printIndex = index+1;
        JLabel roundIndex = new JLabel("Round " + printIndex + " :");
        roundIndex.setPreferredSize(new Dimension(widthText, rowCompHeight));
        roundIndex.setMinimumSize(new Dimension(widthText, rowCompHeight));
        roundIndex.setMinimumSize(new Dimension(widthText, rowCompHeight));
        roundIndex.setOpaque(false);

        String roundCategoryString = "";
        if (index < roundScores.size()) {
            roundCategoryString = roundScores.get(index).getCategory();
        }
        JLabel roundCategory = new JLabel(roundCategoryString);
        roundCategory.setFont(new Font("Montserrat", Font.BOLD, 14));
        roundCategory.setPreferredSize(new Dimension(widthLong+20, rowCompHeight));
        roundCategory.setMaximumSize(new Dimension(widthLong+20, rowCompHeight));
        roundCategory.setMinimumSize(new Dimension(widthLong+20, rowCompHeight));
        roundCategory.setForeground(Color.orange);
        roundCategory.setHorizontalAlignment(0);
        JLabel emptyPanel = new JLabel();
        emptyPanel.setPreferredSize(new Dimension(widthText, rowCompHeight));
        emptyPanel.setMaximumSize(new Dimension(widthText, rowCompHeight));
        emptyPanel.setMinimumSize(new Dimension(widthText, rowCompHeight));
        emptyPanel.setOpaque(false);
        emptyPanel.setBackground(new Color(30, 144, 255));

        roundIndex.setFont(new Font("Montserrat", Font.BOLD, 14));
        roundIndex.setForeground(Color.orange);

        roundIndex.setHorizontalAlignment(0);
        row.add(Box.createHorizontalGlue());
        row.add(roundIndex);
        row.add(Box.createHorizontalGlue());
        row.add(getScoreButtonBatch(playerOne, index));
        row.add(Box.createHorizontalGlue());
        row.add(roundCategory);
        row.add(Box.createHorizontalGlue());
        row.add(getScoreButtonBatch(playerTwo, index));
        row.add(Box.createHorizontalGlue());
        row.add(emptyPanel);
        row.add(Box.createHorizontalGlue());

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
