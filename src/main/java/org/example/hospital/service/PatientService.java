package org.example.hospital.service;

import org.example.hospital.DTO.PatientDTO;
import org.example.hospital.accessingdatamysql.PatientRepository;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Patient;
import org.example.hospital.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AccountService accountService;

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean addPatient(PatientDTO patientDTO) throws SQLException {
        if(accountService.existsByLogin(patientDTO.getLogin())) {
            throw new IllegalArgumentException("user already exist");
        }
        Account account = new Account(patientDTO.getLogin(),patientDTO.getPassword());
        Patient patient = new Patient(patientDTO.getName(),patientDTO.getAge());
        account.setRole(new Role(2));
        patient.setAccount(account);
        accountService.addAccount(account);
        patientRepository.save(patient);
        return true;
    }

    private PatientDTO convertToPatientDTO(Patient patient){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLogin(patient.getAccount().getLogin());
        patientDTO.setPassword(patient.getAccount().getPassword());
        patientDTO.setName(patient.getName());
        patientDTO.setAge(patient.getAge());
        return  patientDTO;
    }


}
