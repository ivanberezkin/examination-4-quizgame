package Client;

import Database.*;
import GUI.*;
import GameComponents.*;
import Quizgame.shared.*;
import javax.swing.*;
import java.util.List;

public class ClientProtocol {

    private final ClientBase client;
    private final MainFrame frame;
    private User loggedInUser;

    public ClientProtocol(ClientBase client, MainFrame frame) {
        this.client = client;
        this.frame = frame;
    }

    public void handleMessage(Message message) {
        IO.println("CLIENTPROTOCOL: Message type to process " + message.getType());
        switch (message.getType()) {

            case LOGIN_OK -> {
                loggedInUser = (User) message.getData();
                JOptionPane.showMessageDialog(frame, "Welcome " + loggedInUser.getUsername());

                MenuPanel menuPanel = new MenuPanel(loggedInUser,client, frame);
                frame.setContentPane(menuPanel);
                frame.revalidate();
                frame.repaint();
            }

            case LOGIN_WRONG_PASSWORD -> {
                JOptionPane.showMessageDialog(null,
                        "Wrong password. Try again",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            case LOGIN_USER_NOT_FOUND -> {
                int choice = JOptionPane.showConfirmDialog(null,
                        "User does not exist. Create a new user?",
                        "Create user",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    String username = JOptionPane.showInputDialog(frame, "Enter your username:");
                    if (username == null || username.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Username cannot be empty.");
                        return;
                    }

                    String password = JOptionPane.showInputDialog(frame, "Enter your password:");
                    if (password == null || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Password cannot be empty.");
                        return;
                    }

                    User newUser = new User(username, password);
                    client.sendMessage(new Message(MessageType.LOGIN_CREATE_REQUEST, newUser));
                } else {
                    JOptionPane.showMessageDialog(frame, "Please try again.");
                }
            }

            case LOGIN_CREATE_OK -> {
                loggedInUser = (User) message.getData();
                JOptionPane.showMessageDialog(null,
                        "User " + loggedInUser.getUsername() + " created!" );
                //moveUserToMenuPanel();
            }

            case LOGIN_CREATE_FAIL -> {
                JOptionPane.showMessageDialog(frame,
                        "Username already taken.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            case REQUEST_NEW_ROUND -> {
                if(message.getData() instanceof User user){
                    client.sendMessage(new Message(MessageType.START_NEXT_ROUND, user));
                }
            }

            case WAITING -> {
                MatchmakingPanel matchmakingPanel = new MatchmakingPanel(() -> {
                });
                frame.setContentPane(matchmakingPanel);
                frame.revalidate();
                frame.repaint();
            }
            case ADDED_TO_GAME -> {

            }
            case CATEGORY_REQUEST -> {
                User user = (User) message.getData();
                CategoryPanel categoryPanel = new CategoryPanel(user, frame, client, new MenuPanel(user, client, frame));
                SwingUtilities.invokeLater(() -> {
                    frame.setContentPane(categoryPanel);
                    frame.revalidate();
                    frame.repaint();
                });

            }
//            case GAME_START -> {
//
//                    //User user = (User) message.getData();
//                    // if (user != null) {
//                    MatchmakingPanel matchmakingPanel = new MatchmakingPanel(() -> {
//                    });
//                    SwingUtilities.invokeLater(() -> {
//                        frame.setContentPane(matchmakingPanel);
//                        frame.revalidate();
//                        frame.repaint();
//                    });
//            }

            case QUESTION -> {
                IO.println("Questions Received");
                if (message.getData() instanceof Question question) {
                    GamePanel gamePanel = new GamePanel(client, question, loggedInUser, frame);
                    SwingUtilities.invokeLater(() -> {
                        frame.setContentPane(gamePanel);
                        frame.revalidate();
                        frame.repaint();
                    });
                }
            }

            case RESULT_ROUND -> {
                boolean waiting;
                if (message.getData() instanceof List list) {
                    if (!list.isEmpty() && list.getFirst() instanceof Score) {
                        List<Score> roundScores = (List<Score>) message.getData();
//                        if (roundScores.size() == 5) {
//                            waiting = false;
//                        } else {
//                            waiting = true;
//                        }
                        waiting = false;
                        GUI.ResultPanel resultPanel = new ResultPanel(roundScores, loggedInUser, client);
                        SwingUtilities.invokeLater(() -> {
                            frame.setContentPane(resultPanel);
//                            resultPanel.setNextRoundButton(false);
                            frame.revalidate();
                            frame.repaint();
                        });
                    } else {
                        waiting = true;
                    }
                }
            }

            case GAME_FINISHED -> {
                //add game logic here
            }
        }
    }
   /* private void moveUserToMenuPanel() {
        MenuPanel menuPanel = new MenuPanel(loggedInUser,frame,client);
        frame.setContentPane(menuPanel);
        frame.revalidate();
        frame.repaint();
    } */

}
