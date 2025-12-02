import Client.ClientBase;
import GUI.LoginPanel;
import GUI.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            MainFrame mainFrame = new MainFrame();
            ClientBase client = new ClientBase("127.0.0.1", 12345, mainFrame); // skapar och håller MainFrame
            client.start();

            LoginPanel loginPanel = new LoginPanel(client);
            mainFrame.setContentPane(loginPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
        });
    }
}

