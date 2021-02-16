package org.example.hospital.entity;

import javax.persistence.*;

@Entity
public class Procedures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String procedureName;

    @ManyToOne
    @JoinColumn(name = "doctor_account_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @Enumerated(EnumType.STRING)
    @Column(name = "procedure_status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public Procedures(){}

    public Procedures(String procedureName){
        this.procedureName = procedureName;
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

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }
}
