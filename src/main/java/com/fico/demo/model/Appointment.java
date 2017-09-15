package com.fico.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Appointment {

	@Id
	@GeneratedValue
	private long appointmentID;
	private String patientName;
	@Temporal(TemporalType.DATE)
	private Date dateTime;
	private int patientID;

	public long getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(long appointmentID) {
		this.appointmentID = appointmentID;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
}