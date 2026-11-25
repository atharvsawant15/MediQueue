
package com.mediqueue.service;

import com.mediqueue.entity.Doctor;
import com.mediqueue.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final Random random = new Random();

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor registerDoctor(Doctor doctor) {

        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new IllegalArgumentException(
                    "A doctor with this email already exists."
            );
        }

        if (doctorRepository.existsByPhone(doctor.getPhone())) {
            throw new IllegalArgumentException(
                    "A doctor with this phone number already exists."
            );
        }

        doctor.setDoctorCode(generateDoctorCode());

        return doctorRepository.save(doctor);
    }

    private String generateDoctorCode() {

        String doctorCode;

        do {
            int number = 1000 + random.nextInt(9000);
            doctorCode = "MQD" + number;

        } while (doctorRepository.existsByDoctorCode(doctorCode));

        return doctorCode;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Optional<Doctor> getDoctorByCode(String doctorCode) {
        return doctorRepository.findByDoctorCode(doctorCode);
    }

    public long getDoctorCount() {
        return doctorRepository.count();
    }
}
