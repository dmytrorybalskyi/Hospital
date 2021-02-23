package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.accessingdatamysql.CategoryRepository;
import org.example.hospital.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-category-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-category-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void findAll() {
        List<Category> categories = categoryService.findAll();
        assertNotNull(categories);
        assertEquals(3,categories.size());
    }

    @Test
    public void findAllWithoutNurse() {
        List<Category> categories = categoryService.findAllWithoutNurse();
        assertNotNull(categories);
        assertEquals(2,categories.size());
    }
}