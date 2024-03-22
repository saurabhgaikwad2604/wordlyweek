package com.example.wordlyweek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.*;
import lombok.Data;

@Data
@Entity
@Table(name = "writer")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String bio;
    @ManyToMany(mappedBy = "writers")
    @JsonIgnoreProperties("writers")
    private List<Magazine> magazines = new ArrayList<>();
}