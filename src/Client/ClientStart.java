package Client;

import GUI.GamePanel;

import javax.swing.*;

public class ClientStart {
    private final static String host = "127.0.0.1";
    private final static int port = 12344;

    private ClientBase client;
    private JFrame frame;

    public ClientBase getClient() {
        return client;
    }

    public ClientStart(JFrame frame) {
        this.frame = frame;
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
            client = new ClientBase(host, port, frame);
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
