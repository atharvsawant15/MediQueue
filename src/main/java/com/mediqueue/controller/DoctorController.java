
package com.mediqueue.controller;

import com.mediqueue.entity.Doctor;
import com.mediqueue.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Display all doctors
    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    // Display doctor registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form";
    }

    // Save registered doctor
    @PostMapping("/save")
    public String saveDoctor(
            @Valid @ModelAttribute("doctor") Doctor doctor,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "doctor-form";
        }

        try {
            doctorService.registerDoctor(doctor);
            return "redirect:/doctors?success";

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "doctor-form";
        }
    }
}
