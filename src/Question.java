public class Question {
    private String prompt;
    private String[] answerOptions;
    private Category qCat;

    public enum Category{ ANIMALS("animals"), GEOGRAPHY("geography"), SPORT("sport");
    public final String qCat;
    Category (String qCat) { this.qCat = qCat; }
    @Override
    public String toString () {return  qCat; }
    }

    public Question (String prompt, String[]answerOptions, Category qCat){
        this.prompt = prompt;
        this.answerOptions = answerOptions;
        this.qCat = qCat;
    }
    public String getPrompt(){
        return prompt;
    }
    public String[]getAnswerOptions(){
        return answerOptions;
    }
    public Category getqCat(){
        return qCat;
    }
}
