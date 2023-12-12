package com.example.demo.model;



import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "event")
@Data
public class Event implements Serializable {
    private int id;
    private String nameEvent;
    private String description;
    private Integer userId;


    public Event() {
    }

    public Event(int id, String nameEvent, String description) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.description = description;}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setNameEvent(String firstName) {
        this.nameEvent = firstName;
    }
    public void setDescription(String lastName) {
        this.description = lastName;
    }
}
//other setters and getters
