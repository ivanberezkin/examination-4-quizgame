package GUI;

import Client.ClientBase;
import Database.Question;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
import Quizgame.shared.UserAndCategory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private ArrayList<JButton> listOfChosenCategories = new ArrayList<>();
    private ArrayList<JButton> listOfAvailableCategories = new ArrayList<>();
    private JPanel categoriesToChooseFrom;
    private JPanel chosenCategoriesButtons;
    private MenuPanel previousPanel;
    private final ActionListener moveBetweenChosenAndAvailable = e -> {
        JButton clickedButton = (JButton) e.getSource();
        String categoryName = (String) clickedButton.getClientProperty("categoryName");


        if(listOfAvailableCategories.contains(clickedButton) && listOfChosenCategories.isEmpty()) {
            listOfAvailableCategories.remove(clickedButton);
            listOfChosenCategories.add(clickedButton);
        } else if(listOfChosenCategories.contains(clickedButton)) {
            listOfChosenCategories.remove(clickedButton);
            listOfAvailableCategories.add(clickedButton);
        }

        printCategoryButtons();

    };

    public CategoryPanel(User user, JFrame frame, ClientBase client, MenuPanel previousPanel) {
        this.user = user;
        this.frame = frame;
        this.client = client;
        this.previousPanel = previousPanel;
        setName("CategoryPanel");
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));


        //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        JLabel chooseCategoryLabel = new JLabel("Choose Category");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 32));
        topPanel.add(chooseCategoryLabel);
        add(topPanel, BorderLayout.NORTH);


        // MID panel
        JPanel middleCategoryPanel = new JPanel();
        middleCategoryPanel.setLayout(new BorderLayout());

        JPanel chosenCategories = new JPanel();
        chosenCategories.setLayout(new BorderLayout());
        JLabel chosenCategoriesLabel = new JLabel("Your chosen categories: ");
        chosenCategoriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chosenCategoriesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chosenCategories.add(chosenCategoriesLabel, BorderLayout.NORTH);
        middleCategoryPanel.add(chosenCategories, BorderLayout.SOUTH);

        chosenCategoriesButtons = new JPanel();
        chosenCategoriesButtons.setLayout(new GridLayout(1, 3, 10, 10));
        categoriesToChooseFrom = new JPanel();
        categoriesToChooseFrom.setLayout(new GridLayout(2, 4));

        listOfCategoriesFilename = readFromCategoriesFilenamse("resources/CategoriesPictures/categoriesFilenames");
        listOfAvailableCategories = createCategoryButtons(listOfCategoriesFilename);

        printCategoryButtons();
        chosenCategories.add(chosenCategoriesButtons, BorderLayout.CENTER);
        middleCategoryPanel.add(categoriesToChooseFrom, BorderLayout.CENTER);
        add(middleCategoryPanel, BorderLayout.CENTER);

        //Bottom Panel
        JPanel buttonPanelBottom = new JPanel();
        buttonPanelBottom.setLayout(new GridLayout(1, 2, 30, 20));
        buttonPanelBottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton playButton = new JButton("Play");
        JButton backButton = new JButton("Back");

        playButton.addActionListener(e -> {
            Question.Category categoryToSend = null;
            if(!listOfChosenCategories.isEmpty()){
                for(JButton button : listOfChosenCategories){
                    String category = (String) button.getClientProperty("categoryName");
                    for (Question.Category c : Question.getAllCategories()){
                        if (category.equalsIgnoreCase(c.qCat)){
                            categoryToSend = c;
                        }
                    }
                }
                Quizgame.shared.UserAndCategory startingParameters = new UserAndCategory(user, categoryToSend);
                System.out.println(" - - - In CategoryPanel, message is sent");
                client.sendMessage(new Message(MessageType.CHOOSING_CATEGORIES, startingParameters));
            } else{
                JOptionPane.showMessageDialog(this, "Please choose atleast 1 category.");
            }
        });

        backButton.addActionListener(e -> {
           frame.setContentPane(previousPanel);
           frame.revalidate();
           frame.repaint();
        });

        buttonPanelBottom.add(playButton);
        buttonPanelBottom.add(backButton);
        add(buttonPanelBottom, BorderLayout.SOUTH);

    }

    private void revalidateAndRepaintButtonsPanel(){
        chosenCategoriesButtons.revalidate();
        chosenCategoriesButtons.repaint();
        categoriesToChooseFrom.repaint();
        categoriesToChooseFrom.revalidate();

    }

    private void printCategoryButtons() {
        chosenCategoriesButtons.removeAll();
        for (JButton button : listOfChosenCategories) {
            chosenCategoriesButtons.add(button);
        }
        categoriesToChooseFrom.removeAll();
        for(JButton b :  listOfAvailableCategories) {
            categoriesToChooseFrom.add(b);
        }
        revalidateAndRepaintButtonsPanel();
    }

    private ArrayList<JButton> createCategoryButtons(ArrayList<String> listOfCategoriesFilename) {
        String categoryName;
        for (String s : listOfCategoriesFilename) {
            JButton categoryButton = createCategoryButton(s);
            categoryName = s.substring(s.indexOf("Category") + 8, s.indexOf("."));
            categoryButton.putClientProperty("categoryName", categoryName);
            listOfAvailableCategories.add(categoryButton);
        }
        return listOfAvailableCategories;
    }

    private ArrayList<String> readFromCategoriesFilenamse(String filename) {
        ArrayList<String> listOfCategoriesFilename = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                listOfCategoriesFilename.add(filePathToCategoryFiles + line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfCategoriesFilename;
    }

    private JButton createCategoryButton(String filename) {
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
        categoryButton.addActionListener(moveBetweenChosenAndAvailable);
        return categoryButton;
    }

}

