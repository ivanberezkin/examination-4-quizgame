import Client.ClientBase;
import Client.ClientProtocol;
import Client.ClientStart;
import GUI.LoginPanel;
import GUI.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Skapa MainFrame
            MainFrame mainFrame = new MainFrame();
            mainFrame.setSize(700, 700);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

            // Starta klient och login-panel
            ClientStart clientStart = new ClientStart(mainFrame);
            LoginPanel loginPanel = new LoginPanel(clientStart.getClient());
            mainFrame.setContentPane(loginPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
        });
    }
}

