package com.userfront.backend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.backend.domain.Appointment;
import com.userfront.backend.repositories.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public Appointment createAppointment(Appointment appointment){
		return appointmentRepository.save(appointment);
	}
	
	public Set<Appointment> findAll(){
		return appointmentRepository.findAll();
	}
	
	public Appointment findAppointment(Integer id){
		return appointmentRepository.findOne(id);
	}
	
	public void confirmAppointment(Integer id){
		
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(true);
		appointmentRepository.save(appointment);
		
	}
	
	public void cancelAppointment(Integer id){
		
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(false);
		appointmentRepository.updateAppointment(appointment.getId(), appointment.isConfirmed());
		
	}
}
