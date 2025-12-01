package GUI;

import Client.ClientBase;
import Quizgame.shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CategoryPanel extends JPanel {


    private final int sizeofCategoryButton = 100;
    private String filePathToCategoryFiles = "resources/CategoriesPictures/";

    private JFrame frame;
    private User user;
    private ClientBase client;
    private ArrayList<String> listOfCategoriesFilename;

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

        listOfCategoriesFilename = readFromCategoriesFilenamse("resources/CategoriesPictures/categoriesFilenames");
        createCategoryButtons(middleCategoryPanel, listOfCategoriesFilename);
        add(middleCategoryPanel,BorderLayout.CENTER);

        JPanel buttonPanelBottom = new JPanel();
        buttonPanelBottom.setLayout(new GridLayout(1,2));
        JButton playButton = new JButton("Play");
        JButton backButton = new JButton("Back");

        buttonPanelBottom.add(playButton);
        buttonPanelBottom.add(backButton);
        add(buttonPanelBottom,BorderLayout.SOUTH);
    }


    private void createCategoryButtons(JPanel middleCategoryPanel, ArrayList<String> listOfCategoriesFilename) {
        for(String s: listOfCategoriesFilename){
            JButton categoryButton = createCategoryButton(s);
            middleCategoryPanel.add(categoryButton);
        }
    }

    private ArrayList<String> readFromCategoriesFilenamse(String filename){
        ArrayList<String> listOfCategoriesFilename = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while((line = br.readLine()) != null){
                listOfCategoriesFilename.add(filePathToCategoryFiles + line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfCategoriesFilename;
    }

    private JButton createCategoryButton(String filename  ) {
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

