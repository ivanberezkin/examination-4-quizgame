package GameComponents;

import Database.*;
import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;

public class Set {
    int numberOfPlayers = 0;
    private static int numberOfQuestions;
    private int numberOfMatches;
    private User player1;
    private User player2;
    private User[] players = new User[]{player1, player2};
    private int setScorePlayer1 = 0;
    private int setScorePlayer2 = 0;
    private User setWinner;
    private List <int[]>setScores = new ArrayList<>();
    private List <Match> matches;
    private Match match;
    Database.Question.Category[] category;
    private static List<Question> allSetQuestions;
    static Database db = new Database();

    public Set (User player, Database.Question.Category[] category, int numberOfQuestions, int numberOfMatches){
        this.player1 = player;
        this.category = category;
        this.numberOfMatches = numberOfMatches;
        this.numberOfQuestions = numberOfQuestions;
        startMatch(player);
    }

    public void startMatch (User player){
        if (matches.size() != numberOfMatches-1){
            match = new Match(player, category[matches.size()], numberOfQuestions);
            matches.add(match);
            numberOfPlayers+=1;
        }
    }
    public void addPlayer(User player){
        if (numberOfPlayers < 2){
            this.player2 = player;
            numberOfPlayers+=1;
            match.addPlayer(player);
        }
    }

    public void getMatchScore(Match match, Score score){
        match.setPoints(score);
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
    public List <Match> getMatches(){
        return matches;
    }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
}
