
package com.mediqueue.repository;

import com.mediqueue.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByDoctorCode(String doctorCode);

    boolean existsByDoctorCode(String doctorCode);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
