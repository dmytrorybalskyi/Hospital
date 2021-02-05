package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.DoctorRepository;
import org.example.hospital.accessingdatamysql.PatientRepository;
import org.example.hospital.accessingdatamysql.TreatmentRepository;
import org.example.hospital.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Treatment> getArchive(){
        return treatmentRepository.findByStatus(new Status(3));
    }

    public List<Treatment> findAll() {
        return (List<Treatment>) treatmentRepository.findAll();
    }

    public Optional<Treatment> findById(int id) {
        return treatmentRepository.findById(id);
    }

    public Page<Treatment> findByStatus(Status status, Pageable pageable) {
        return treatmentRepository.findByStatus(status,pageable);
    }
    @Transactional
    public List<Treatment> findByStatusAndDoctor(Status status,Doctor doctor) {
        return treatmentRepository.findByStatusAndDoctor(status,doctor);
    }

    @Transactional
    public List<Treatment> findByPatientAndStatus(Status status) {
        return treatmentRepository.findByPatientAndStatus(accountService.getCurrentAccount().getPatient(), status);
    }

    @Transactional
    public boolean setDiagnosis(String diagnosis, Integer treatment_id){
        treatmentRepository.setDiagnosis(diagnosis,treatment_id);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean setDoctor(int doctor_account_id, int treatment_id) {
        Treatment treatment = findById(treatment_id).get();
        Doctor doctor = doctorRepository.findById(doctor_account_id).get();
        if(treatment.getStatus().getId()==2){
            throw new  IllegalArgumentException("doctor already set");
        }
        treatmentRepository.setDoctor(doctor_account_id, 2, treatment_id);
        patientRepository.setDoctor(doctor_account_id, treatment.getPatient().getAccount().getId());
        doctorRepository.updatePatients(doctor.getPatientsNumber() + 1, doctor_account_id);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean addTreatment(Category category)throws IllegalArgumentException {
        Account account = accountService.getCurrentAccount();
        if (!treatmentRepository.findByPatientAndStatus(account.getId(),1,2).isEmpty()){
            throw new IllegalArgumentException("you're already attempt");
        }
        Treatment treatment = new Treatment(category);
        treatment.setPatient(account.getPatient());
        treatment.setStatus(new Status(1));
        treatmentRepository.save(treatment);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public void discharge(Integer treatment_id){
        Treatment treatment = treatmentRepository.findById(treatment_id).get();
        treatmentRepository.setStatus(new Status(3).getId(),treatment_id);
        patientRepository.setDoctor(null,treatment.getPatient().getId());
        Doctor doctor = doctorRepository.findById(treatment.getDoctor().getId()).get();
        doctorRepository.updatePatients(doctor.getPatientsNumber() -1, doctor.getId());
    }


}
