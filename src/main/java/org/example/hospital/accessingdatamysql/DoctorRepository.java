package org.example.hospital.accessingdatamysql;

import org.example.hospital.entity.Category;
import org.example.hospital.entity.Doctor;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    List<Doctor> findByCategory(Category category);

    @Modifying
    @Query(value ="UPDATE Doctor d SET d.patients_number = ? WHERE d.account_id = ?",nativeQuery = true)
    Integer updatePatients(Integer number,Integer doctor_account_id);

    @Query("SELECT d FROM Doctor d WHERE d.category.name = :category_name OR d.category.name = :nurse")
    List<Doctor> findDoctorsByCategoryAndNurse(@Param("category_name")String category_name,
                                            @Param("nurse")String nurse);
}
