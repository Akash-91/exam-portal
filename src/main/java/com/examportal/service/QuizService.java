package com.examportal.service;

import com.examportal.entity.exam.Category;
import com.examportal.entity.exam.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {

    public Quiz addQuiz(Quiz quiz);

    public Quiz updateQuiz(Quiz quiz);

    public Set<Quiz> getQuizzes();

    public Quiz getQuiz(Long quizId);

    public void deleteQuiz(Long quizId);

    public List<Quiz> getQuizzesOfCategory(Long cid);

    public List<Quiz> getActiveQuizzes();

    public List<Quiz> getActiveQuizzesOfCategory(Long cid);
}
