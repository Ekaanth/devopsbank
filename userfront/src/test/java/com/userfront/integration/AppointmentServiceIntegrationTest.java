package com.userfront.integration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.userfront.UserfrontApplication;
import com.userfront.backend.domain.Appointment;
import com.userfront.backend.domain.User;
import com.userfront.backend.repositories.PrimaryAccountRepository;
import com.userfront.backend.repositories.SavingsAccountRepository;
import com.userfront.backend.repositories.UserRepository;
import com.userfront.backend.service.AppointmentService;
import com.userfront.enums.AppointmentEnum;
import com.userfront.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserfrontApplication.class)
@Transactional
public class AppointmentServiceIntegrationTest {
	
    @Autowired
    private PrimaryAccountRepository primaryAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Rule
    public TestName testName = new TestName();
    
//    private ZoneId defaultZoneId = ZoneId.systemDefault();
    
    @Test
    public void createAppointmentTest() throws Exception {
    	
    	// Test the new name and new email email
        String name = testName.getMethodName();
        String email = testName.getMethodName() + "@devopsbuddy.com";
        
        // Creates the new user object with the tested parameters
        User user = UserUtils.createNewUser(name, email);
        
        // Saves the linked accounts to the user into database and gets the newly saved ones
        // to put into the user relationships
        user.setPrimaryAccount(primaryAccountRepository.save(user.getPrimaryAccount()));
        user.setSavingsAccount(savingsAccountRepository.save(user.getSavingsAccount()));
        
        // Save the user object into database
        user = userRepository.save(user);
    	
        // Create Appointment object test
        Appointment appointment = createAppointment();
        
        // Set user to the appointment object
        appointment.setUser(user);
        
        // create the appointment
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        
        // Tests ============================================>>>        
        Assert.assertNotNull(createdAppointment);
        Assert.assertNotNull(createdAppointment.getUser());
        
        Assert.assertTrue(createdAppointment.getId() != 0);
        Assert.assertTrue(createdAppointment.getUser().getId() != 0);
    }
    
    private Appointment createAppointment(){
    	
    	Appointment appointment = new Appointment();
    	
		try {
			/** Date to LocalDateTime */
			String date = "2016-10-21T15:25:00Z";
			SimpleDateFormat format = new SimpleDateFormat(AppointmentEnum.DATE_FORMAT.getMessage());
			Date time;
			time = format.parse(date);
			
//			Instant instant = time.toInstant();
//			LocalDateTime localDate = instant.atZone(defaultZoneId).toLocalDateTime();
			
			appointment.setDate(time);
			appointment.setLocation("Chicago");
			appointment.setDescription("New appointment for some day");
			appointment.setConfirmed(true);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return appointment;
    }
}
