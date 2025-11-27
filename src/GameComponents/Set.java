package GameComponents;
import Database.*;
import Quizgame.shared.User;
import java.util.ArrayList;
import java.util.List;

public class Set {
    static int maxPlayers;
    static int numberOfPlayers = 0;
    private static int maxNumberOfQuestions;
    private final int maxNumberOfMatches;
    private User player1;
    private User player2;
    private int setScorePlayer1 = 0;
    private static int setScorePlayer2 = 0;
    private User setWinner;
    private static List<int[]> setScores = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();
    private static Match match;
    Question.Category category;
    private static List<Question> allSetQuestions = new ArrayList<>();
    static Database db = new Database();

    public Set(List <User> players, Question.Category category, int maxPlayers, int maxNumberOfQuestions, int maxNumberOfMatches) {
        this.category = category;
        this.maxPlayers = maxPlayers;
        this.maxNumberOfMatches = maxNumberOfMatches;
        this.maxNumberOfQuestions = maxNumberOfQuestions;
        startMatch(players);
    }

    public void startMatch(List<User> players) {
        if (matches.size() < maxNumberOfMatches) {
        if (players.size() == 2) {
            match = new Match(players, category, maxNumberOfQuestions, maxPlayers);
        } else {

                match = new Match(players, category, maxNumberOfQuestions, maxPlayers);
                matches.add(match);
            }
        }
        else {
            if (checkIfCompleted()){
                Game.sendMatchScore(players, match.getMatchID());
            }
        }
    }

    public void addPlayer(User player) {
        if (numberOfPlayers < 2) {
            this.player2 = player;
            numberOfPlayers += 1;
            List<User>players = new ArrayList<>();
            players.add(player);
            match.addPlayer(players);
        }
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

    public static Question[] getQuestions() {
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
    private static boolean ensureNewQuestion(Question question){
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
