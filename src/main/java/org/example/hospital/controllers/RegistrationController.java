package org.example.hospital.controllers;


import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.accessingdatamysql.PatientRepository;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Patient;
import org.example.hospital.entity.Role;
import org.example.hospital.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public String addAccount(Model model,
                             @RequestParam String login,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam int age){
        Account accountFromDB = accountRepository.findByLogin(login);
        if(accountFromDB != null){
            model.addAttribute("message","User already exist");
            return "registration";
        }
        Account account = new Account(login,password);
        account.setRole(new Role(2));
        accountService.addAccount(account);
        Patient patient = new Patient(name,age);
        patient.setAccount(account);
        patientRepository.save(patient);

        return "redirect:/login";

    }

}
