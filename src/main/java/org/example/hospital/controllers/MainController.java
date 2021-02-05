package org.example.hospital.controllers;

import org.example.hospital.entity.*;
import org.example.hospital.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private ProceduresService proceduresService;

    //TODO
    @GetMapping("/")//<- Refactor
    public String greeting(@PageableDefault(size = 3) Pageable pageable,
                           Model model) {
        Account account = accountService.getCurrentAccount();
        if (account.getRole().getName().equals("admin"))
            return admin(pageable, model);
        if (account.getRole().getName().equals("patient"))
            return patientMain(model);
        if (account.getRole().getName().equals("doctor"))
            return doctorMain(model);
        if (account.getRole().getName().equals("nurse"))
            return nurseMain(model);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/appointment")
    public String appointment(Model model) {
        return patientMain(model);
    }

    @GetMapping("/patient")
    public String patientMain(Model model) {
        Account account = accountService.getCurrentAccount();
        List<Category> categoryList = categoryService.findAllWithoutNurse();
        model.addAttribute("proceduresList", proceduresService.findByPatientAndStatus(account));
        model.addAttribute("login", account.getLogin());
        model.addAttribute("categories", categoryList);
        return "patient";
    }

    @GetMapping("/admin")
    public String admin(@PageableDefault(size = 3) Pageable pageable,
                        Model model) {
        Page<Treatment> pages = treatmentService.findByStatus(new Status(1), pageable);
        List<Treatment> treatments = pages.getContent();
        model.addAttribute("pages", pages.getTotalPages());
        model.addAttribute("treatments", treatments);
        return "admin";
    }

    @GetMapping("/procedure")
    public String procedure(Model model){
        Account account = accountService.getCurrentAccount();
        model.addAttribute("proceduresList", proceduresService.findByDoctorAndStatus(account.getDoctor(), new Status(2)));
        return ("procedure");
    }

    @GetMapping("/doctor")
    public String doctorMain(Model model) {
        Account account = accountService.getCurrentAccount();
        model.addAttribute("proceduresList", proceduresService.findByDoctorAndStatus(account.getDoctor(), new Status(2)));
        model.addAttribute("treatments", treatmentService.findByStatusAndDoctor(new Status(2), account.getDoctor()));
        model.addAttribute("login", account.getLogin());
        return "doctor";
    }

    @GetMapping("/nurse")
    public String nurseMain(Model model) {
        Account account = accountService.getCurrentAccount();
        model.addAttribute("proceduresList", proceduresService.findByDoctorAndStatus(account.getDoctor(), new Status(2)));
        model.addAttribute("login", account.getLogin());
        return "nurse";
    }

    @PostMapping("/appointment")
    public String makeAppointment(Model model,
                                  Category category) throws SQLException {
        try {
            treatmentService.addTreatment(category);
        } catch (IllegalArgumentException e) {
            model.addAttribute("appointment", true);
            return patientMain(model);
        }
        return "redirect:/patient";
    }


    @PostMapping("/discharge/{treatment.id}")
    public String discharge(@PathVariable(value = "treatment.id") int id,
                            Model model) {
        treatmentService.discharge(id);
        return "redirect:/doctor";
    }


}
