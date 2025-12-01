package Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {

    private final ArrayList<Question> questionsList = new ArrayList<>();
    private final String questionPath = "resources/Questions";
    private ArrayList<Question> questionsListForUser = new ArrayList<>();

    public Database() {
        readFromQuestionsFile();
    }

    public void readFromQuestionsFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(questionPath))) {
            String line;
            String category;
            String question;
            String correctAnswer;

            while ((line = br.readLine()) != null) {
                List<AnswerOption> answerOptions = new ArrayList<>();
                String[] splitLine = line.split(":");
                category = splitLine[0];
                question = splitLine[1];

                correctAnswer = splitLine[splitLine.length - 1];

                for (int i = 2; i < splitLine.length - 2; i++) {
                    if (splitLine[i].equals(correctAnswer)) {
                        answerOptions.add(new AnswerOption(splitLine[i], true));
                    } else {
                        answerOptions.add(new AnswerOption(splitLine[i], false));
                    }
                }
                questionsList.add(new Question(question.trim(), answerOptions, category.trim()));
//                IO.println("Category: " + category);
//                IO.println("Question: " + question);
//                IO.println(answerOptions.getFirst().getText());
//                IO.println(answerOptions.get(1).getText());
//                IO.println(answerOptions.get(2).getText());
//                IO.println(answerOptions.getLast().getText());
//                IO.println(answerOptions.getFirst().getCorrect());

//                IO.println(answerOptions.getLast().getCorrect());
//                IO.println("Answer: " + correctAnswer);
//                IO.println(answerOptions.size());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Question> getQuestionsForRound(int questionsPerRound) {
        ArrayList<Question> result = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0; i < questionsPerRound; i++){
            Question q = questionsList.get(rand.nextInt(questionsList.size()));
            result.add(q);
        }
        return result;
    }


}
