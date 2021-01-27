package org.example.hospital.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {
    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @MapsId
    private Account account;

    private String name;

    @ManyToOne
    @JoinColumn(name = "doctor_account_id")
    private Doctor doctor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient",fetch = FetchType.LAZY)
    private List<Treatment> treatments = new ArrayList<>();

    private int age;

    public Patient(){}

    public Patient(String name,int age){
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
