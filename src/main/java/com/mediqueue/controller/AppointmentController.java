
package com.mediqueue.controller;

import com.mediqueue.entity.Appointment;
import com.mediqueue.entity.AppointmentStatus;
import com.mediqueue.service.AppointmentService;
import com.mediqueue.service.DoctorService;
import com.mediqueue.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentController(
            AppointmentService appointmentService,
            PatientService patientService,
            DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments",
                appointmentService.getAllAppointments());

        return "appointments";
    }

    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        loadFormData(model);

        return "appointment-form";
    }

    @PostMapping("/save")
    public String saveAppointment(
            @Valid @ModelAttribute("appointment") Appointment appointment,
            BindingResult bindingResult,
            @RequestParam("patientId") Long patientId,
            @RequestParam("doctorId") Long doctorId,
            Model model) {

        if (bindingResult.hasErrors()) {
            loadFormData(model);
            return "appointment-form";
        }

        try {
            appointmentService.bookAppointment(
                    appointment, patientId, doctorId);

            return "redirect:/appointments";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            loadFormData(model);
            return "appointment-form";
        }
    }

    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatus status) {

        appointmentService.updateAppointmentStatus(id, status);

        return "redirect:/appointments";
    }

    private void loadFormData(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
    }
}
