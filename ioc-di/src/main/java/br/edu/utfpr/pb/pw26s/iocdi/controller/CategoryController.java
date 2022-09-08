package br.edu.utfpr.pb.pw26s.iocdi.controller;

import br.edu.utfpr.pb.pw26s.iocdi.respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    /**
     * 1 - constructor injection
     * 2 - setter injection
     * 3 - field injection
     */

    private final CategoryRepository categoryRepository;

//    @Autowired
//    public void setCategoryRepository(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

    //    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
