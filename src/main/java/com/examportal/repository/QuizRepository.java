package com.examportal.repository;

import com.examportal.entity.exam.Category;
import com.examportal.entity.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    public List<Quiz> findByCategory(Category category);     // custom finder method

    public List<Quiz> findByActive(Boolean b);    // custom finder method

    public List<Quiz> findByCategoryAndActive(Category category, Boolean b);    // custom finder method


}
