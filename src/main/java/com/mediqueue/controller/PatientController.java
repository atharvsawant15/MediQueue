
package com.mediqueue.controller;

import com.mediqueue.entity.Patient;
import com.mediqueue.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Display all patients
    @GetMapping
    public String listPatients(Model model) {

        model.addAttribute(
                "patients",
                patientService.getAllPatients()
        );

        return "patients";
    }

    // Display registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("patient", new Patient());

        return "patient-form";
    }

    // Save patient
    @PostMapping("/save")
    public String savePatient(
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "patient-form";
        }

        try {

            patientService.registerPatient(patient);

            return "redirect:/patients?success";

        } catch (IllegalArgumentException exception) {

            model.addAttribute("error", exception.getMessage());

            return "patient-form";
        }
    }
}
