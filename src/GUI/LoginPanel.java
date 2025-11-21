package GUI;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JFrame {

    JLabel welcomeLabel = new JLabel("Welcome to Quizgame");

    JPanel backgroudPanel = new JPanel();
    ImageIcon startImage = new ImageIcon("resources/QuizStartGame.jpg");
    JLabel backgroundImage = new JLabel(startImage);

    JPanel buttonPanel = new JPanel();
    JButton Login = new JButton("Login");
    JButton Exit = new JButton("Exit");

    JLabel usernameLabel = new JLabel("Username: ");
    JTextField usernameInput = new JTextField(20);

    public LoginPanel(){

        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());

        backgroudPanel.setLayout(new BorderLayout());
        backgroundImage.setLayout(new BorderLayout());
        backgroudPanel.add(backgroundImage,BorderLayout.CENTER);
        backgroundImage.add(welcomeLabel,BorderLayout.NORTH);

        welcomeLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(Color.BLUE);

        backgroundImage.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(usernameLabel);
        buttonPanel.add(usernameInput);
        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.add(Login);
        buttonPanel.add(Exit);

        add(backgroudPanel);




        pack();

    }


}
