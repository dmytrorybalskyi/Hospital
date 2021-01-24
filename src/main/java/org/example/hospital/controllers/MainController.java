package org.example.hospital.controllers;

import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String greeting(Map<String, Object> model){
        return "greeting";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/patient",method = RequestMethod.GET)
    public String patientMain(Map<String, Object> model){
        Account account = accountRepository.findByLogin(getCurrentUser().getUsername());
        model.put("login",account.getLogin());
        return "patient";
    }

    @RequestMapping(value = "/doctor",method = RequestMethod.GET)
    public String doctorMain(Map<String, Object> model){
        Account account = accountRepository.findByLogin(getCurrentUser().getUsername());
        model.put("login",account.getLogin());
        return "doctor";
    }

    public static org.springframework.security.core.userdetails.User getCurrentUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

}
