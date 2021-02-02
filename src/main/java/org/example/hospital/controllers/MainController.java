package org.example.hospital.controllers;

import org.example.hospital.entity.*;
import org.example.hospital.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private ProceduresService proceduresService;

    @Autowired
    private  PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String greeting(Model model) {
        Account account = accountService.getCurrentAccount();
        if (account.getRole().getName().equals("admin"))
            return admin(model);
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
        model.addAttribute("proceduresList",proceduresService.findByPatientAndStatus(account));
        model.addAttribute("login", account.getLogin());
        model.addAttribute("categories", categoryList);
        return "patient";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        Account account = accountService.getCurrentAccount();
        List<Treatment> treatments = treatmentService.findByStatus(new Status(1));
        model.addAttribute("login", account.getLogin());
        model.addAttribute("treatments", treatments);
        return "admin";
    }

    @GetMapping("/doctor")
    public String doctorMain(Model model) {
        Account account = accountService.getCurrentAccount();
        model.addAttribute("proceduresList",proceduresService.findByDoctorAndStatus(account.getDoctor(),new Status(2)));
        model.addAttribute("treatments", treatmentService.findByStatusAndDoctor(new Status(2), account.getDoctor()));
        model.addAttribute("login", account.getLogin());
        return "doctor";
    }

    @GetMapping("/nurse")
    public String nurseMain(Model model) {
        Account account = accountService.getCurrentAccount();
        model.addAttribute("proceduresList",proceduresService.findByDoctorAndStatus(account.getDoctor(),new Status(2)));
        model.addAttribute("login", account.getLogin());
        return "nurse";
    }

    @PostMapping("/appointment")
    public String makeAppointment(Model model,
                                  Category category)throws SQLException {
        try {
            treatmentService.addTreatment(category);
        }catch (IllegalArgumentException e){
            model.addAttribute("appointment", true);
            return patientMain(model);
        }
        return "redirect:/patient";
    }

    @GetMapping("/addProcedure/{id}")
    public String addProcedure(@PathVariable(value = "id") int id,
                               Model model) {
        Treatment treatment = treatmentService.findById(id).get();
        model.addAttribute("types",proceduresService.findAllType());
        model.addAttribute("doctors",doctorService.getAllDoctorsByCategoryAndNurse(treatment.getCategory()));
        model.addAttribute("treatment",treatment);
        return "addProcedure";
    }

    @PostMapping("/procedure/{treatment.id}")
    public String addProcedure(@PathVariable(value = "treatment.id") int id,
                               Procedures procedures,
                               Type type,
                               Doctor doctor,
                               Model model){

        if(!proceduresService.addProcedure(id,doctor,type,procedures)){
            model.addAttribute("typeError","sister error");
           return addProcedure(id,model);
        }
        return "redirect:/";
    }

    @PostMapping("/diagnosis/{treatment.id}")
    public String diagnosis(@PathVariable(value = "treatment.id") int id,
                            @RequestParam String diagnosis,
                            Model model){
        treatmentService.setDiagnosis(diagnosis,id);
        return "redirect:/doctor";
    }

    @PostMapping("/doProcedures")
    public String doProcedure(@RequestParam Integer procedures_id,
                              Model model){
        proceduresService.doProcedure(procedures_id);
        return "redirect:/doctor";
    }

    @PostMapping("/discharge/{treatment.id}")
    public String discharge(@PathVariable(value = "treatment.id") int id,
                            Model model){
        treatmentService.discharge(id);
        return "redirect:/doctor";
    }


}
