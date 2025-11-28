package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private JFrame frame;
    private JPanel previousPanel;

    private JPanel buttonPanel;
    private JButton backButton;
    private JButton saveButton;

    private final int sizeOfAvatar = 75;
    private JButton avatar1Button;
    private JButton avatar2Button;
    private JButton avatar3Button;
    private JButton avatar4Button;
    private JButton avatar5Button;
    private JButton avatar6Button;
    private JButton avatar7Button;
    private JButton avatar8Button;
    private JButton avatar9Button;
    private JButton avatar10Button;

    public SettingsPanel(JFrame frame, JPanel previousPanel) {
        this.frame = frame;
        this.previousPanel = previousPanel;

        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));
        JPanel avatarPanel = new JPanel();

        createAvatarButtons(avatarPanel);
        avatarPanel.setLayout(new GridLayout(2,5));
        avatarPanel.setOpaque(false);
        add(avatarPanel, BorderLayout.CENTER);


        buttonPanel = new JPanel();
        backButton = new JButton("Back");
        saveButton = new JButton("Save");

        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1,2,10,0));
        buttonPanel.setBorder(new EmptyBorder(5, 30, 5, 30));
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);


        backButton.addActionListener(e -> {
            goBackToMenu(previousPanel);
        });


        saveButton.addActionListener(e -> {

        });

        add(buttonPanel, BorderLayout.SOUTH);

    }



    private JButton createAvatarButton(int size, String filename){
        JButton avatarButton = new JButton();
        ImageIcon tempAvatar = new ImageIcon(filename);
        Image scaledTempAvatar = tempAvatar.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH);
        avatarButton.setBorderPainted(false);
        avatarButton.setContentAreaFilled(false);
        avatarButton.setFocusPainted(false);
        avatarButton.setOpaque(false);
        avatarButton.setBorder(null);
        avatarButton.setMargin(new Insets(0,0,0,0));
        avatarButton.setIcon(new ImageIcon(scaledTempAvatar));
        return avatarButton;
    }
    private void createAvatarButtons(JPanel avatarPanel){
        avatar1Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar1.png");
        avatar2Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar2.jpg");
        avatar3Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar3.png");
        avatar4Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar4.png");
        avatar5Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar5.png");
        avatar6Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar6.png");
        avatar7Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar7.png");
        avatar8Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar8.png");
        avatar9Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar9.png");
        avatar10Button = createAvatarButton(sizeOfAvatar, "resources/Avatars/avatar10.png");
        avatarPanel.add(avatar1Button);
        avatarPanel.add(avatar2Button);
        avatarPanel.add(avatar3Button);
        avatarPanel.add(avatar4Button);
        avatarPanel.add(avatar5Button);
        avatarPanel.add(avatar6Button);
        avatarPanel.add(avatar7Button);
        avatarPanel.add(avatar8Button);
        avatarPanel.add(avatar9Button);
        avatarPanel.add(avatar10Button);
    }

    private void goBackToMenu(JPanel previousPanel) {
        frame.setContentPane(previousPanel);
        frame.revalidate();
        frame.repaint();
    }


}
