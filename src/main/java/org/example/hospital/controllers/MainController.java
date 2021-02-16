package org.example.hospital.controllers;

import org.example.hospital.entity.*;
import org.example.hospital.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private CategoryService categoryService;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private ProceduresService proceduresService;

    @GetMapping("/")
    public String greeting(@PageableDefault(size = 3) Pageable pageable,
                           @AuthenticationPrincipal Account account,
                           Model model) {
        switch (account.getRole().getAuthority()) {
            case "admin": return admin(pageable, account, model);
            case "patient": return patientMain(account, model);
            case "doctor": return doctorMain(account, model);
            case "nurse": return nurseMain(account, model);
            default: return "login";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/appointment")
    public String appointment(@AuthenticationPrincipal Account account,
                              Model model) {
        return patientMain(account, model);
    }

    @GetMapping("/patient")
    public String patientMain(@AuthenticationPrincipal Account account,
                              Model model) {
        List<Category> categoryList = categoryService.findAllWithoutNurse();
        model.addAttribute("proceduresList", proceduresService.findByPatientAndStatus(account));
        model.addAttribute("login", account.getLogin());
        model.addAttribute("categories", categoryList);
        return "patient";
    }

    @GetMapping("/admin")
    public String admin(@PageableDefault(size = 3) Pageable pageable,
                        @AuthenticationPrincipal Account account,
                        Model model) {
        Page<Treatment> pages = treatmentService.findByStatus(Status.registration, pageable);
        List<Treatment> treatments = pages.getContent();
        model.addAttribute("pages", pages.getTotalPages());
        model.addAttribute("treatments", treatments);
        return "admin";
    }

    @GetMapping("/procedure")
    public String procedure(@AuthenticationPrincipal Account account,
                            Model model) {
        model.addAttribute("proceduresList", proceduresService.findByDoctorAndStatus(account.getDoctor(), Status.treatment));
        return ("procedure");
    }

    @GetMapping("/doctor")
    public String doctorMain(@AuthenticationPrincipal Account account,
                             Model model) {
        model.addAttribute("proceduresList", proceduresService.findByDoctorAndStatus(account.getDoctor(), Status.treatment));
        model.addAttribute("treatments", treatmentService.findByStatusAndDoctor(Status.treatment, account.getDoctor()));
        model.addAttribute("login", account.getLogin());
        return "doctor";
    }

    @GetMapping("/nurse")
    public String nurseMain(@AuthenticationPrincipal Account account,
                            Model model) {
        model.addAttribute("proceduresList", proceduresService.findByDoctorAndStatus(account.getDoctor(), Status.treatment));
        model.addAttribute("login", account.getLogin());
        return "nurse";
    }

    @PostMapping("/appointment")
    public String makeAppointment(Model model,
                                  @AuthenticationPrincipal Account account,
                                  Category category) throws SQLException {
        try {
            treatmentService.addTreatment(account, category);
        } catch (IllegalArgumentException e) {
            model.addAttribute("appointment", true);
            return patientMain(account,model);
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
