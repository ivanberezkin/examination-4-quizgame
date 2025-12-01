package GUI;

import Client.ClientBase;
import Client.ClientProtocol;
import Database.*;
import Quizgame.shared.Answer;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.IO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GamePanel extends JPanel {


    //Temporary label, byts mot något annat ev hur många frågor är kvar och ifall dom är rätt/fel.
    
    JPanel northPanel = new JPanel();
//    JLabel welcomeLabel = new JLabel("Welcome to the game");
    ImageIcon giveUpFlagIcon = new ImageIcon("resources/giveUpFlag.jpg");
    Image scaledImageFlag = giveUpFlagIcon.getImage().getScaledInstance(100,75,Image.SCALE_SMOOTH);
    ImageIcon scaledGiveUpFlagIcon = new ImageIcon(scaledImageFlag);

    JButton giveUpButton = new JButton(scaledGiveUpFlagIcon);

    JPanel questionPanel = new JPanel();
    JLabel questionCategoryLabel = new JLabel("Test Category");
    JLabel questionArea = new JLabel("");


    JPanel answerButtonsPanel = new JPanel();
    JButton answerA = new JButton("A");
    JButton answerB = new JButton("B");
    JButton answerC = new JButton("C");
    JButton answerD = new JButton("D");
    String correctAnswer;
    ArrayList<Question> questionsForRound;
    private ClientBase clientBase;
    private Question question;
    private User user;
    private JFrame frame;

    public GamePanel(ClientBase client, Question question, User user, JFrame frame) {
        System.out.println("GamePanel Constructor was reached");
        this.clientBase = client;
        this.question = question;
        this.questionsForRound = questionsForRound;
        this.user = user;
        this.frame = frame;

        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        //NORTH
        northPanel.setBackground(Color.CYAN);
        giveUpButton.setBorderPainted(false);
        giveUpButton.setContentAreaFilled(false);
        giveUpButton.setFocusPainted(false);
        giveUpButton.setOpaque(false);
        giveUpButton.setBorder(null);
        giveUpButton.setMargin(new Insets(0,0,0,0));


        northPanel.setLayout(new BorderLayout());
        northPanel.add(giveUpButton, BorderLayout.WEST);

        giveUpButton.addActionListener(e -> {
            IO.println("GAMEPANEL:" + user.getUsername() + " pressed give up button.");
            client.sendMessage(new Message(MessageType.GIVE_UP, user));
        });

//        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
//        northPanel.add(welcomeLabel);
        add(northPanel, BorderLayout.NORTH);

        //CENTER
        add(questionPanel, BorderLayout.CENTER);
        questionPanel.setBackground(Color.CYAN);
        questionPanel.add(questionArea);

        add(answerButtonsPanel, BorderLayout.SOUTH);
        answerButtonsPanel.setLayout(new GridLayout(2, 2));
        answerButtonsPanel.add(answerA);
        answerButtonsPanel.add(answerB);
        answerButtonsPanel.add(answerC);
        answerButtonsPanel.add(answerD);
        answerA.setBackground(Color.LIGHT_GRAY);
        answerB.setBackground(Color.LIGHT_GRAY);
        answerC.setBackground(Color.LIGHT_GRAY);
        answerD.setBackground(Color.LIGHT_GRAY);
        answerA.setFocusable(false);
        answerB.setFocusable(false);
        answerC.setFocusable(false);
        answerD.setFocusable(false);
        newQuestion(question);

        ActionListener answerButtonListener = e -> {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals(correctAnswer)) {
                clickedButton.setBackground(Color.GREEN);
                JOptionPane.showMessageDialog(GamePanel.this, "You guessed the correct answer.");
                clickedButton.setBackground(Color.LIGHT_GRAY);
                //TODO lägga till logik för antal rundor och sedan starta ny fråga

                client.sendMessage(new Message(MessageType.ANSWER, new Answer(user, question, nextQuestion())));

                //TODO lägg till någon snygg väntar på motståndare.
                answerButtonsPanel.removeAll();

            } else {
                clickedButton.setBackground(Color.RED);
                JOptionPane.showMessageDialog(GamePanel.this, "You guessed the incorrect answer" +
                        "\n Correct Answer is: " + correctAnswer);
                clickedButton.setBackground(Color.LIGHT_GRAY);
                client.sendMessage(new Message(MessageType.ANSWER, new Answer(user, question, nextQuestion())));
                //TODO lägg till någon snygg väntar på motståndare.
                answerButtonsPanel.removeAll();
            }
        };

        answerA.addActionListener(answerButtonListener);
        answerB.addActionListener(answerButtonListener);
        answerC.addActionListener(answerButtonListener);
        answerD.addActionListener(answerButtonListener);


    }


    private void questionAreaSetText(String question, String category) {
        final String startOfHTML = "<html><div style='text-align: center; padding: 20px;'>";
        final String endOfHTML = "</div></html>";
        String categoryText = "<span style = 'color: red;'" + category + "'>" + category + "</span><br><br>";

        questionArea.setText(startOfHTML + categoryText + question + endOfHTML);
        questionArea.setHorizontalAlignment(JLabel.CENTER);
        questionArea.updateUI();
    }

    private boolean nextQuestion() {
//        if (questionsForRound.size() == 0) {
//            IO.println("There are no questions to play.");
//        } else {
            Question temp = question;

            return newQuestion(temp);
//        }
    }

    private boolean newQuestion(Question newQuestion) {
//        Question newQuestion = db.getNewQuestion();
        List<AnswerOption> answerOptions = newQuestion.getAnswerOptions();
        Collections.shuffle(answerOptions);

        questionAreaSetText(newQuestion.getPrompt(), newQuestion.getCategory());

        answerA.setText(answerOptions.getFirst().getText());
        answerB.setText(answerOptions.getLast().getText());
        answerC.setText(answerOptions.get(1).getText());
        answerD.setText(answerOptions.get(2).getText());

        for (AnswerOption option : answerOptions) {
            if (option.getCorrect()) {
                correctAnswer = option.getText();
                return true;
            }
        }
        return false;
    }
}

