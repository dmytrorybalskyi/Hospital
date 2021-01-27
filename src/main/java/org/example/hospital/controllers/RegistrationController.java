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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String addAccount(Account account,
                             Patient patient,
                             Model model){
        Account accountFromDB = accountRepository.findByLogin(account.getLogin());
        if(accountFromDB != null){
            model.addAttribute("message","User already exist");
            return "registration";
        }
        account.setRole(new Role(2));
        accountService.addAccount(account);
        Account account1 = new Account("admin","admin");
        account1.setRole(new Role(1));
        accountService.addAccount(account1);
        patient.setAccount(account);
        patientRepository.save(patient);

        return "redirect:/login";

    }

}
