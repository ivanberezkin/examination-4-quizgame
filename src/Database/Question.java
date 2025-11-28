package Database;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String prompt;
    private List<AnswerOption> answerOptions;
    private String qCat;

    public enum Category {
        ANIMALS("animals"),
        GEOGRAPHY("geography"),
        SPORT("sport");
        public final String qCat;

        Category(String qCat) {
            this.qCat = qCat;
        }

        @Override
        public String toString() {
            return qCat;
        }
    }

    public Question(String prompt, List<AnswerOption> answerOptions, String qCat) {
        this.prompt = prompt;
        this.answerOptions = answerOptions;
        this.qCat = qCat;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public String getCorrect(){
        String answer = "";
        for (AnswerOption option : answerOptions){
            if (option.getCorrect() == true ){
                answer = option.getText();
            }
        }
        return answer;
    }

    public String getCategory() {
        return qCat;
    }


    @Override
    public String toString() {
        return "Question{" +
                "prompt='" + prompt + '\'' +
                ", answerOptions=" + answerOptions +
                ", qCat='" + qCat + '\'' +
                '}';
    }
}
