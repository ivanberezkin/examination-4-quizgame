package Database;

import java.util.List;

public class Question {
    private String prompt;
    private List<AnswerOption> answerOptions;
    private Category qCat;

    public enum Category{ ANIMALS("animals"), GEOGRAPHY("geography"), SPORT("sport");
    public final String qCat;
    Category (String qCat) { this.qCat = qCat; }
    @Override
    public String toString () {return  qCat; }
    }

    public Question (String prompt, List<AnswerOption>answerOptions, Category qCat){
        this.prompt = prompt;
        this.answerOptions = answerOptions;
        this.qCat = qCat;
    }
    public String getPrompt(){
        return prompt;
    }
    public List<AnswerOption>getAnswerOptions(){
        return answerOptions;
    }
    public Category getCategory(){
        return qCat;
    }
}
