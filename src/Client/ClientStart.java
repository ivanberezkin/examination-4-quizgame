package Client;

import GUI.GamePanel;

import javax.swing.*;

public class ClientStart {
    private final static String host = "127.0.0.1";
    private final static int port = 12345;

    private ClientBase client;

    public ClientBase getClient() {
        return client;
    }

    public ClientStart() {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor();
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            GamePanel gamePanel = new GamePanel();
//            frame.add(gamePanel);
//
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//

        try {
            client = new ClientBase(host, port);
            client.start();
        } catch (RuntimeException e) {
            System.out.println("Kunde inte ansluta till servern: " + e.getMessage());
        }
//        });
    }

//    public static void main(String[] args) {
//        ClientStart client = new ClientStart();
//    }
}
