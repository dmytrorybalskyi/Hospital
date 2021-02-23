package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.ProceduresRepository;
import org.example.hospital.accessingdatamysql.TreatmentRepository;
import org.example.hospital.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")

@Sql(value = {"/create-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-category-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-doctor-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-patient-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-treatment-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-procedures-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-procedures-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-treatment-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-patient-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-doctor-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-category-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-account-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProceduresServiceTest {
    @Autowired
    private ProceduresService proceduresService;

    @Mock
    private ProceduresRepository proceduresRepository;

    @Mock
    private TreatmentRepository treatmentRepository;

    @Test
    public void findByDoctorAndStatus() {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        Doctor doctor = new Doctor("doctor");
        doctor.setCategory(category);
        Account account = new Account("doctorTest","test3");
        account.setRole(Roles.doctor);
        doctor.setAccount(account);
        doctor.setId(100502);
        List<Procedures> proceduresList = proceduresService.findByDoctorAndStatus(doctor,Status.treatment);
        assertEquals(2,proceduresList.size());
    }

    @Test
    public void findByPatientAndStatus() {
        Account account = new Account("test1","test1");
        account.setId(100500);
        List<Procedures> proceduresList = proceduresService.findByPatientAndStatus(account);
        assertEquals(2,proceduresList.size());
    }

    @Test
    public void doProcedure() {
        Account account = new Account("test1","test1");
        account.setId(100500);
        List<Procedures> proceduresList = proceduresService.findByPatientAndStatus(account);
        assertEquals(2,proceduresList.size());
        proceduresService.doProcedure(777);
        proceduresList = proceduresService.findByPatientAndStatus(account);
        assertEquals(1,proceduresList.size());
    }

    @Test
    public void addProcedure() {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        Doctor doctor = new Doctor("doctor");
        doctor.setCategory(category);
        Account account = new Account("doctorTest","test3");
        account.setRole(Roles.doctor);
        doctor.setAccount(account);
        doctor.setId(100502);
        Procedures procedures = new Procedures("coronka");
        List<Procedures> proceduresList = proceduresService.findByDoctorAndStatus(doctor,Status.treatment);
        assertEquals(2,proceduresList.size());
        assertTrue(proceduresService.addProcedure(999,doctor,Type.procedure,procedures));
        proceduresList = proceduresService.findByDoctorAndStatus(doctor,Status.treatment);
        assertEquals(3,proceduresList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addProcedureFail() {
        Category category = new Category();
        category.setId(2);
        category.setName("nurse");
        Doctor doctor = new Doctor("nurse");
        doctor.setCategory(category);
        Account account = new Account("nurseTest","test4");
        account.setRole(Roles.nurse);
        doctor.setAccount(account);
        doctor.setId(100503);
        Procedures procedures = new Procedures("coronka");
        assertTrue(proceduresService.addProcedure(999,doctor,Type.operation,procedures));
    }
}