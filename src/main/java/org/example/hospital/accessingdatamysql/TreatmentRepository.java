package org.example.hospital.accessingdatamysql;

import org.example.hospital.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment,Integer> {
    Treatment findByPatientAndStatus(Patient patient, Status status);

    Page<Treatment> findByStatus(Status status, Pageable pageable);

    List<Treatment> findByStatus(Status status);

    List<Treatment> findByStatusAndDoctor(Status status, Doctor doctor);

    @Query("SELECT t FROM Treatment t WHERE t.patient.account.id = :patient_account_id AND (t.status = :status1 OR t.status = :status2)")
    List<Treatment> findByPatientAndStatus(@Param("patient_account_id")Integer patient_account_id,
                                           @Param("status1")Status status1,
                                           @Param("status2")Status status2);

    @Modifying
    @Query(value ="UPDATE Treatment t SET t.doctor_account_id = ?, t.treatment_status = ? WHERE t.id = ?",nativeQuery = true)
    Integer setDoctor(Integer doctor_account_id,String status, Integer treatment_id);

    @Modifying
    @Query(value ="UPDATE Treatment t SET t.diagnosis = ? WHERE t.id = ?",nativeQuery = true)
    Integer setDiagnosis(String diagnosis, Integer treatment_id);

    @Modifying
    @Query(value ="UPDATE Treatment t SET t.treatment_status = ? WHERE t.id = ?",nativeQuery = true)
    Integer setStatus(String status, Integer treatment_id);

}
