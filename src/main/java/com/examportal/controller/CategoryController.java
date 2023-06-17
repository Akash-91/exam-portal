package com.examportal.controller;

import com.examportal.entity.exam.Category;
import com.examportal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //add category

    @PostMapping("/")
    ResponseEntity<Category> addCategory(@RequestBody Category category) {
        var categoryResult = this.categoryService.addCategory(category);
        return ResponseEntity.ok(categoryResult);
    }

    @PutMapping("/")
    ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        var categoryResult = this.categoryService.updateCategory(category);
        return ResponseEntity.ok(categoryResult);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<Category> getCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
    }

    @GetMapping("/")
    ResponseEntity<Set<Category>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }

}
