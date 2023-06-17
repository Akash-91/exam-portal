package com.examportal.controller;


import com.examportal.entity.exam.Quiz;
import com.examportal.service.QuizService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        var quizResult = this.quizService.addQuiz(quiz);
        return ResponseEntity.ok(quizResult);
    }

    @PutMapping("/")
    ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz) {
        var categoryResult = this.quizService.updateQuiz(quiz);
        return ResponseEntity.ok(categoryResult);
    }

    @GetMapping("/{qid}")
    ResponseEntity<Quiz> getQuiz(@PathVariable("qid") Long qid) {
        return ResponseEntity.ok(this.quizService.getQuiz(qid));
    }

    @GetMapping("/")
    ResponseEntity<Set<Quiz>> getQuizzes() {
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    @DeleteMapping("/{qid}")
    public void deleteQuiz(@PathVariable("qid") Long qid) {
        this.quizService.deleteQuiz(qid);
    }

    @GetMapping("/category/{cid}")
    ResponseEntity<List<Quiz>> getQuizzesOfCategory(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(this.quizService.getQuizzesOfCategory(cid));
    }

    @GetMapping("/active")
    ResponseEntity<List<Quiz>> getActiveQuizzes() {
        return ResponseEntity.ok(this.quizService.getActiveQuizzes());
    }

    @GetMapping("/category/active/{cid}")
    ResponseEntity<List<Quiz>> getActiveQuizzesOfCategory(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(this.quizService.getActiveQuizzesOfCategory(cid));
    }
}
