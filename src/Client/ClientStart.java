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
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor();
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            GamePanel gamePanel = new GamePanel();
//            frame.add(gamePanel);
//
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);});
    }
    public ClientBase getClient() {
        return client;
    }
    public MainFrame getMainFrame() {
        return mainFrame;
    }

//    public static void main(String[] args) {
//        ClientStart client = new ClientStart();
//    }
}
