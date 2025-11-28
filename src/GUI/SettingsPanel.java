package GUI;

import javax.swing.*;

public class SettingsPanel extends JPanel {
    private JFrame frame;
    private JPanel previousPanel;
    private JButton backButton;

    public SettingsPanel(JFrame frame, JPanel previousPanel) {
        this.frame = frame;
        this.previousPanel = previousPanel;

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            goBackToMenu(previousPanel);
        });

        add(backButton);

    }

    private void goBackToMenu(JPanel previousPanel) {
        frame.setContentPane(previousPanel);
        frame.revalidate();
        frame.repaint();
    }


}
