package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.ProceduresRepository;
import org.example.hospital.accessingdatamysql.TreatmentRepository;
import org.example.hospital.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProceduresService {

    @Autowired
    private ProceduresRepository proceduresRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    public List<Procedures> findByDoctorAndStatus(Doctor doctor,Status status){
        return proceduresRepository.findByDoctorAndStatus(doctor,status);
    }

    public List<Procedures> findByPatientAndStatus(Account account){
        return proceduresRepository.getAllProceduresByAccountAndStatus(account.getId(),Status.treatment);
    }
    @Transactional
    public boolean doProcedure(Integer procedure_id){
        proceduresRepository.doProcedure(Status.done.name(),procedure_id);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean addProcedure(int treatment_id, Doctor doctor, Type type, Procedures procedures){
        if(doctor.getCategory().getName().equals("nurse")&&type.name().equals("operation")){
            throw new IllegalArgumentException("nurse cannot do appointment");
        }
        procedures.setDoctor(doctor);
        procedures.setTreatment(treatmentRepository.findById(treatment_id).get());
        procedures.setType(type);
        procedures.setStatus(Status.treatment);
        proceduresRepository.save(procedures);
        return true;
    }

}
