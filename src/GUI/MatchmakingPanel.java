package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class MatchmakingPanel extends JPanel {

    private JLabel timerLabel;
    private int secondsWaited = 0;
    private Timer timer;

    public MatchmakingPanel(Runnable onCancel) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));

        JLabel title = new JLabel("Matchmaking...", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel loading = new JLabel("Loading...", SwingConstants.CENTER);
        loading.setFont(new Font("Arial", Font.BOLD, 25));
        loading.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerLabel = new JLabel("Tid väntad: 0 sekunder", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(loading);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(timerLabel);

        add(centerPanel, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Avbryt");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setPreferredSize(new Dimension(200, 50));
        cancelButton.setFocusPainted(false);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        timer = new Timer(1000, e -> {
            secondsWaited++;
            timerLabel.setText("Tid väntad: " + secondsWaited + " sekunder");
        });
        timer.start();

        cancelButton.addActionListener(e -> {
            timer.stop();
            onCancel.run();
        });

    }
    public void stopTimer() {
        timer.stop();
    }


    //TESTTT
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Test Matchmaking");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        MatchmakingPanel panel = new MatchmakingPanel(() -> {
//            System.out.println("Matchmaking canceled!");
//        });
//
//        frame.setContentPane(panel);
//        frame.setSize(400, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }

}
