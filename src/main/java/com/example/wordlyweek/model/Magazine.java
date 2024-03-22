package com.example.wordlyweek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.*;
import lombok.Data;

@Data
@Entity
@Table(name = "magazine")
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String publicationDate;
    @ManyToMany
    @JoinTable(name = "writer_magazine", joinColumns = @JoinColumn(name = "magazineid"), inverseJoinColumns = @JoinColumn(name = "writerid"))
    @JsonIgnoreProperties("magazines")
    private List<Writer> writers = new ArrayList<>();
}