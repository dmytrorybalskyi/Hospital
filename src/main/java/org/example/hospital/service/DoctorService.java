package org.example.hospital.service;

import org.example.hospital.DTO.DoctorDTO;
import org.example.hospital.accessingdatamysql.DoctorRepository;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Category;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Role;
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
        if (accountService.existsByLogin(doctorDTO.getLogin())) {
            throw new IllegalArgumentException("user already exist");
        }
        Account account = new Account(doctorDTO.getLogin(), doctorDTO.getPassword());
        Doctor doctor = new Doctor.Builder(doctorDTO.getName()).setCategory(doctorDTO.getCategory()).setAccount(account).build();
        account.setRole(new Role(3));
        if(doctor.getCategory().getName().equals("nurse"))
            account.setRole(new Role(4));
        accountService.addAccount(account);
        doctorRepository.save(doctor);
        return true;
    }


    private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setLogin(doctor.getAccount().getLogin());
        doctorDTO.setPassword(doctor.getAccount().getPassword());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setCategory(doctor.getCategory());
        return doctorDTO;
    }
}
