package com.userfront.web.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.backend.domain.Appointment;
import com.userfront.backend.domain.User;
import com.userfront.backend.service.AppointmentService;
import com.userfront.backend.service.UserService;
import com.userfront.enums.AppointmentEnum;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	
	public static final String APPOINTMENT_CREATE_URL_MAPPING = "/create";
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private UserService userService;
	
//	private ZoneId defaultZoneId = ZoneId.systemDefault();
	
	@RequestMapping(value = APPOINTMENT_CREATE_URL_MAPPING, method = RequestMethod.GET)
	public String createAppointment(Model model) {
		
		Appointment appointment = new Appointment();
		model.addAttribute(AppointmentEnum.MODEL_APPOINTMENT.getMessage(), appointment);
		model.addAttribute(AppointmentEnum.MODEL_DATE.getMessage(), "");
		
		return AppointmentEnum.APPOINTMENT_VIEW_NAME.getMessage();
	}

	@RequestMapping(value = APPOINTMENT_CREATE_URL_MAPPING, method = RequestMethod.POST)
	public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat(AppointmentEnum.DATE_FORMAT.getMessage());
		Date time = format.parse(date);
		
//		Instant instant = time.toInstant();
//		LocalDateTime localDate = instant.atZone(defaultZoneId).toLocalDateTime();
//		appointment.setDate(localDate);
		
		appointment.setDate(time);
		
		User user = userService.findByUsername(principal.getName());
		appointment.setUser(user);
		
		appointmentService.createAppointment(appointment);
		
		return AppointmentEnum.REDIRECT_USER_FRONT_VIEW_NAME.getMessage();
	}
}
