package org.example.hospital.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category",fetch = FetchType.LAZY)
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category",fetch = FetchType.LAZY)
    private List<Treatment> treatments = new ArrayList<>();

    public Category(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
