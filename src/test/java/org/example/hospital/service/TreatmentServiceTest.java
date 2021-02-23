package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.PatientRepository;
import org.example.hospital.accessingdatamysql.ProceduresRepository;
import org.example.hospital.accessingdatamysql.TreatmentRepository;
import org.example.hospital.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
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
@Sql(value = {"/create-treatment-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-patient-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-doctor-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-category-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-account-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TreatmentServiceTest {

    @Autowired
    private TreatmentService treatmentService;

    @Mock
    private ProceduresRepository proceduresRepository;

    @Mock
    private TreatmentRepository treatmentRepository;

    @Test
    public void findById() {
        Treatment treatment = treatmentService.findById(999).get();
        assertEquals("caries",treatment.getDiagnosis());
    }

    @Test
    public void findByStatus() {
        List<Treatment> treatmentList = treatmentService.findByStatus(Status.treatment, Pageable.unpaged()).getContent();
        assertEquals(1,treatmentList.size());
    }

    @Test
    public void findByStatusAndDoctor() {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        Doctor doctor = new Doctor("doctor");
        doctor.setCategory(category);
        Account account = new Account("doctorTest","test3");
        account.setRole(Roles.doctor);
        doctor.setAccount(account);
        doctor.setId(100502);
        List<Treatment> treatmentList = treatmentService.findByStatusAndDoctor(Status.treatment,doctor);
        assertEquals(1,treatmentList.size());
    }

    @Test
    public void setDiagnosis() {
        String expected = "plomba";
        treatmentService.setDiagnosis(expected,999);
        String actual = treatmentService.findById(999).get().getDiagnosis();
        assertEquals(expected,actual);
    }

    @Test
    public void setDoctor() {
        String expected = "doctor";
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        Doctor doctor = new Doctor("doctor");
        doctor.setCategory(category);
        Account account = new Account("doctorTest","test3");
        account.setRole(Roles.doctor);
        doctor.setAccount(account);
        doctor.setId(100502);
        treatmentService.setDoctor(doctor,998);
        Treatment treatment = treatmentService.findById(998).get();
        assertEquals(expected,treatment.getDoctor().getName());
        assertEquals(1,treatment.getDoctor().getPatientsNumber());
        assertEquals(java.util.Optional.of(100502).get(),treatment.getPatient().getDoctor().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTreatmentFalse() throws SQLException {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        Account account = new Account("test1","test1");
        account.setId(100500);
        assertTrue(treatmentService.addTreatment(account,category));
    }

    @Test
    public void addTreatment() throws SQLException {
        Category category = new Category();
        category.setId(1);
        category.setName("dentist");
        Account account = new Account("test4","test4");
        account.setId(100504);
        assertTrue(treatmentService.addTreatment(account,category));
    }

    @Test
    public void discharge() {
        treatmentService.discharge(999);
        Treatment treatment = treatmentService.findById(999).get();
        assertEquals(Status.done,treatment.getStatus());
        assertEquals(0,treatment.getDoctor().getPatientsNumber());
        assertNull(treatment.getPatient().getDoctor());
    }

    @Test
    public void getArchive(){
        List<Treatment> treatments = treatmentService.getArchive();
        assertEquals(0,treatments.size());
        treatmentService.discharge(999);
        treatments = treatmentService.getArchive();
        assertEquals(1,treatments.size());
    }
}