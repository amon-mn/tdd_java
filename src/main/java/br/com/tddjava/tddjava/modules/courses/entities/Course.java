package br.com.tddjava.tddjava.modules.courses.entities;

import java.util.UUID;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Course {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    
    private UUID id;

    private String description;

    private String name;

    private int workload;

}