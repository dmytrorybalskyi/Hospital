package org.example.hospital.service;

import org.example.hospital.DTO.PatientDTO;
import org.example.hospital.accessingdatamysql.PatientRepository;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Patient;
import org.example.hospital.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AccountService accountService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean addPatient(PatientDTO patientDTO){
        Account account = convertToAccount(patientDTO);
        Patient patient = convertToPatient(patientDTO,account);
        accountService.addAccount(account);
        patientRepository.save(patient);
        return true;
    }

    private Patient convertToPatient(PatientDTO patientDTO, Account account){
        Patient patient = new Patient(patientDTO.getName(),patientDTO.getAge());
        patient.setAccount(account);
        return patient;
    }

    private Account convertToAccount(PatientDTO patientDTO){
        Account account = new Account(patientDTO.getLogin(),patientDTO.getPassword());
        account.setRole(Roles.patient);
        return account;
    }

}
