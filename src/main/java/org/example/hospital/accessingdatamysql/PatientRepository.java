package org.example.hospital.accessingdatamysql;

import org.example.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {
    @Modifying
    @Query(value ="UPDATE Patient p SET p.doctor_account_id = ? WHERE p.account_id = ?",nativeQuery = true)
    Integer setDoctor(Integer doctor_account_id, Integer patient_account_id);
}
