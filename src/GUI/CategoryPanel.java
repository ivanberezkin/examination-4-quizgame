package GUI;

import Client.ClientBase;
import Quizgame.shared.User;

import javax.swing.*;
import java.awt.*;

public class CategoryPanel extends JPanel {

    private JFrame frame;
    private User user;
    private ClientBase client;

    CategoryPanel(User user, JFrame frame, ClientBase client ) {
        this.user = user;
        this.frame = frame;
        this.client = client;

        setName("CategoryPanel");
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        JLabel chooseCategoryLabel = new JLabel("Choose Category");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 32));
        topPanel.add(chooseCategoryLabel);
        add(topPanel,BorderLayout.NORTH);

        JPanel middleCategoryPanel = new JPanel();
        middleCategoryPanel.setLayout(new GridLayout(2,5));



    }


}

