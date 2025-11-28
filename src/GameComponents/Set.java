package GameComponents;
import Database.*;
import Quizgame.shared.User;
import Server.Connections;

import java.util.ArrayList;
import java.util.List;

public class Set {
    int maxPlayers;
    int numberOfPlayers;
    private int maxNumberOfQuestions;
    private final int maxNumberOfMatches;
    private List<User>players = new ArrayList<>();
    private Connections player1;
    private Connections player2;
    private int setScorePlayer1 = 0;
    private int setScorePlayer2 = 0;
    private Connections setWinner;
    private List<int[]> setScores = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();
    private Match match;
    Question.Category category;
    private final List<Question> allSetQuestions = new ArrayList<>();
    static Database db = new Database();

    public Set(Connections player, Question.Category category, int maxPlayers, int maxNumberOfQuestions, int maxNumberOfMatches) {
        this.category = category;
        this.maxPlayers = maxPlayers;
        this.maxNumberOfMatches = maxNumberOfMatches;
        this.maxNumberOfQuestions = maxNumberOfQuestions;
        players.add(player.getUser());
        this.numberOfPlayers = players.size();
        startMatch(player);
    }

    public void startMatch(Connections player) {
        if (matches.size() < maxNumberOfMatches) {
            match = new Match(this, player, category, maxNumberOfQuestions, maxPlayers);
            matches.add(match);
        }
        else {
            if (checkIfCompleted()){
                Game.sendMatchScore(match);
            }
        }
    }

    public void addPlayer(Connections player) {
        System.out.println("addPlayer in Set was reached");
        if (numberOfPlayers == 0){
            this.player1 = player;
        }
       else if (numberOfPlayers == 1) {
            this.player2 = player;
        }
            numberOfPlayers += 1;
            match.addPlayer(player);
        }

    public void continuePlaying() {
        if (numberOfPlayers == 2 && matches.size() < maxNumberOfMatches && (!match.completedMatch()) && (match.getPointsPlayer1().size() == match.getPointsPlayer2().size())) {
            match.sendQuestion();
        }
    }

    public void getMatchScore() {
        if (match.completedMatch()) {
            setSetScore();
        }
        continuePlaying();
    }

    private void setSetScore() {
        if (match.completedMatch()) {
            for (int i = 0; i < maxNumberOfQuestions; i++) {
                setScorePlayer1 = match.getPointsPlayer1().get(i);
                setScorePlayer2 = match.getPointsPlayer2().get(i);
                setScores.add(new int[]{setScorePlayer1, setScorePlayer2});
            }
        }
    }

    public Question[] getQuestions() {
        Question[] questions = new Question[maxNumberOfQuestions];
        ArrayList<Question> questionList = db.getQuestionsForRound(maxNumberOfQuestions);

        for (int i = 0; i < maxNumberOfQuestions; i++) {
            Question question;
            do {

                question = questionList.get(i);
            }
            while (ensureNewQuestion(question));
            allSetQuestions.add(question);
            questions[i] = question;
        }
        return questions;
    }
    private boolean ensureNewQuestion(Question question){
        for (Question q : allSetQuestions){
            if (q.getPrompt().equals(question.getPrompt())){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfCompleted(){
        return setScores.size() == maxNumberOfMatches - 1;
    }
    public void findSetWinner(){
        int player1Total = 0;
        int player2Total = 0;
        if (checkIfCompleted()){
            for (int [] score : setScores){
                player1Total += score[0];
                player2Total += score[1];
            }
            if (player1Total > player2Total){
                setWinner = player1;
            }
            else if (player1Total < player2Total){
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
