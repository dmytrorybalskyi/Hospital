package org.example.hospital.DTO;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PatientDTO {
    @NotBlank(message = "label.loginBlank")
    @Length(max = 20, message = "label.loginTooLong")
    private String login;

    @NotBlank(message = "label.passwordBlank")
    private String password;

    @NotBlank(message = "label.nameBlank")
    @Length(max = 20,message = "label.nameTooLong")
    private String name;

    @NotNull
    @Min(value = 0,message = "label.ageMin")
    @Max(value = 130,message = "label.ageMax")
    private Integer age;

    public PatientDTO(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
