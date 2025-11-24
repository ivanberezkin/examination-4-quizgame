package GUI;

import javax.swing.*;

public class MenuPanel extends JPanel {

    JPanel menuPanel = new JPanel();
    JLabel welcomeLabel = new JLabel();

    JButton testButtonToGamePanel = new JButton();

    public MenuPanel(String username) {

        welcomeLabel.setText("Welcome " + username);
        add(welcomeLabel);



        //Test knapp bara, för att kunna gå vidare till Game Panel
        add(testButtonToGamePanel);
        testButtonToGamePanel.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.revalidate();
            frame.repaint();
        });

        //TODO lämnar till Raymond

    }


}
