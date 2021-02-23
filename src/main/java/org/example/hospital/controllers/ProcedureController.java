package org.example.hospital.controllers;

import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Procedures;
import org.example.hospital.entity.Treatment;
import org.example.hospital.entity.Type;
import org.example.hospital.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class ProcedureController {

    private Logger logger = LoggerFactory.getLogger(ProcedureController.class);

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private ProceduresService proceduresService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/addProcedure/{id}")
    public String addProcedurePage(@PathVariable(value = "id") int id,
                               Model model) {
        Treatment treatment = treatmentService.findById(id).get();
        model.addAttribute("types", new ArrayList(Arrays.asList(Type.values())));
        model.addAttribute("doctors", doctorService.getAllDoctorsByCategoryAndNurse(treatment.getCategory()));
        model.addAttribute("treatment", treatment);
        return "addProcedure";
    }

    @GetMapping("/procedure/{id}")
    public String procedureId(@PathVariable(value = "id")int id,
                              Model model){
        return addProcedurePage(id,model);
    }

    @PostMapping("/procedure/{treatment.id}")
    public String addProcedure(@PathVariable(value = "treatment.id") int id,
                               Procedures procedures,
                               @RequestParam String type,
                               Doctor doctor,
                               Model model) {
        try {
            proceduresService.addProcedure(id, doctor, Type.valueOf(type), procedures);
        } catch (IllegalArgumentException e) {
            model.addAttribute("typeError", "sister error");
            logger.info("Treatment #"+id+" "+procedures.getProcedureName()+" was rejected --> sister cannot do operation");
           return addProcedurePage(id,model);
        }
        return "redirect:/";
    }

    @PostMapping("/diagnosis/{treatment.id}")
    public String diagnosis(@PathVariable(value = "treatment.id") int id,
                            @RequestParam String diagnosis,
                            Model model) {
        treatmentService.setDiagnosis(diagnosis, id);
        return "redirect:/doctor";
    }

    @PostMapping("/doProcedures")
    public String doProcedure(@RequestParam Integer procedures_id,
                              Model model) {
        proceduresService.doProcedure(procedures_id);
        return "redirect:/";
    }
}
