package org.example.hospital.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status",fetch = FetchType.LAZY)
    private List<Treatment> treatments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status",fetch = FetchType.LAZY)
    private List<Procedures> procedures = new ArrayList<>();

    public Status(){}

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

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public List<Procedures> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedures> procedures) {
        this.procedures = procedures;
    }
}
