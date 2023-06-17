package com.examportal.service.impl;

import com.examportal.entity.exam.Category;
import com.examportal.entity.exam.Question;
import com.examportal.entity.exam.Quiz;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuestionRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.service.CategoryService;
import com.examportal.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class QuestionsServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Question addQuestions(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestions(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new LinkedHashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        this.questionRepository.deleteById(questionId);
    }
}
