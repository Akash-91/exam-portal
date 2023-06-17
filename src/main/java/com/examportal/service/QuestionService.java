package com.examportal.service;

import com.examportal.entity.exam.Question;
import com.examportal.entity.exam.Quiz;

import java.util.Set;

public interface QuestionService {


    public Question addQuestions(Question question);

    public Question updateQuestions(Question question);

    public Set<Question> getQuestions();

    public Question getQuestion(Long questionId);

    public Set<Question> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(Long questionId);

}
