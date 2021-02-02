package org.example.hospital.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "doctor_account_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_account_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treatment",fetch = FetchType.LAZY)
    private List<Procedures> proceduresList = new ArrayList<>();

    private String diagnosis;

    public Treatment(){}

    public Treatment(Category category){
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Procedures> getProceduresList() {
        return proceduresList;
    }

    public void setProceduresList(List<Procedures> proceduresList) {
        this.proceduresList = proceduresList;
    }
}

