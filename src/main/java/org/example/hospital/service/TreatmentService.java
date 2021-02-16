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
        return treatmentRepository.findByStatus(Status.done);
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

    public List<Treatment> findByStatusAndDoctor(Status status,Doctor doctor) {
        return treatmentRepository.findByStatusAndDoctor(status,doctor);
    }


    public List<Treatment> findByPatientAndStatus(Status status,Account account) {
        return treatmentRepository.findByPatientAndStatus(account.getPatient(), status);
    }

    @Transactional
    public boolean setDiagnosis(String diagnosis, Integer treatment_id){
        treatmentRepository.setDiagnosis(diagnosis,treatment_id);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean setDoctor(Doctor doctor, int treatment_id) {
        Treatment treatment = findById(treatment_id).get();
        if(treatment.getStatus().name().equals("treatment")){
            throw new  IllegalArgumentException("doctor already set");
        }
        treatmentRepository.setDoctor(doctor.getId(), Status.treatment.name(), treatment_id);
        patientRepository.setDoctor(doctor.getId(), treatment.getPatient().getAccount().getId());
        doctorRepository.updatePatients(doctor.getPatientsNumber() + 1, doctor.getId());
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean addTreatment(Account account,Category category) throws IllegalArgumentException, SQLException {
        if(!treatmentRepository.findByPatientAndStatus(account.getId(),Status.registration,Status.treatment).isEmpty()){
            throw new IllegalArgumentException("you already appointment");
        }
        Treatment treatment = new Treatment(category);
        treatment.setPatient(account.getPatient());
        treatment.setStatus(Status.registration);
        treatmentRepository.save(treatment);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public void discharge(Integer treatment_id){
        Treatment treatment = treatmentRepository.findById(treatment_id).get();
        treatmentRepository.setStatus(Status.done.name(),treatment_id);
        patientRepository.setDoctor(null,treatment.getPatient().getId());
        doctorRepository.updatePatients(treatment.getDoctor().getPatientsNumber() -1, treatment.getDoctor().getId());
    }


}
