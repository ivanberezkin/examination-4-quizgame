package Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final ArrayList<Question> questionsList = new ArrayList<>();

    Database() {



    }

    public void readFromQuestionsFile(){

        try(BufferedReader br = new BufferedReader(new FileReader("questions.txt"))){
            String line;
            String category;
            String question;
            String correctAnswer;

            List <AnswerOption> answerOptions = new ArrayList<>();

            while((line = br.readLine()) != null){
            String [] splitLine = line.split(":");
            category = splitLine[0];
            question = splitLine[1];

            correctAnswer = splitLine[splitLine.length-1];

            for(int i = 2; i < splitLine.length-1; i++){
                if(splitLine[i].equals(correctAnswer)){
                    answerOptions.add(new AnswerOption(splitLine[i],true));
                }else{
                    answerOptions.add(new AnswerOption(splitLine[i],false));
                }
            }
                questionsList.add(new Question(question,answerOptions,category));
                IO.println("Question added succesfully");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
