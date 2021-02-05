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
    List<Treatment> findByPatientAndStatus(Patient patient, Status status);

    Page<Treatment> findByStatus(Status status, Pageable pageable);

    List<Treatment> findByStatus(Status status);

    List<Treatment> findByStatusAndDoctor(Status status, Doctor doctor);

    @Query("SELECT t FROM Treatment t WHERE t.patient.account.id = :patient_account_id AND (t.status.id = :status_id1 OR t.status.id = :status_id2)")
    List<Treatment> findByPatientAndStatus(@Param("patient_account_id")Integer patient_account_id,
                                           @Param("status_id1")Integer status_id1,
                                           @Param("status_id2")Integer status_id2);

    @Modifying
    @Query(value ="UPDATE Treatment t SET t.doctor_account_id = ?, t.status_id = ? WHERE t.id = ?",nativeQuery = true)
    Integer setDoctor(Integer doctor_account_id,Integer status_id, Integer treatment_id);

    @Modifying
    @Query(value ="UPDATE Treatment t SET t.diagnosis = ? WHERE t.id = ?",nativeQuery = true)
    Integer setDiagnosis(String diagnosis, Integer treatment_id);

    @Modifying
    @Query(value ="UPDATE Treatment t SET t.status_id = ? WHERE t.id = ?",nativeQuery = true)
    Integer setStatus(Integer status_id, Integer treatment_id);

}
