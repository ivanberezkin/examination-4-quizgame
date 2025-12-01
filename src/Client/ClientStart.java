package Client;

import GUI.GamePanel;
import GUI.LoginPanel;
import GUI.MainFrame;

import javax.swing.*;

public class ClientStart {
    private final static String host = "127.0.0.1";
    private final static int port = 12345;

    private final ClientBase client;
    private final MainFrame mainFrame;


    public ClientStart(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.client = new ClientBase(host, port, mainFrame);
        client.start();

        LoginPanel loginPanel = new LoginPanel(client);
        mainFrame.setContentPane(loginPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public ClientBase getClient() {
        return client;
    }
}