package com.examportal.controller;

import com.examportal.entity.exam.Category;
import com.examportal.entity.exam.Question;
import com.examportal.entity.exam.Quiz;
import com.examportal.service.CategoryService;
import com.examportal.service.QuestionService;
import com.examportal.service.QuizService;
import com.mysql.cj.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@CrossOrigin("*")
@AllArgsConstructor
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;


    private Integer marksGot;
    private Integer correctAnswers;
    private Integer attempted;

    @PostMapping("/")
    ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        var categoryResult = this.questionService.addQuestions(question);
        return ResponseEntity.ok(categoryResult);
    }

    @PutMapping("/")
    ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        var categoryResult = this.questionService.updateQuestions(question);
        return ResponseEntity.ok(categoryResult);
    }


    @GetMapping("/quiz/{qid}")
    ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList<>(questions);
        Collections.shuffle(list);
        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()));
        }
        list.forEach(o -> o.setAnswer(""));
        return ResponseEntity.ok(list);
    }


    @GetMapping("/quiz/all/{qid}")
    ResponseEntity<?> getQuestionsOfQuizForAdmin(@PathVariable("qid") Long qid) {
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List list = new ArrayList<>(questions);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{quesId}")
    ResponseEntity<Question> getQuestion(@PathVariable("quesId") Long quesId) {
        return ResponseEntity.ok(this.questionService.getQuestion(quesId));
    }

    @GetMapping("/")
    ResponseEntity<Set<Question>> getQuestions() {
        return ResponseEntity.ok(this.questionService.getQuestions());
    }

    @DeleteMapping("/{quesId}")
    public void deleteQuestion(@PathVariable("quesId") Long quesId) {
        this.questionService.deleteQuestion(quesId);
    }


    @PostMapping("/evaluate-quiz")
    public ResponseEntity<?> evaluateQuiz(@RequestBody List<Question> questions) {

        resetFields();
        Integer marksSingle = Integer.parseInt(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
        questions.forEach(q -> {
            Question question = this.questionService.getQuestion(q.getQId());
            if (question.getAnswer().equalsIgnoreCase(q.getGivenAnswer())) {
                correctAnswers++;
                marksGot += marksSingle;
            }
            if (!StringUtils.isNullOrEmpty(q.getGivenAnswer())) {
                attempted++;
            }
        });
        return ResponseEntity.ok(Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted));
    }

    private void resetFields() {
        this.marksGot = 0;
        this.attempted = 0;
        this.correctAnswers = 0;
    }
}
