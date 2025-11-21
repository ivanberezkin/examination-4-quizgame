package GUI;

import javax.swing.*;

public class MenuPanel extends JPanel {

    JPanel menuPanel = new JPanel();
    JLabel welcomeLabel = new JLabel();

    public MenuPanel(String username) {

        welcomeLabel.setText("Welcome " + username);
        add(welcomeLabel);



    }


}
