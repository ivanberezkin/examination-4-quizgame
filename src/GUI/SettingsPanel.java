package GUI;

import Client.ClientBase;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JFrame frame;
    private MenuPanel previousPanel;

    private JPanel topPanel;

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

    private User user;
    private ClientBase client;

    public SettingsPanel(JFrame frame, MenuPanel previousPanel, User user, ClientBase client) {
        this.frame = frame;
        this.previousPanel = previousPanel;
        this.user = user;
        this.client = client;

        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(20, 5, 10, 5));
        JLabel currentAvatarLabel =  new JLabel("Current Avatar");
        currentAvatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel currentAvatarPreview = new JLabel(user.getAvatar());
        currentAvatarPreview.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(currentAvatarLabel, BorderLayout.NORTH);
        topPanel.add(currentAvatarPreview, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        ActionListener changePreviewPicture = e -> {
            JButton clickedButton =  (JButton) e.getSource();
            currentAvatarPreview.setIcon(clickedButton.getIcon());
            currentAvatarPreview.updateUI();
        };

        JPanel avatarPanel = new JPanel();
        createAvatarButtons(avatarPanel, changePreviewPicture);
        avatarPanel.setLayout(new GridLayout(2, 5));
        avatarPanel.setOpaque(false);
        add(avatarPanel, BorderLayout.CENTER);



        buttonPanel = new JPanel();
        backButton = new JButton("Back");
        saveButton = new JButton("Save");
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(5, 30, 5, 30));
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            goBackToMenu(previousPanel);
        });

        saveButton.addActionListener(e -> {
            Icon icon = currentAvatarPreview.getIcon();
            user.setAvatar(icon);
            previousPanel.updateAvatarLabel(icon);
            client.sendMessage(new Message(MessageType.SETTINGS_AVATAR_CHANGED,user));
        });

    }


    private JButton createAvatarButton(String filename, ActionListener changePreviewPicture ) {
        JButton avatarButton = new JButton();
        ImageIcon tempAvatar = new ImageIcon(filename);
        Image scaledTempAvatar = tempAvatar.getImage().getScaledInstance(sizeOfAvatar, sizeOfAvatar, Image.SCALE_SMOOTH);
        avatarButton.setBorderPainted(false);
        avatarButton.setContentAreaFilled(false);
        avatarButton.setFocusPainted(false);
        avatarButton.setOpaque(false);
        avatarButton.setBorder(null);
        avatarButton.setMargin(new Insets(0, 0, 0, 0));
        avatarButton.setIcon(new ImageIcon(scaledTempAvatar));
        avatarButton.addActionListener(changePreviewPicture);
        return avatarButton;
    }

    private void createAvatarButtons(JPanel avatarPanel, ActionListener changePreviewPicture) {
        avatar1Button = createAvatarButton("resources/Avatars/avatar1.png", changePreviewPicture);
        avatar2Button = createAvatarButton("resources/Avatars/avatar2.jpg",changePreviewPicture);
        avatar3Button = createAvatarButton("resources/Avatars/avatar3.png",changePreviewPicture);
        avatar4Button = createAvatarButton("resources/Avatars/avatar4.png", changePreviewPicture);
        avatar5Button = createAvatarButton("resources/Avatars/avatar5.png", changePreviewPicture);
        avatar6Button = createAvatarButton("resources/Avatars/avatar6.png", changePreviewPicture);
        avatar7Button = createAvatarButton("resources/Avatars/avatar7.png", changePreviewPicture);
        avatar8Button = createAvatarButton("resources/Avatars/avatar8.png", changePreviewPicture);
        avatar9Button = createAvatarButton("resources/Avatars/avatar9.png", changePreviewPicture);
        avatar10Button = createAvatarButton("resources/Avatars/avatar10.png", changePreviewPicture);
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
