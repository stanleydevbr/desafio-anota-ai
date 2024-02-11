package com.devbr.desafioanotaai.controllers;

import com.devbr.desafioanotaai.domain.category.Category;
import com.devbr.desafioanotaai.domain.category.CategoryDTO;
import com.devbr.desafioanotaai.services.CategoryService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService service;
    public CategoryController(CategoryService service){
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Category> Insert(@RequestBody CategoryDTO categoryData){

        Category newCategory = this.service.insert(categoryData);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> GetAll(){
        var categories = this.service.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> Update(@PathVariable("id") String id, @RequestBody CategoryDTO categoryData){

        Category newCategory = this.service.update(id, categoryData);
        return ResponseEntity.ok().body(newCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> Delete(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
