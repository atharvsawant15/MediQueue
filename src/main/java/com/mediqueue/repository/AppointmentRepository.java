package com.mediqueue.repository;

import com.mediqueue.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByAppointmentNumber(String appointmentNumber);

    List<Appointment> findAllByOrderByAppointmentDateTimeDesc();

    long countByStatus(
            com.mediqueue.entity.AppointmentStatus status
    );
}