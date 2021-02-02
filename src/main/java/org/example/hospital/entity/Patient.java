package org.example.hospital.entity;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {
    @Id
    @Column(name = "account_id")
    private Integer id;

    @OneToOne
    @MapsId
    private Account account;

    @NotBlank(message = "Name cannot be empty")
    @Length(max = 20, message = "Name too long")
    private String name;

    @ManyToOne
    @JoinColumn(name = "doctor_account_id")
    private Doctor doctor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient",fetch = FetchType.LAZY)
    private List<Treatment> treatments = new ArrayList<>();

    @NotNull
    @Min(value = 0,message = "more than 0")
    @Max(value = 130,message = "las than 130")
    private Integer age;

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
