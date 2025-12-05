package GUI;
import Client.ClientBase;
import Database.AnswerOption;
import GameComponents.Score;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
import Server.SettingsLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.XMLFormatter;

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
    private JPanel bottomPanel;
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
        System.out.println("in ResultPanel, roundScores.size is:" + roundScores.size());
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));
        this.roundScores = roundScores;
        this.user = user;
        this.client = client;
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
        p1.setPreferredSize(new Dimension(widthLong, rowHeight * 2));
        p1.setMinimumSize(new Dimension(widthLong, rowHeight * 2));
        p1.setMaximumSize(new Dimension(widthLong, rowHeight * 2));
        JLabel p2 = playerLabel(playerTwo.getAvatar(), playerTwo.getUsername());

        p2.setPreferredSize(new Dimension(widthLong, rowHeight * 2));
        p2.setMinimumSize(new Dimension(widthLong, rowHeight * 2));
        p2.setMaximumSize(new Dimension(widthLong, rowHeight * 2));


        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(p1);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(p2);
        topPanel.add(Box.createHorizontalGlue());

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.add(topPanel, BorderLayout.CENTER);
        add(topWrapper, BorderLayout.NORTH);


//        centerPanel.setPreferredSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*7));
//        centerPanel.setMaximumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*7));
//        centerPanel.setMinimumSize(new Dimension((widthScore*2 +widthText*2 + widthLong + 100), rowHeight*7));

        centerPanel = new JPanel(new BorderLayout());
        updateCenterPanel();
        centerPanel.setOpaque(false);
        centerPanel.setBackground(new Color(30, 144, 255));
        add(centerPanel, BorderLayout.CENTER);

        if (roundScores.size() == maxNumberOfRounds) {
            JPanel endPanel = createEndPanel(roundScores);
            if (bottomPanel != null) {
                bottomPanel.removeAll();
                bottomPanel.add(endPanel);
                add(bottomPanel);
                repaint();
                revalidate();
            }
            else {
                bottomPanel = new JPanel(new BorderLayout());
                bottomPanel.setBackground(new Color(30, 144, 255));
                bottomPanel.setPreferredSize(new Dimension(0, 120));
                bottomPanel.setMinimumSize(new Dimension(0, 120));
                bottomPanel.setMaximumSize(new Dimension(0, 120));
                bottomPanel.add(endPanel, BorderLayout.CENTER);
                add(bottomPanel, BorderLayout.SOUTH);
                repaint();
                revalidate();
            }
        }
        else {
            bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bottomPanel.setOpaque(false);
            add(bottomPanel, BorderLayout.SOUTH);
            setNextRoundButton(whoGetsToChoose());

        }
    }

    private static JLabel playerLabel(Icon avatar, String name) {
        JLabel label = new JLabel(name, avatar, SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setPreferredSize(new Dimension(90, 110));
        label.setMaximumSize(new Dimension(90, 110));
        label.setMinimumSize(new Dimension(90, 110)); // lite högre för text
        label.setFont(new Font("Montserrat", Font.BOLD, 26));
        label.setForeground(Color.orange);
        label.setBackground(Color.BLACK);
        label.setOpaque(false);
        return label;
    }


    private boolean whoGetsToChoose() {

        if (roundScores.size() < maxNumberOfRounds) {
            if (user.getUsername().equalsIgnoreCase(playerOne.getUsername()) && roundScores.size() % 2 != 0) {
                return true;
            } else if (user.getUsername().equalsIgnoreCase(playerTwo.getUsername()) && roundScores.size() % 2 == 0) {
                return true;
            }
        }
        return false;
    }

    public void setNextRoundButton(boolean waiting) {
        bottomPanel.removeAll();
        IO.println(user.getUsername() + " is waiting " + waiting);

        if (waiting) {
            JLabel waitingLabel = new JLabel("Waiting for opponent to choose category");
            waitingLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            waitingLabel.setForeground(Color.BLACK);
            bottomPanel.add(waitingLabel);
            bottomPanel.revalidate();
            bottomPanel.repaint();
        } else {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            row.setOpaque(false);

            JButton backButton = new JButton("Back to menu");
            backButton.addActionListener(e -> {
                MenuPanel menu = new MenuPanel(user, client, client.getMainframe());
                client.getMainframe().setContentPane(menu);
                client.getMainframe().revalidate();
                client.getMainframe().repaint();
            });

            JButton nextRound = new JButton("Start next round");
            nextRound.addActionListener(e -> {
                client.sendMessage(new Message(MessageType.START_NEXT_ROUND, user));
            });
            row.add(backButton);
            row.add(nextRound);
            bottomPanel.add(row);
        }
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    public void updateCenterPanel() {
        createButtons(roundScores);
        if (centerPanel != null) {
            centerPanel.removeAll();

        }
        else {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.setOpaque(false);
            centerPanel.setBackground(new Color(30, 144, 255));
            add(centerPanel, BorderLayout.CENTER);
        }
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setOpaque(false);
        scorePanel.setPreferredSize(new Dimension((widthScore * 2 + widthText * 2 + widthLong + 50), rowHeight * 6));
        scorePanel.setMaximumSize(new Dimension((widthScore * 2 + widthText * 2 + widthLong + 50), rowHeight * 6));
        scorePanel.setMinimumSize(new Dimension((widthScore * 2 + widthText * 2 + widthLong + 50), rowHeight * 6));
        for (int i = 0; i < maxNumberOfRounds; i++) {
            scorePanel.add(getRow(i));
            centerPanel.add(scorePanel, BorderLayout.CENTER);
            add(centerPanel, BorderLayout.CENTER);
            repaint();
            revalidate();
        }
    }

    private void createButtons(List<Score> roundScores) {
        for (Score score : roundScores) {
            int[] playerOneScores = score.getRoundScoresPlayer1();
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
        for (Score score : roundScores) {
            int[] playerTwoScores = score.getRoundScoresPlayer2();
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
        int start = numberOfQuestions * index;
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
        } else {
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

    private List<JButton> getEmptyButtons() {
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
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.add(Box.createHorizontalStrut(10));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowHeight));
        row.setPreferredSize(new Dimension(0, rowHeight));
        row.setMinimumSize(new Dimension(0, rowHeight));

        row.setOpaque(true);
        row.setBackground(new Color(30, 144, 255));
        row.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 1));

        int printIndex = index + 1;
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
        roundCategory.setPreferredSize(new Dimension(widthLong + 20, rowCompHeight));
        roundCategory.setMaximumSize(new Dimension(widthLong + 20, rowCompHeight));
        roundCategory.setMinimumSize(new Dimension(widthLong + 20, rowCompHeight));
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

    private Color assignColorToButton(int i) {
        if (i == 1) {
            return Color.GREEN;
        } else if (i == 0) {
            return Color.RED;
        } else {
            return Color.LIGHT_GRAY;
        }
    }

    private JPanel createEndPanel(List<Score> roundScores) {
        JPanel resPanel = new JPanel();
        resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.X_AXIS));
        resPanel.setOpaque(false);
        resPanel.setBackground(new Color(30, 144, 255));
        resPanel.setPreferredSize(new Dimension(85, 85));
        resPanel.setMaximumSize(new Dimension(85, 85));
        resPanel.setMinimumSize(new Dimension(85, 85));
        JPanel scoreDisplay = finalResults(roundScores);
        User gameWinner = getWinner(roundScores);
        if (gameWinner != null) {
            resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.X_AXIS));
            resPanel.setOpaque(false);
            resPanel.setBackground(new Color(30, 144, 255));
            resPanel.setPreferredSize(new Dimension(85, 85));
//            resPanel.setMaximumSize(new Dimension(85, 85));
            resPanel.setMinimumSize(new Dimension(85, 85));

            Icon avatar = gameWinner.getAvatar();
            JPanel wrapperWinnerAvatar = new JPanel();
            wrapperWinnerAvatar.setBackground(new Color(30, 144, 255));
            JLabel winnerAvatar = new JLabel(avatar);
            winnerAvatar.setPreferredSize(new Dimension(avatar.getIconWidth(), avatar.getIconHeight()));
            winnerAvatar.setMinimumSize(new Dimension(avatar.getIconWidth(), avatar.getIconHeight()));
            winnerAvatar.setMaximumSize(new Dimension(avatar.getIconWidth(), avatar.getIconHeight()));
//            winnerAvatar.setPreferredSize(new Dimension(85, 85));
//            winnerAvatar.setMaximumSize(new Dimension(85, 85));
//            winnerAvatar.setMinimumSize(new Dimension(85, 85));
            winnerAvatar.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
            wrapperWinnerAvatar.add(winnerAvatar);
            JLabel winnerName = new JLabel(gameWinner.getUsername());
            winnerName.setFont(new Font("Montserrat", Font.BOLD, 24));
            winnerName.setHorizontalAlignment(0);
            winnerName.setForeground(Color.ORANGE);
            JPanel winnerPanel = new JPanel();
            winnerPanel.setBackground(new Color(30, 144, 255));
            winnerPanel.setLayout(new BorderLayout());
//            winnerPanel.setPreferredSize(new Dimension(75, 75));
//            winnerPanel.setMaximumSize(new Dimension(75, 75));
//            winnerPanel.setMinimumSize(new Dimension(75, 75));
            winnerPanel.add(wrapperWinnerAvatar, BorderLayout.CENTER);
            winnerPanel.add(winnerName, BorderLayout.SOUTH);

            resPanel.add(Box.createHorizontalGlue());
            resPanel.add(scoreDisplay);
            resPanel.add(Box.createHorizontalGlue());
            resPanel.add(winnerPanel);
            resPanel.add(Box.createHorizontalGlue());

        }
        else {
            JLabel draw = new JLabel("\nNone! Or Both!");
            draw.setFont(new Font("Montserrat", Font.BOLD, 34));
            draw.setForeground(Color.ORANGE);
            resPanel.add(Box.createHorizontalGlue());
            resPanel.add(scoreDisplay);
            resPanel.add(Box.createHorizontalGlue());
            resPanel.add(draw);
            resPanel.add(Box.createHorizontalGlue());
        }

        JButton backToMenu = new JButton("Return to menu");
        backToMenu.setFont(new Font("Montserrat", Font.BOLD, 12));
        backToMenu.setBackground((new Color(30, 144, 200)));
        backToMenu.setForeground(Color.ORANGE);
        backToMenu.setPreferredSize(new Dimension(120, 20));
        backToMenu.setMaximumSize(new Dimension(120, 20));
        backToMenu.setMinimumSize(new Dimension(120, 20));
        backToMenu.addActionListener(e -> {
            MenuPanel menuPanel = new MenuPanel(user, client, client.getMainframe());
            client.getMainframe().setContentPane(menuPanel);
            client.getMainframe().revalidate();
            client.getMainframe().repaint();
        });

        JPanel endPanel = new JPanel();
        endPanel.setLayout(new BorderLayout());
        endPanel.setBackground(new Color(30, 144, 255));
        endPanel.setOpaque(false);
        endPanel.add(resPanel, BorderLayout.CENTER);
        endPanel.add(backToMenu, BorderLayout.SOUTH);


        return endPanel;
    }

    private int getFinalPoints(List<Score> roundScores, User player) {
        int finalPoints = 0;
        for (Score score : roundScores) {
            if (player.getUsername().equalsIgnoreCase(score.getPlayer1().getUsername())) {
                int[] roundScore = score.getRoundScoresPlayer1();
                for (int i = 0; i < roundScore.length; i++) {
                    finalPoints += roundScore[i];
                }
            } else if (player.getUsername().equalsIgnoreCase(score.getPlayer2().getUsername())) {
                int[] roundScore = score.getRoundScoresPlayer2();
                for (int i = 0; i < roundScore.length; i++) {
                    finalPoints += roundScore[i];
                }
            }
        }
        return finalPoints;
    }
    private User getWinner (List<Score> roundScores){
        User gameWinner = null;
        int pointsPlayerOne = getFinalPoints(roundScores, playerOne);
        int pointsPlayerTwo = getFinalPoints(roundScores, playerTwo);
        if (pointsPlayerOne > pointsPlayerTwo){
            gameWinner = playerOne;
        }
        else if (pointsPlayerOne < pointsPlayerTwo){
            gameWinner = playerTwo;
        }
        else {
            return null;
        }
        System.out.println("in getWinner, winner is: " + gameWinner.getUsername());
        return gameWinner;
    }
    private JPanel finalResults (List<Score> roundScores){
        JPanel results = new JPanel();
        results.setBackground(new Color(30, 144, 255));
//        results.setMinimumSize(new Dimension(400, 200));
//        results.setMaximumSize(new Dimension(400, 200));
//        results.setPreferredSize(new Dimension(400, 200));
        results.setLayout(new GridLayout(2, 1));
        int pointsPlayerOne = getFinalPoints(roundScores, playerOne);
        int pointsPlayerTwo = getFinalPoints(roundScores, playerTwo);

        JLabel scoreHeader = new JLabel("Final result:      ");
        scoreHeader.setFont(new Font("Montserrat", Font.BOLD, 18));
        scoreHeader.setForeground(Color.ORANGE);
        JLabel player1 = new JLabel(playerOne.getUsername());
        player1.setFont(new Font("Montserrat", Font.BOLD, 18));
        player1.setForeground(Color.ORANGE);
        JLabel player2 = new JLabel(playerTwo.getUsername());
        player2.setFont(new Font("Montserrat", Font.BOLD, 18));
        player2.setForeground(Color.ORANGE);
        JLabel points = new JLabel("   " + pointsPlayerOne + "   -   " + pointsPlayerTwo + "   ");
        points.setForeground(Color.ORANGE);
        points.setFont(new Font("Montserrat", Font.BOLD, 24));
        JPanel finalScore = new JPanel();
        finalScore.setLayout(new BoxLayout(finalScore, BoxLayout.X_AXIS));
        finalScore.add(scoreHeader);
        finalScore.add(player1);
        finalScore.add(points);
        finalScore.add(player2);
        finalScore.setOpaque(false);
        finalScore.setBackground(new Color(30, 144, 255));

        JLabel winnerIs = new JLabel("The winner is: ");
        winnerIs.setHorizontalAlignment(SwingConstants.LEFT);
        winnerIs.setOpaque(false);
        winnerIs.setFont(new Font("Montserrat", Font.BOLD, 28));
        winnerIs.setForeground(Color.ORANGE);

        results.add(finalScore);
        results.add(winnerIs);

        return results;




    }
}





