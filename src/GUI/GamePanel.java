package GUI;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class GamePanel extends JPanel {

    //Temporary label, byts mot något annat ev hur många frågor är kvar och ifall dom är rätt/fel.
    JLabel temporaryLabel = new JLabel("Welcome to the game");

    JPanel questionPanel = new JPanel();
    JLabel questionCategoryLabel = new JLabel("Test Category");
//    JTextArea questionArea = new JTextArea(8, 30);
    JLabel questionArea = new JLabel("");


    JPanel answerButtonsPanel = new JPanel();
    JButton answerA = new JButton("A");
    JButton answerB = new JButton("B");
    JButton answerC = new JButton("C");
    JButton answerD = new JButton("D");


    GamePanel() {

        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        temporaryLabel.setHorizontalAlignment(JLabel.CENTER);
        add(temporaryLabel, BorderLayout.NORTH);

        add(questionPanel, BorderLayout.CENTER);
        questionPanel.setBackground(Color.CYAN);

        //TODO test bara, parametrar ska inte vara hårdkodade
        questionAreaSetText("What is the name of our course?", "Java");
        questionPanel.add(questionArea);

        add(answerButtonsPanel, BorderLayout.SOUTH);
        answerButtonsPanel.setLayout(new GridLayout(2, 2));
        answerButtonsPanel.add(answerA);
        answerButtonsPanel.add(answerB);
        answerButtonsPanel.add(answerC);
        answerButtonsPanel.add(answerD);
    }

    private void questionAreaSetText(String question, String category) {
        final String startOfHTML = "<html><div style='text-align: center; padding: 20px;'>";
        final String endOfHTML = "</div></html>";
        String categoryText = "<span style = 'color: red;'" + category + "'>" + category + "</span><br><br>";

        questionArea.setText(startOfHTML + categoryText + question + endOfHTML);
        questionArea.setHorizontalAlignment(JLabel.CENTER);


    }

}
