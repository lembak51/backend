package com.example.demo.repository;

import com.example.demo.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Integer>{

    @Query("update Event c set c.nameEvent = :name WHERE c.id = :customerId")
    void setCustomerName(@Param("customerId") Long id, @Param("name") String name);



}
