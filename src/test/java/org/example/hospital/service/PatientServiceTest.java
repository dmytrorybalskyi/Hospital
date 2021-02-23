package org.example.hospital.service;

import org.example.hospital.DTO.PatientDTO;
import org.example.hospital.accessingdatamysql.DoctorRepository;
import org.example.hospital.accessingdatamysql.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")

@Sql(value = {"/create-account-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-patient-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-patient-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-account-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AccountService accountService;

    @Test
    public void addPatient() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLogin("patientTest3");
        patientDTO.setPassword("patient");
        patientDTO.setAge(22);
        patientDTO.setName("patientTest3");
        assertTrue(patientService.addPatient(patientDTO));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addPatientFail() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLogin("test1");
        patientDTO.setPassword("patient");
        patientDTO.setAge(22);
        patientDTO.setName("patientTest2");
        assertTrue(patientService.addPatient(patientDTO));
    }

}