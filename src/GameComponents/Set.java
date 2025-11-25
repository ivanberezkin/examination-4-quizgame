package GameComponents;

import Database.*;
import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;

public class Set {
    private int numberOfMatches = 2;
    private User player1;
    private User player2;
    private User[] players = new User[]{player1, player2};
    private int setScorePlayer1 = 0;
    private int setScorePlayer2 = 0;
    private User setWinner;
    private List <int[]>setScores = new ArrayList<>();
    private Match[]matches;
    Database.Question.Category[] category;
    private static int numberOfQuestions = 3;
    private static List<Question> allSetQuestions;
    static Database db = new Database();

    public Set (User[] players, Database.Question.Category[] category){
        this.player1 = players[0];
        this.player2 = players[1];
        this.category = category;
        startNewMatch();

    }
    public void startNewMatch (){
        if (matches.length != numberOfMatches-1){
            Match match = new Match(players, category[matches.length], numberOfQuestions);
        }
    }
    public void getMatchScore(Match match){
        if (match.checkIfComplete()){
            int[]matchPoints = match.getMatchPoints();
            setScorePlayer1 += matchPoints[0];
            setScorePlayer2 += matchPoints[1];
            setScores.add(matchPoints);
        }
    }
    public static Question[] getQuestions(){
        Question[]questions = new Question[numberOfQuestions];
        for (int i = 0; i<= numberOfQuestions; i++) {
            Question question = db.getNewQuestion();
            if (!previousQuestion(question)) {
                allSetQuestions.add(question);
                questions[i] = question;
            }
        }
        return questions;
    }
    private static boolean previousQuestion(Question question){
        for (Question q : allSetQuestions){
            if (q.getPrompt().equals(question.getPrompt())){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfCompleted(){
        return setScores.size() == numberOfMatches - 1;
    }
    public void findSetWinner(){
        if (checkIfCompleted()){
            if (setScorePlayer1 > setScorePlayer2){
                setWinner = player1;
            }
            else if (setScorePlayer1 < setScorePlayer2){
                setWinner = player2;
            }
            else {
                setWinner = null;
            }
        }
    }
}
