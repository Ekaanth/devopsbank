package com.userfront.backend.resource;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.userfront.backend.domain.Appointment;
import com.userfront.backend.service.AppointmentService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ADMIN')")
public class AppointmentResource {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@RequestMapping(value = "/appointment/all", method = RequestMethod.GET)
	public Set<Appointment> findAppointmentList(){
		Set<Appointment> appointments = appointmentService.findAll();
		return appointments;
	}

	@RequestMapping("/appointment/{id}/confirm")
	public void confirmAppointment(@PathVariable("id") Integer id){
		appointmentService.confirmAppointment(id);
	}
	
	@RequestMapping("/appointment/{id}/cancel")
	public void cancelAppointment(@PathVariable("id") Integer id){
		appointmentService.cancelAppointment(id);
	}
}
