package org.example.hospital.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @Column(name = "account_id")
    private Integer id;
    @OneToOne
    @MapsId
    private Account account;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 20,message = "Name too long")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor",fetch = FetchType.EAGER)
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor",fetch = FetchType.LAZY)
    private List<Procedures> procedures = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor",fetch = FetchType.LAZY)
    private List<Treatment> treatments = new ArrayList<>();

    private int patientsNumber;

    public Doctor(){}

    public Doctor(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPatientsNumber() {
        return patientsNumber;
    }

    public void setPatientsNumber(int patientsNumber) {
        this.patientsNumber = patientsNumber;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Procedures> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedures> procedures) {
        this.procedures = procedures;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }
}
