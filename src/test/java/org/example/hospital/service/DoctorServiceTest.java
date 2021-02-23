package org.example.hospital.service;

import org.example.hospital.DTO.DoctorDTO;
import org.example.hospital.accessingdatamysql.CategoryRepository;
import org.example.hospital.accessingdatamysql.DoctorRepository;
import org.example.hospital.entity.Category;
import org.example.hospital.entity.Doctor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-category-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-account-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-doctor-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-doctor-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-account-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-category-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DoctorServiceTest {
    @Autowired
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AccountService accountService;

    @Test
    public void getAllDoctorsByCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        List<Doctor> doctors = doctorService.getAllDoctorsByCategory(category);
        assertEquals(1,doctors.size());
        assertEquals("doctor",doctors.get(0).getName());
    }

    @Test
    public void getAllDoctorsByCategoryAndNurse() {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        List<Doctor> doctors = doctorService.getAllDoctorsByCategoryAndNurse(category);
        assertEquals(2,doctors.size());
    }

    @Test
    public void addDoctor() {
        DoctorDTO doctorDTO = new DoctorDTO();
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        doctorDTO.setLogin("doctorTest2");
        doctorDTO.setName("doctor2");
        doctorDTO.setCategory(category);
        doctorDTO.setPassword("doc");
        assertTrue(doctorService.addDoctor(doctorDTO));
        List<Doctor> doctors = doctorService.getAllDoctorsByCategory(category);
        assertEquals(2,doctors.size());
    }

    @Test
    public void addDoctorNurse() {
        DoctorDTO doctorDTO = new DoctorDTO();
        Category category = new Category();
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("dentist");
        category.setId(2);
        category.setName("nurse");
        doctorDTO.setLogin("nurseTest2");
        doctorDTO.setName("nurse2");
        doctorDTO.setCategory(category);
        doctorDTO.setPassword("nurse2");
        assertTrue(doctorService.addDoctor(doctorDTO));
        List<Doctor> doctors = doctorService.getAllDoctorsByCategoryAndNurse(category1);
        assertEquals(3,doctors.size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addDoctorFail(){
        DoctorDTO doctorDTO = new DoctorDTO();
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        doctorDTO.setLogin("doctorTest");
        doctorDTO.setName("doctor");
        doctorDTO.setCategory(category);
        doctorDTO.setPassword("doc");
        assertTrue(doctorService.addDoctor(doctorDTO));
    }
}