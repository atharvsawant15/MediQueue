
package com.mediqueue.repository;

import com.mediqueue.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPatientCode(String patientCode);

    boolean existsByPhone(String phone);

    boolean existsByPatientCode(String patientCode);
}
