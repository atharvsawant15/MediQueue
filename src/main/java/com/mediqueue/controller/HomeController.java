
package com.mediqueue.controller;

import com.mediqueue.entity.AppointmentStatus;
import com.mediqueue.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AppointmentService appointmentService;

    public HomeController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments()
        );

        model.addAttribute(
                "totalAppointments",
                appointmentService.getAppointmentCount()
        );

        model.addAttribute(
                "bookedAppointments",
                appointmentService.getAppointmentCountByStatus(
                        AppointmentStatus.BOOKED
                )
        );

        model.addAttribute(
                "completedAppointments",
                appointmentService.getAppointmentCountByStatus(
                        AppointmentStatus.COMPLETED
                )
        );

        model.addAttribute(
                "cancelledAppointments",
                appointmentService.getAppointmentCountByStatus(
                        AppointmentStatus.CANCELLED
                )
        );

        return "index";
    }
}
