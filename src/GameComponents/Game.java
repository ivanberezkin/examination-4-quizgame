package GameComponents;
import Database.*;
import Quizgame.shared.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    int maxPlayers;
    int numberOfPlayers;
    private int maxNumberOfQuestions;
    private final int maxNumberOfMatches;
    private List<User>players = new ArrayList<>();
    private User player1;
    private User player2;
    private int setScorePlayer1 = 0;
    private int setScorePlayer2 = 0;
    private User setWinner;
    private List<int[]> setScores = new ArrayList<>();
    private List<Rond> ronds = new ArrayList<>();
    private Rond rond;
    Question.Category category;
    private final List<Question> allSetQuestions = new ArrayList<>();
    static Database db = new Database();

    public Game(User player, Question.Category category, int maxPlayers, int maxNumberOfQuestions, int maxNumberOfMatches) {
        this.category = category;
        this.maxPlayers = maxPlayers;
        this.maxNumberOfMatches = maxNumberOfMatches;
        this.maxNumberOfQuestions = maxNumberOfQuestions;
        this.player1 = player;
        players.add(player);
        this.numberOfPlayers = players.size();
        startMatch(player);
    }

    public void startMatch(User player) {
        if (ronds.size() < maxNumberOfMatches) {
            rond = new Rond(this, player, category, maxNumberOfQuestions, maxPlayers);
            ronds.add(rond);
        }
        else {
            if (checkIfCompleted()){
                GameManager.sendMatchScore(rond);
            }
        }
    }

    public void addPlayer(User player) {
        if (numberOfPlayers == 0){
            this.player1 = player;
            players.add(player1);
            numberOfPlayers += 1;
            rond.addPlayer(player1);
        }
        else if (numberOfPlayers == 1) {
            this.player2 = player;
            players.add(player2);
            numberOfPlayers += 1;
            rond.addPlayer(player2);
        }
    }

    public void continuePlaying() {
        if (numberOfPlayers == 2 && ronds.size() < maxNumberOfMatches && (!rond.completedMatch()) && (rond.getPointsPlayer1().size() == rond.getPointsPlayer2().size())) {
            rond.sendQuestion();
        }
    }

    public void getMatchScore() {
        if (rond.completedMatch()) {
            setSetScore();
        }
        continuePlaying();
    }

    private void setSetScore() {
        if (rond.completedMatch()) {
            for (int i = 0; i < maxNumberOfQuestions; i++) {
                setScorePlayer1 = rond.getPointsPlayer1().get(i);
                setScorePlayer2 = rond.getPointsPlayer2().get(i);
                setScores.add(new int[]{setScorePlayer1, setScorePlayer2});
            }
        }
    }

    public Question[] getQuestions() {
        Question[] questions = new Question[maxNumberOfQuestions];
        ArrayList<Question> questionList = db.getQuestionsForRound(maxNumberOfQuestions * 2); //Prevents issues if some questions would be dupes.
        int count = 0;

        for (Question q : questionList) {
            boolean dupe = false;
            for (int i = 0; i < count; i++) {
                if (questions[i].getPrompt().equals(q.getPrompt())) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe) {
                questions[count++] = q;
            }
            if(count == maxNumberOfQuestions){
            break;}
        }
        if(count < maxNumberOfQuestions){
            System.out.println("WARNING: not enough with unique questions");
        }
        return questions;
    }
            /*Question question;
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
        return false;*/


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
    public List <Rond> getMatches(){
        return ronds;
    }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
}
