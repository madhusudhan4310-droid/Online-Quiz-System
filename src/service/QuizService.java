package service;

import model.Question;
import java.util.*;

public class QuizService {
    private List<Question> questions = new ArrayList<>();

    public QuizService() {
        loadQuestions();
    }

    private void loadQuestions() {

        questions.add(new Question("What is Java?",
                new String[]{"Programming Language", "Operating System", "Browser", "Database"}, 1));

        questions.add(new Question("Which keyword is used for inheritance?",
                new String[]{"this", "super", "extends", "implements"}, 3));

        questions.add(new Question("Which collection allows dynamic size?",
                new String[]{"Array", "ArrayList", "int", "String"}, 2));

        questions.add(new Question("Which method is entry point of Java program?",
                new String[]{"start()", "main()", "run()", "init()"}, 2));

        questions.add(new Question("Which keyword is used to create object?",
                new String[]{"class", "new", "void", "static"}, 2));
    }

    public List<Question> getQuestions() {
        Collections.shuffle(questions); // Random questions
        return questions;
    }
}