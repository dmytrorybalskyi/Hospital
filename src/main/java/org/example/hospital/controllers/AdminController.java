package org.example.hospital.controllers;

import org.example.hospital.DTO.DoctorDTO;
import org.example.hospital.entity.*;
import org.example.hospital.service.AccountService;
import org.example.hospital.service.CategoryService;
import org.example.hospital.service.DoctorService;
import org.example.hospital.service.TreatmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;


@Controller
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/addDoctor")
    public String addDoctor(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "addDoctor";
    }


    @PostMapping("/addDoctor")
    public String addDoctor(@Valid DoctorDTO doctorDTO,
                            BindingResult bindingResultDoctorDTO,
                            Model model) {
        if (bindingResultDoctorDTO.hasErrors()) {
            bindingResultDoctorDTO.getFieldErrors().stream().forEach(FieldError -> model
                    .addAttribute(FieldError.getField() + FieldError.getCode(), true));
            return addDoctor(model);
        }
        try{
            doctorService.addDoctor(doctorDTO);
        }catch (Exception e) {
            logger.error(doctorDTO.getLogin()+": " + e.getMessage());
            model.addAttribute("message", true);
            return addDoctor(model);
        }

        return "redirect:/admin";
    }

    @GetMapping("/treatment/{id}")
    public String setDoctor(@PathVariable(value = "id") int id,
                            Model model) {
        Treatment treatment = treatmentService.findById(id).get();
        model.addAttribute("treatment", treatment);
        model.addAttribute("doctors", doctorService.getAllDoctorsByCategory(treatment.getCategory()));
        return "treatment";
    }


    @PostMapping("/setDoctor/{treatment.id}")
    public String setDoctor(@PathVariable(value = "treatment.id") int id,
                            Doctor doctor,
                            Model model) {
        try{
            treatmentService.setDoctor(doctor,id);
        }catch (IllegalArgumentException e){
            logger.error("Treatment #"+id+" --> doctor was already set");
            model.addAttribute("doctor","doctor already set");
            return setDoctor(id,model);
        }
        return "redirect:/admin";
    }
}
