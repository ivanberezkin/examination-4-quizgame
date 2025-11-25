package Database;

import java.io.Serializable;

public class AnswerOption implements Serializable {
    private final String text;
    private final boolean correct;

    public AnswerOption(String text, boolean correct){
        this.text = text;
        this.correct = correct;
    }
    public String getText(){
        return text;
    }
    public boolean getCorrect(){
        return correct;
    }
}
