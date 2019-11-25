package com.example.countryquiz;

public class Answers {

    private String answerType;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswer;
    private String correctAnswerCapital;

    public Answers(String answerType, String answer1, String answer2, String answer3, String answer4, String correctAnswer,String correctAnswerCapital){
        this.answerType=answerType;
        this.answer1=answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.answer4=answer4;
        this.correctAnswer=correctAnswer;
        this.correctAnswerCapital=correctAnswerCapital;
    }

    public String getAnswerType() {
        return answerType;
    }

    public String getAnswer1(){
        return answer1;
    }
    public String getAnswer2(){
        return answer2;
    }
    public String getAnswer3(){
        return answer3;
    }
    public String getAnswer4(){
        return answer4;
    }
    public String getCorrectAnswer(){
        return correctAnswer;
    }
    public String getCorrectAnswerCapital(){
        return correctAnswerCapital;
    }
}
