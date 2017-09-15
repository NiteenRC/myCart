package com.fico.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Appointment;
import com.fico.demo.repo.AppointmentRepo;

@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentRepo appointmentRepo;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Appointment>> getAll(@RequestParam(required = false) Long personId) {
		return new ResponseEntity(appointmentRepo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
		Appointment newAppointment = appointmentRepo.save(appointment);
		if (newAppointment == null) {
			return new ResponseEntity(new CustomErrorType("Appointment is not saved"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(newAppointment, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Appointment> findOne(@PathVariable int id) {
		return new ResponseEntity(appointmentRepo.findOne(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody Appointment appointment) {
		appointment.setAppointmentID(id);
		return new ResponseEntity(appointmentRepo.save(appointment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Appointment> delete(@PathVariable int id) {
		this.appointmentRepo.delete(id);
		return new ResponseEntity(null, HttpStatus.RESET_CONTENT);
	}
}