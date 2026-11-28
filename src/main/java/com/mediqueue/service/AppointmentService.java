package com.mediqueue.service;

import com.mediqueue.entity.Appointment;
import com.mediqueue.entity.AppointmentStatus;
import com.mediqueue.entity.Doctor;
import com.mediqueue.entity.Patient;
import com.mediqueue.repository.AppointmentRepository;
import com.mediqueue.repository.DoctorRepository;
import com.mediqueue.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Appointment bookAppointment(Appointment appointment,
                                       Long patientId,
                                       Long doctorId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Doctor not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointment.setAppointmentNumber(generateAppointmentNumber());
        appointment.setStatus(AppointmentStatus.BOOKED);

        return appointmentRepository.save(appointment);
    }

    private String generateAppointmentNumber() {
        String appointmentNumber;

        do {
            long number = System.currentTimeMillis() % 90000 + 10000;
            appointmentNumber = "MQA" + number;
        } while (appointmentRepository
                .existsByAppointmentNumber(appointmentNumber));

        return appointmentNumber;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository
                .findAllByOrderByAppointmentDateTimeDesc();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Appointment not found"));
    }

    @Transactional
    public void updateAppointmentStatus(Long id,
                                        AppointmentStatus status) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    public long getAppointmentCount() {
        return appointmentRepository.count();
    }

    public long getAppointmentCountByStatus(AppointmentStatus status) {
        return appointmentRepository.countByStatus(status);
    }
}