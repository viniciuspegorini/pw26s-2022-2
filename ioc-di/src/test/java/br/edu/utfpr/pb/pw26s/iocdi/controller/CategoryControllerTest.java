package br.edu.utfpr.pb.pw26s.iocdi.controller;

import br.edu.utfpr.pb.pw26s.iocdi.respository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryControllerTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryController categoryController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(categoryController);
    }

}