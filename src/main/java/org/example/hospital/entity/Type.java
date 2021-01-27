package org.example.hospital.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type",fetch = FetchType.EAGER)
    private List<Procedures> procedures = new ArrayList<>();

    public Type(){}

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

    public List<Procedures> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedures> procedures) {
        this.procedures = procedures;
    }
}
