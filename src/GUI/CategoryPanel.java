package GUI;

import Client.ClientBase;
import Quizgame.shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CategoryPanel extends JPanel {

    private JButton categoryButton1;
    private JButton categoryButton2;
    private JButton categoryButton3;
    private JButton categoryButton4;
    private JButton categoryButton5;
    private JButton categoryButton6;
    private JButton categoryButton7;
    private JButton categoryButton8;

    private final int sizeofCategoryButton = 100;

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
        middleCategoryPanel.setLayout(new GridLayout(2,4));
        createCategoryButtons(middleCategoryPanel);

        add(middleCategoryPanel,BorderLayout.CENTER);

        JPanel buttonPanelBottom = new JPanel();
        buttonPanelBottom.setLayout(new GridLayout(1,2));
        JButton playButton = new JButton("Play");
        JButton backButton = new JButton("Back");

        buttonPanelBottom.add(playButton);
        buttonPanelBottom.add(backButton);
        add(buttonPanelBottom,BorderLayout.SOUTH);

    }
    private void createCategoryButtons(JPanel middleCategoryPanel) {
        categoryButton1 = createCategoryButton("resources/CategoriesPictures/CategoryAnimals.jpg", changePreviewPicture);
        categoryButton2 = createCategoryButton("resources/CategoriesPictures/CategoryBiology.jpg", changePreviewPicture);
        categoryButton3 = createCategoryButton("resources/CategoriesPictures/CategoryGames.jpg", changePreviewPicture);
        categoryButton4 = createCategoryButton("resources/CategoriesPictures/CategoryGeography.jpg", changePreviewPicture);
        categoryButton5 = createCategoryButton("resources/CategoriesPictures/CategoryHistory.jpg", changePreviewPicture);
        categoryButton6 = createCategoryButton("resources/CategoriesPictures/CategoryMusic.jpg", changePreviewPicture);
        categoryButton7 = createCategoryButton("resources/CategoriesPictures/CategoryScience.webp", changePreviewPicture);
        categoryButton8 = createCategoryButton("resources/CategoriesPictures/CategorySport.jpg", changePreviewPicture);

        middleCategoryPanel.add(categoryButton1);
        middleCategoryPanel.add(categoryButton2);
        middleCategoryPanel.add(categoryButton3);
        middleCategoryPanel.add(categoryButton4);
        middleCategoryPanel.add(categoryButton5);
        middleCategoryPanel.add(categoryButton6);
        middleCategoryPanel.add(categoryButton7);
        middleCategoryPanel.add(categoryButton8);
    }


    private JButton createCategoryButton(String filename, ActionListener changePreviewPicture ) {
        JButton categoryButton = new JButton();
        ImageIcon tempAvatar = new ImageIcon(filename);
        Image scaledTempAvatar = tempAvatar.getImage().getScaledInstance(sizeofCategoryButton, sizeofCategoryButton, Image.SCALE_SMOOTH);
        categoryButton.setBorderPainted(false);
        categoryButton.setContentAreaFilled(false);
        categoryButton.setFocusPainted(false);
        categoryButton.setOpaque(false);
        categoryButton.setBorder(null);
        categoryButton.setMargin(new Insets(0, 0, 0, 0));
        categoryButton.setIcon(new ImageIcon(scaledTempAvatar));
//        categoryButton.addActionListener(changePreviewPicture);
        return categoryButton;
    }

}

