package com.fico.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fico.demo.model.Appointment;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

	Iterable<Appointment> findByPatientID(Long patientID);
}
