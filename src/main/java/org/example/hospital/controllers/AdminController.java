package org.example.hospital.controllers;

import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.accessingdatamysql.CategoryRepository;
import org.example.hospital.accessingdatamysql.DoctorRepository;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Category;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Role;
import org.example.hospital.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String admin(Map<String, Object> model){
        Account account = accountRepository.findByLogin(MainController.getCurrentUser().getUsername());
        model.put("login",account.getLogin());
        return "admin";
    }

    @RequestMapping(value = "/addDoctor",method = RequestMethod.GET)
    public String addDoctor(Map<String, Object> model){
        List<Category> categories = categoryRepository.findAll();
        model.put("categories",categories);
        return "addDoctor";
    }

    @RequestMapping(value = "/addDoctor",method = RequestMethod.POST)
    public String addDoctor(Map<String, Object> model,
                            @RequestParam String login,
                            @RequestParam String name,
                            @RequestParam String password,
                            @RequestParam Category category){
        Account account = new Account(login,password);
        account.setRole(new Role(3));
        accountService.addAccount(account);
        Doctor doctor = new Doctor(name);
        doctor.setCategory(category);
        doctor.setAccount(account);
        doctorRepository.save(doctor);
        return "admin";
    }

}
