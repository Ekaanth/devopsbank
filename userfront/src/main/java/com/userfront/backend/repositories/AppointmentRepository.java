package com.userfront.backend.repositories;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.userfront.backend.domain.Appointment;

@Repository
@Transactional
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
	
	Set<Appointment> findAll();
	
	@Transactional
    @Modifying // Indicates to JPA engine that the content of the @Query annotation will change the database state
    @Query("update Appointment appointment set appointment.confirmed =:confirmed where appointment.id =:id")
    void updateAppointment(@Param("id") int id, @Param("confirmed") boolean confirmed);

}
