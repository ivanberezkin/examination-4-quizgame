package GUI;

import Database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GamePanel extends JPanel {


    //Temporary label, byts mot något annat ev hur många frågor är kvar och ifall dom är rätt/fel.
    JLabel temporaryLabel = new JLabel("Welcome to the game");
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

    public GamePanel(ArrayList<Question> questionsForRound) {
        this.questionsForRound = questionsForRound;

        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        temporaryLabel.setHorizontalAlignment(JLabel.CENTER);
        add(temporaryLabel, BorderLayout.NORTH);

        add(questionPanel, BorderLayout.CENTER);
        questionPanel.setBackground(Color.CYAN);

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
        for(Question question : questionsForRound){
            correctAnswer = newQuestion(question);
        }

        ActionListener answerButtonListener = e -> {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals(correctAnswer)) {
                clickedButton.setBackground(Color.GREEN);
                JOptionPane.showMessageDialog(GamePanel.this, "You guessed the correct answer.");
                clickedButton.setBackground(Color.LIGHT_GRAY);
                //TODO lägga till logik för antal rundor och sedan starta ny fråga
//                correctAnswer = newQuestion();
            } else{
                clickedButton.setBackground(Color.RED);
                JOptionPane.showMessageDialog(GamePanel.this, "You guessed the incorrect answer" +
                        "\n Correct Answer is: " +correctAnswer);
                clickedButton.setBackground(Color.LIGHT_GRAY);
//                correctAnswer = newQuestion();
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
    }


    private String newQuestion(Question newQuestion) {
//        Question newQuestion = db.getNewQuestion();
        List<AnswerOption> answerOptions = newQuestion.getAnswerOptions();
        Collections.shuffle(answerOptions);

        questionAreaSetText(newQuestion.getPrompt(), newQuestion.getCategory());

        answerA.setText(answerOptions.getFirst().getText());
        answerB.setText(answerOptions.getLast().getText());
        answerC.setText(answerOptions.get(1).getText());
        answerD.setText(answerOptions.get(2).getText());

        for(AnswerOption option : answerOptions) {
            if(option.getCorrect())
                return option.getText();
        }
        return null;
    }


}
