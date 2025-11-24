package GUI;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    //Temporary label, byts mot något annat ev hur många frågor är kvar och ifall dom är rätt/fel.
    JLabel temporaryLabel = new JLabel("Welcome to the game");

    JTextArea questionArea = new JTextArea(20, 20);

    JPanel answerButtonsPanel = new JPanel();
    JButton answerA = new JButton("A");
    JButton answerB = new JButton("B");
    JButton answerC = new JButton("C");
    JButton answerD = new JButton("D");

    GamePanel() {

        setBackground(Color.BLUE);
        setLayout(new GridLayout(3, 1));

        add(temporaryLabel);
        add(questionArea);
        add(answerButtonsPanel);

        answerButtonsPanel.setLayout(new GridLayout(2, 2));
        answerButtonsPanel.add(answerA);
        answerButtonsPanel.add(answerB);
        answerButtonsPanel.add(answerC);
        answerButtonsPanel.add(answerD);


    }

}
