package org.example.hospital.controllers;

import org.example.hospital.DTO.PatientDTO;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Roles;
import org.example.hospital.service.AccountService;
import org.example.hospital.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addAccount(@Valid PatientDTO patientDTO,
                             BindingResult bindingResultPatientDTO,
                             Model model){
        if (bindingResultPatientDTO.hasErrors()) {
            bindingResultPatientDTO.getFieldErrors().stream().forEach(FieldError -> model
                    .addAttribute(FieldError.getField() + FieldError.getCode(), true));
            return "registration";
        }
        try {
            patientService.addPatient(patientDTO);
            Account account = new Account("admin","admin");
            account.setRole(Roles.admin);
            accountService.addAccount(account);
        } catch (Exception e) {
            model.addAttribute("message", true);
            logger.error("User with login "+patientDTO.getLogin()+" --> is already exist");
            return "registration";
        }
        return "redirect:/login";
    }
}
