package GUI;

import Client.ClientBase;
import Client.ClientStart;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

    JLabel passwordLabel = new JLabel("Password: ");
    JPasswordField passwordInput = new JPasswordField(20);

    public LoginPanel() {

        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());

        backgroudPanel.setLayout(new BorderLayout());
        backgroundImage.setLayout(new BorderLayout());
        backgroudPanel.add(backgroundImage, BorderLayout.CENTER);
        backgroundImage.add(welcomeLabel, BorderLayout.NORTH);

        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(Color.BLUE);

        backgroundImage.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.add(usernameLabel);
        buttonPanel.add(usernameInput);
        buttonPanel.add(passwordLabel);
        buttonPanel.add(passwordInput);
        buttonPanel.add(Login);
        buttonPanel.add(Exit);

        add(backgroudPanel);

        Login.addActionListener(e -> {
            String inputUsername = usernameInput.getText();
            String inputPassword = passwordInput.getText();

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPanel.this, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User(inputUsername, inputPassword);
                ClientStart cs = new ClientStart(this);
                ClientBase client = cs.getClient();
                client.sendMessage(new Message(MessageType.LOGIN_REQUEST, user));
            }
        });

        Exit.addActionListener(e -> {
            closeProgram();
        });

        WindowListener windowListener = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeProgram();
            }
        };
        addWindowListener(windowListener);

        pack();

    }

    public void closeProgram() {
        IO.println("Quizgame.shared.User closed the program");
        System.exit(0);
    }

}
