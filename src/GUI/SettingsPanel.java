package GUI;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private JFrame frame;
    private JPanel previousPanel;
    private JButton backButton;

    public SettingsPanel(JFrame frame, JPanel previousPanel) {
        this.frame = frame;
        this.previousPanel = previousPanel;

        setLayout(new GridLayout(3,10));
        JPanel avatarPanel = new JPanel();

        //Scaled avatars
        ImageIcon avatar1 = scaleAvatarPicture("resources/avatarSmokey.jpg");
        JButton avatar1Button = new JButton(avatar1);

        avatarPanel.add(avatar1Button);
        add(avatarPanel);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            goBackToMenu(previousPanel);
        });

        add(backButton);

    }

    private ImageIcon scaleAvatarPicture(String filename){
        ImageIcon tempAvatar = new ImageIcon(filename);
        Image scaledTempAvatar = tempAvatar.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
        return new ImageIcon(scaledTempAvatar);
    }

    private void goBackToMenu(JPanel previousPanel) {
        frame.setContentPane(previousPanel);
        frame.revalidate();
        frame.repaint();
    }


}
