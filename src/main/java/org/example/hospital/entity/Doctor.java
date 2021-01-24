package org.example.hospital.entity;

import javax.persistence.*;
@Entity
public class Doctor {
    @Id
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @MapsId
    private Account account;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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
}
