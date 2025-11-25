package Client;

import GUI.GamePanel;

import javax.swing.*;

public class ClientStart {
    private final static String host = "127.0.0.1";
    private final static int port = 12345;

    public ClientStart() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quizgame Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


        try {
            ClientBase client = new ClientBase(host, port, gamePanel);
        } catch (RuntimeException e) {
            System.out.println("Kunde inte ansluta till servern: " + e.getMessage());
        }
        });
    }

//    public static void main(String[] args) {
//        ClientStart client = new ClientStart();
//    }
}
