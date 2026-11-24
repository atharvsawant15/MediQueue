
package com.mediqueue.service;

import com.mediqueue.entity.Patient;
import com.mediqueue.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Register a new patient
    @Transactional
    public Patient registerPatient(Patient patient) {

        // Prevent duplicate phone numbers
        if (patientRepository.existsByPhone(patient.getPhone())) {
            throw new IllegalArgumentException(
                    "A patient with this phone number already exists."
            );
        }

        // Generate unique patient code
        String patientCode = generatePatientCode();

        patient.setPatientCode(patientCode);

        return patientRepository.save(patient);
    }

    // Generate patient code
    private String generatePatientCode() {

        String patientCode;

        do {
            long number = 1000 + (long) (Math.random() * 9000);
            patientCode = "MQP" + number;

        } while (patientRepository.existsByPatientCode(patientCode));

        return patientCode;
    }

    // Retrieve all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Retrieve patient by ID
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    // Retrieve patient by patient code
    public Optional<Patient> getPatientByCode(String patientCode) {
        return patientRepository.findByPatientCode(patientCode);
    }

    // Count registered patients
    public long getPatientCount() {
        return patientRepository.count();
    }
}
