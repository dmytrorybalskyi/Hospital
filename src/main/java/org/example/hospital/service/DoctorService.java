package org.example.hospital.service;

import org.example.hospital.DTO.DoctorDTO;
import org.example.hospital.accessingdatamysql.DoctorRepository;
import org.example.hospital.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccountService accountService;

    public List<Doctor> getAllDoctorsByCategory(Category category){
        return doctorRepository.findByCategory(category);
    }

    public List<Doctor> getAllDoctorsByCategoryAndNurse(Category category){
        return doctorRepository.findDoctorsByCategoryAndNurse(category.getName(),"nurse");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,IllegalArgumentException.class})
    public boolean addDoctor(DoctorDTO doctorDTO) {
        Account account = convertToAccount(doctorDTO);
        Doctor doctor = convertToDoctor(doctorDTO,account);
        account.setRole(Roles.doctor);
        if(doctor.getCategory().getName().equals("nurse"))
           account.setRole(Roles.nurse);
        accountService.addAccount(account);
        doctorRepository.save(doctor);
        return true;
    }


    private Doctor convertToDoctor(DoctorDTO doctorDTO,Account account) {
        return new Doctor.Builder(doctorDTO.getName())
                .setCategory(doctorDTO.getCategory())
                .setAccount(account).build();
    }

    private Account convertToAccount(DoctorDTO doctorDTO){
        return new Account(doctorDTO.getLogin(),doctorDTO.getPassword());
    }

}
